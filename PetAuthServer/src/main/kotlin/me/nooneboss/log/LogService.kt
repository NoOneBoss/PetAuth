package me.nooneboss.log

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.Document
import org.bson.types.ObjectId
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


class LogService(private val database: MongoDatabase) {
    var collection: MongoCollection<Document>

    init {
        database.createCollection("logs")
        collection = database.getCollection("logs")
    }

    // Check if there's already a log for today
    suspend fun isAlreadyLoggedToday(userId: String): Boolean = withContext(Dispatchers.IO) {
        val startOfDay = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
        val endOfDay = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant())

        val query = and(
            eq("userId", userId),
            gte("time", startOfDay.time),
            lt("time", endOfDay.time)
        )

        collection.find(query).first() != null
    }

    // Add work log
    suspend fun create(log: Log): String = withContext(Dispatchers.IO) {
        val doc = log.toDocument()
        collection.insertOne(doc)
        doc["_id"].toString()
    }

    // Read work log
    suspend fun read(id: String): Log? = withContext(Dispatchers.IO) {
        collection.find(Filters.eq("_id", ObjectId(id))).first()?.let(Log::fromDocument)
    }

    //read all work logs
    suspend fun read(): List<Log> = withContext(Dispatchers.IO) {
        collection.find().map { Log.fromDocument(it) }.toList()
    }


    // Update a work log
    suspend fun update(id: String, log: Log): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndReplace(Filters.eq("_id", ObjectId(id)), log.toDocument())
    }

    // Delete a work log
    suspend fun delete(id: String): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndDelete(Filters.eq("_id", ObjectId(id)))
    }
}