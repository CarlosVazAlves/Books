package carlos.alves.books.searchResults

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import carlos.alves.books.Book

class SearchResultsAdapter internal constructor(context: Context) : RecyclerView.Adapter<SearchResultsAdapter.ItemViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private var books = mutableListOf<Book>()
    private var currentBook: MutableLiveData<Book> = MutableLiveData()

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val booksTitle: TextView = itemView.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.booksTitle.text = books[position].title
        holder.itemView.setOnClickListener {
            currentBook.value = Book(
                id = books[position].id,
                title = books[position].title,
                author = books[position].author,
                description = books[position].description,
                smallThumbnail = books[position].smallThumbnail,
                buyLink = books[position].buyLink,
            )
        }
    }

    override fun getItemCount(): Int = books.size

    fun getCurrentBook(): MutableLiveData<Book> {
        return currentBook
    }

    fun setBooks(books: MutableList<Book>) {
        this.books = books
        notifyDataSetChanged()
    }
}