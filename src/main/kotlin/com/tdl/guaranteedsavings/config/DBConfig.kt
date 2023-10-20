package com.tdl.guaranteedsavings.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.ktorm.database.Database
import org.slf4j.Logger
import org.slf4j.LoggerFactory
object DBConfig {
    private lateinit var connection: Database
    fun getDatabaseConnection() {
           connection = PostgresConfig().connect(
                dataBaseUrl = Configuration.env.databaseUrl,
                userName = Configuration.env.databaseUsername,
                dbPassword = Configuration.env.databasePassword,
                driverClassName = Configuration.env.driverClassName,
               )
    }
    fun getDatabase(): Database {
        return connection
    }
    suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO) {
        getDatabase().useTransaction {
            block()
        }
    }
}

class PostgresConfig {
    private val log: Logger = LoggerFactory.getLogger(javaClass)
    fun connect(dataBaseUrl: String, userName: String, dbPassword: String, driverClassName: String): Database {
        log.info("Guaranteed-Savings : Database connected")
        return Database.connect(hikari(dataBaseUrl, userName, dbPassword,driverClassName))
    }

    private fun hikari(
        dataBaseUrl: String,
        userName: String,
        dbPassword: String,
        driverClassName: String
    ): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = driverClassName
        config.jdbcUrl = dataBaseUrl
        config.username = userName
        config.password = dbPassword
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.maxLifetime = 90000
        config.validate()
        return HikariDataSource(config)
    }
}

