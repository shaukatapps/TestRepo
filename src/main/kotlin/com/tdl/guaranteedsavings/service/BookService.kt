package com.tdl.guaranteedsavings.service

import com.tdl.guaranteedsavings.config.DBConfig
import com.tdl.guaranteedsavings.dto.BookRequest
import com.tdl.guaranteedsavings.repository.Book
import com.tdl.guaranteedsavings.repository.Books
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toSet

class BookService {

    private val database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/gauranteedsavings",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "postgres"
    )

    fun createBook(bookRequest: BookRequest): Boolean {
        val newBook = Book {
            name = bookRequest.name
        }

        val affectedRecordsNumber =
            DBConfig.getDatabase().sequenceOf(Books)
                .add(newBook)

        return affectedRecordsNumber == 1
    }

    fun findAllBooks(): Set<Book> =
        database.sequenceOf(Books).toSet()

    fun findBookById(bookId: Long): Book? =
        database.sequenceOf(Books)
            .find { book -> book.id eq bookId }

    fun updateBookById(bookId: Long, bookRequest: BookRequest): Boolean {
        val foundBook = findBookById(bookId)
        foundBook?.name = bookRequest.name

        val affectedRecordsNumber = foundBook?.flushChanges()

        return affectedRecordsNumber == 1
    }
    fun deleteBookById(bookId: Long): Boolean {
        val foundBook = findBookById(bookId)

        val affectedRecordsNumber = foundBook?.delete()

        return affectedRecordsNumber == 1
    }
}