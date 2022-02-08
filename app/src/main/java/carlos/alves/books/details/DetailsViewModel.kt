package carlos.alves.books.details

import androidx.lifecycle.ViewModel
import carlos.alves.books.database.BookServer

class DetailsViewModel : ViewModel() {

    fun checkIfIsFavorite(id: String): Boolean {
        return BookServer.checkIfIsFavorite(id)
    }

    fun addAsFavorite(id: String) {
        BookServer.addAsFavorite(id)
    }

    fun removeAsFavorite(id: String) {
        BookServer.removeAsFavorite(id)
    }
}