package carlos.alves.books

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import carlos.alves.books.searchResults.SearchResultsActivity
import carlos.alves.books.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener {
            val searchResultIntent = Intent(this, SearchResultsActivity::class.java)
            val bookTitle = binding.searchBox.text.toString()
            searchResultIntent.putExtra("BOOK_TITLE", bookTitle)
            startActivity(searchResultIntent)
        }

        binding.exitButton.setOnClickListener {
            finishAffinity()
        }
    }
}