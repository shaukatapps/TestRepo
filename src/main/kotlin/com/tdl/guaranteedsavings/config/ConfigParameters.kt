package com.tdl.guaranteedsavings.config

data class ConfigParameters(
    val databaseUrl: String,
    val databaseUsername: String,
    val databasePassword: String,
    val appLoggingLevel: String,
    val isTestEnv:String,
    val driverClassName :String

)
