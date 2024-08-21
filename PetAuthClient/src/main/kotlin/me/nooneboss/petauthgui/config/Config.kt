package me.nooneboss.petauthgui.config

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

object Config {
    data class AppConfig(
        val server: ServerSettings,
        val auth: AuthSettings
    )

    data class ServerSettings(
        val url: String
    )

    data class AuthSettings(
        var uuid: String
    )

    fun loadConfig(filePath: String): AppConfig {
        val mapper = YAMLMapper().registerModule(KotlinModule())
        return mapper.readValue(File(filePath))
    }

    fun saveConfig(filePath: String, config: AppConfig) {
        val mapper = YAMLMapper().registerModule(KotlinModule())
        mapper.writeValue(File(filePath), config)
    }
}