package carlos.alves.books.searchResults

data class SearchResult(val items: List<BookItem>)

data class BookItem(val id: String, val volumeInfo: VolumeInfo, val saleInfo: SaleInfo)

data class VolumeInfo(val title: String, val authors: List<String>, val description: String, val imageLinks: ImageLinks)

data class SaleInfo(val buyLink: String?)

data class ImageLinks(val smallThumbnail: String, val thumbnail: String)