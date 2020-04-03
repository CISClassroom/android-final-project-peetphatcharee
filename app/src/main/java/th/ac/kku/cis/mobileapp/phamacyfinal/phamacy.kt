package th.ac.kku.cis.mobileapp.phamacyfinal

//หมวดหมู่ยา
class CatePhamacy {
    companion object Factory {
        fun create(): CatePhamacy = CatePhamacy()
    }

    var phamacyId: String? = null
    var Namephamacy: String? = null

}

//ยา
class Phamacy {
    companion object Factory1 {
        fun create(): Phamacy = Phamacy()
    }

    var NewName: String? = null
    var phamacyItemId: String? = null
    var itemPha: String? = null
    var cure : String? = null
    var more: String? = null

}