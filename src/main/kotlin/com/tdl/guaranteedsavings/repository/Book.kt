package com.tdl.guaranteedsavings.repository

import org.ktorm.entity.Entity

interface Book : Entity<Book> {
    companion object : Entity.Factory<Book>()
    val id: Long?
    var name: String
}
