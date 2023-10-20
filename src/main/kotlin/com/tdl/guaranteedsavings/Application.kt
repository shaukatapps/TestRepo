package com.tdl.guaranteedsavings

import com.tdl.guaranteedsavings.config.Configuration
import com.tdl.guaranteedsavings.config.DBConfig
import com.tdl.guaranteedsavings.plugins.configureSerialization
import io.ktor.server.application.Application
import com.tdl.guaranteedsavings.route.configureBookRoutes


fun main(args: Array<String>): Unit =
    io.ktor.server.cio.EngineMain.main(args)

fun Application.module() {
        Configuration.initConfig(this.environment)
        configureBookRoutes()
        configureSerialization()
        DBConfig.getDatabaseConnection()
}
