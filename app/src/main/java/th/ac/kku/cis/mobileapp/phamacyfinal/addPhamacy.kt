package th.ac.kku.cis.mobileapp.phamacyfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_phamacy.*

class addPhamacy : AppCompatActivity() {
    lateinit var phamacyList: MutableList<CatePhamacy>
    lateinit var mDatabase: DatabaseReference
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_phamacy)
        if (supportActionBar != null) // เอาแถบบนออก
            supportActionBar?.hide()



        phamacyList = mutableListOf()
        //2
        auth = FirebaseAuth.getInstance()
        // Id = auth.currentUser!!.uid
        mDatabase = FirebaseDatabase.getInstance().reference
        bt_add.setOnClickListener {
            AddData("String")
        }

    }
    //เพิ่มประเภทยา
    fun AddData(data: String) {
        var newData: CatePhamacy = CatePhamacy.create()
        val obj = mDatabase.child("Categoryphamacy").push()
        newData.Namephamacy= editText.text.toString()
        newData.phamacyId = obj.key
        obj.setValue(newData)


        Toast.makeText(applicationContext,"Phamacy save successfully", Toast.LENGTH_LONG).show()
        finish()//กลับไปหน้าก่อนนี้

    }
}
