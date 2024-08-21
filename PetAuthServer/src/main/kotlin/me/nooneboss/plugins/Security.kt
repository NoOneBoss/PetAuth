package me.nooneboss.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import me.nooneboss.LoginPage
import me.nooneboss.log.Log

private object JWTConfig{
    fun generateToken(uuid: String, secret: String): String {
        return JWT.create()
            .withClaim("uuid", uuid)
            .sign(Algorithm.HMAC256(secret))
    }

    fun verifyToken(secret: String) : JWTVerifier {
        return JWT.require(Algorithm.HMAC256(secret))
            .build()
    }
}

fun Application.configureSecurity() {
    val secret = environment.config.propertyOrNull("jwt.secret")?.getString() ?: "secret"
    val rlm = environment.config.propertyOrNull("jwt.realm")?.getString() ?: "realm"

    authentication {
        jwt {
            realm = rlm
            verifier(JWTConfig.verifyToken(secret))
        }
    }

    routing {
        get("/login/{uuid}"){
            val uuid = call.parameters["uuid"] ?: throw IllegalArgumentException("No UUID found")
            if(!Databases.logService!!.isAlreadyLoggedToday(uuid)) {

                Databases.logService!!.create(Log(uuid, "START_WORK", System.currentTimeMillis()))

                LoginPage.renderLoginPage(call)
            }
            else {
                LoginPage.renderAlreadyLoginPage(call)
            }
        }
    }
}
