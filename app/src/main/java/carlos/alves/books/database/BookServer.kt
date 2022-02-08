package carlos.alves.books.database

import android.content.Context
import androidx.room.Room

object BookServer {

    private lateinit var context: Context

    private val database by lazy {
        Room.databaseBuilder(context, BooksDatabase::class.java, "book_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    private val repository by lazy { BookRepository(database) }

    fun setContext(context: Context) {
        BookServer.context = context
    }

    fun checkIfIsFavorite(id: String): Boolean {
        val hasId = repository.getBookCount(id)
        if (hasId == 0) return false
        return true
    }

    fun addAsFavorite(id: String) {
        repository.insertNewBookId(id)
    }

    fun removeAsFavorite(id: String) {
        repository.deleteBookId(id)
    }
}