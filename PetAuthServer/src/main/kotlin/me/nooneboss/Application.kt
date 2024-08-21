package me.nooneboss

import io.ktor.server.application.*
import io.ktor.server.netty.*
import me.nooneboss.plugins.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureSecurity()
    configureMonitoring()
    configureDatabases()
    configureRouting()
}
