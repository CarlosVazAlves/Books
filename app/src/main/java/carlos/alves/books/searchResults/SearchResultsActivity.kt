package carlos.alves.books.searchResults

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import carlos.alves.books.*
import carlos.alves.books.database.BookServer
import carlos.alves.books.details.DetailsActivity
import carlos.alves.books.databinding.ActivitySearchResultsBinding


class SearchResultsActivity : AppCompatActivity() {

    private val binding: ActivitySearchResultsBinding by lazy { ActivitySearchResultsBinding.inflate(layoutInflater) }
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.book_results) }
    private val viewModel by lazy { ViewModelProvider(this).get(SearchResultsViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        BookServer.setContext(this)

        viewModel.bookTitle = intent.getStringExtra("BOOK_TITLE").toString()

        val recyclerAdapter = SearchResultsAdapter(this)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.fetchBooks(recyclerAdapter)

        recyclerAdapter.getCurrentBook().observe(this) {
            val detailsIntent = Intent(this, DetailsActivity::class.java)
            detailsIntent.putExtra("ID", it.id)
            detailsIntent.putExtra("TITLE", it.title)
            detailsIntent.putExtra("AUTHOR", it.author)
            detailsIntent.putExtra("DESCRIPTION", it.description)
            detailsIntent.putExtra("THUMBNAIL", it.smallThumbnail)
            detailsIntent.putExtra("BUY_LINK", it.buyLink)
            startActivity(detailsIntent)
        }

        binding.filterSwitch.setOnClickListener {
            viewModel.setFilter(recyclerAdapter, binding.filterSwitch.isChecked)
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateFilteredBooks()
    }
}