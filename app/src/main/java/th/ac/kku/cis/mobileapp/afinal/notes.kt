package th.ac.kku.cis.mobileapp.afinal

//1
class note{
    companion object Factory {
        fun create(): note = note()
    }

    var noteId: String? = null

    var Date: String? = null
    var detail: String? = null
    var title: String? = null
    var image_uri: String? = null
}
