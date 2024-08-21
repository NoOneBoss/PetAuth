package me.nooneboss.plugins

import com.mongodb.client.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.nooneboss.log.Log
import me.nooneboss.log.LogService

object Databases {
    var mongoDatabase : MongoDatabase? = null
    var logService : LogService? = null
}

fun Application.configureDatabases() {
    Databases.mongoDatabase = connectToMongoDB()
    Databases.logService = LogService(Databases.mongoDatabase!!)

    /*
    Эти роутинги - заготовка под orm-gui
    */
    routing {
        authenticate {
            // Create log
            post("/logs") {
                val log = call.receive<Log>()
                val id = Databases.logService!!.create(log)
                call.respond(HttpStatusCode.Created, id)
            }

            // Read log
            get("/logs/{id}") {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
                Databases.logService!!.read(id)?.let { log ->
                    call.respond(log)
                } ?: call.respond(HttpStatusCode.NotFound)
            }

            //Read all logs
            get("/logs") {
                Databases.logService!!.read().let { logs ->
                    call.respond(logs)
                }
            }
        }
    }
}


fun Application.connectToMongoDB(): MongoDatabase {
    val user = environment.config.tryGetString("db.mongo.user")
    val password = environment.config.tryGetString("db.mongo.password")
    val host = environment.config.tryGetString("db.mongo.host") ?: "127.0.0.1"
    val port = environment.config.tryGetString("db.mongo.port") ?: "27017"
    val maxPoolSize = environment.config.tryGetString("db.mongo.maxPoolSize")?.toInt() ?: 20
    val databaseName = environment.config.tryGetString("db.mongo.database.name") ?: "employee-logs"

    val credentials = user?.let { userVal -> password?.let { passwordVal -> "$userVal:$passwordVal@" } }.orEmpty()
    val uri = "mongodb://$credentials$host:$port/?maxPoolSize=$maxPoolSize&w=majority"

    val mongoClient = MongoClients.create(uri)
    val database = mongoClient.getDatabase(databaseName)

    environment.monitor.subscribe(ApplicationStopped) {
        mongoClient.close()
    }

    return database
}
