package com.tdl.guaranteedsavings.repository

import org.ktorm.schema.Table
import org.ktorm.schema.long
import org.ktorm.schema.varchar

object Books : Table<Book>("book") {
    val id = long("id").primaryKey().bindTo(Book::id)
    val name = varchar("name").bindTo(Book::name)
}