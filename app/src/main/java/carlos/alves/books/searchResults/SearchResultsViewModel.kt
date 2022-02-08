package carlos.alves.books.searchResults

import androidx.lifecycle.ViewModel
import carlos.alves.books.ApiInterface
import carlos.alves.books.Book
import carlos.alves.books.database.BookServer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultsViewModel : ViewModel() {

    lateinit var bookTitle: String
    private var allBooks = mutableListOf<Book>()
    private var favoriteBooks = mutableListOf<Book>()

    fun fetchBooks(recyclerAdapter: SearchResultsAdapter) {

        val apiInterface = ApiInterface.create().getBooks(bookTitle)

        apiInterface.enqueue(object: Callback<SearchResult> {

            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                if(response.body() != null)
                    setBooks(response.body()!!)
                    recyclerAdapter.setBooks(allBooks)
            }

            override fun onFailure(call: Call<SearchResult>?, t: Throwable?) {
            }

        })
    }

    fun setFilter(recyclerAdapter: SearchResultsAdapter, showOnlyFavorite: Boolean) {
        if (showOnlyFavorite) {
            recyclerAdapter.setBooks(favoriteBooks)
        }
        else {
            recyclerAdapter.setBooks(allBooks)
        }
    }

    fun updateFilteredBooks() {
        val filteredBooks = mutableListOf<Book>()
        for (book in allBooks) {
            val isFavorite = BookServer.checkIfIsFavorite(book.id)
            if (isFavorite) {
                filteredBooks.add(book)
            }
        }
        favoriteBooks = filteredBooks
    }

    private fun setBooks(searchResult: SearchResult) {
        val allBooksList = mutableListOf<Book>()
        for (book in searchResult.items) {
            allBooksList.add(
                Book(
                    id = book.id,
                    title = book.volumeInfo.title,
                    author = book.volumeInfo.authors[0],
                    description = book.volumeInfo.description,
                    smallThumbnail = book.volumeInfo.imageLinks.smallThumbnail,
                    buyLink = book.saleInfo.buyLink
                )
            )
        }
        allBooks = allBooksList
        updateFilteredBooks()
    }
}