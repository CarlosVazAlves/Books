package carlos.alves.books.details

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import carlos.alves.books.R
import carlos.alves.books.database.BookServer
import carlos.alves.books.databinding.ActivityDetailsBinding


class DetailsActivity : AppCompatActivity() {

    private val binding: ActivityDetailsBinding by lazy { ActivityDetailsBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this).get(DetailsViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        BookServer.setContext(this)

        val notAvailableString = resources.getString(R.string.not_available)
        val id = intent.getStringExtra("ID")
        val thumbnailUrl = intent.getStringExtra("THUMBNAIL")

        binding.titleText.setText(intent.getStringExtra("TITLE").toString())
        binding.authorText.setText(intent.getStringExtra("AUTHOR").toString())
        binding.descriptionText.setText(intent.getStringExtra("DESCRIPTION").toString())
        binding.buyLinkText.setText(intent.getStringExtra("BUY_LINK") ?: notAvailableString)

        if (thumbnailUrl != null) {
            DownloadImage(binding.thumbnail).execute(thumbnailUrl)
        }

        val isFavorite = viewModel.checkIfIsFavorite(id!!)

        binding.favoriteSwitch.isChecked = isFavorite

        binding.buyLinkText.setOnClickListener {
            val url = binding.buyLinkText.text.toString()
            if (url != notAvailableString) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        }

        binding.favoriteSwitch.setOnClickListener {
            if (!binding.favoriteSwitch.isChecked) {
                viewModel.removeAsFavorite(id)
            }
            else {
                viewModel.addAsFavorite(id)
            }
        }

        binding.detailBackButton.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Suppress("DEPRECATION")
    private inner class DownloadImage(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val img = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(img)
            }
            catch (exception: Exception) {
                exception.printStackTrace()
            }
            return image
        }
        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }
}