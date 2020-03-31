package th.ac.kku.cis.mobileapp.phamacyfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_item_phamacy.*
import kotlinx.android.synthetic.main.activity_add_phamacy.*

class addItemPhamacy : AppCompatActivity() {
    lateinit var phamacyList: MutableList<Phamacy>
    lateinit var mDatabase: DatabaseReference
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item_phamacy)
        if (supportActionBar != null) // เอาแถบบนออก
            supportActionBar?.hide()

        phamacyList = mutableListOf()
        //2 เพิ่มนักศึกษา--------------
        auth = FirebaseAuth.getInstance()
        // Id = auth.currentUser!!.uid
        mDatabase = FirebaseDatabase.getInstance().reference
        bt_addphamacyItem.setOnClickListener {
            AddDataPhamacy("String")
        }

    }
    fun AddDataPhamacy(data: String) {

        var name = getIntent().getStringExtra("name1")///////-----
        var newData: Phamacy = Phamacy.create()
        val obj = mDatabase.child("Phamacy").push()
        newData.NewName = name.toString()////////------
        newData.itemPha = editText2.text.toString()
        newData.cure = editText3.text.toString()
        newData.more = editText4.text.toString()
        newData.phamacyItemId = obj.key
        obj.setValue(newData)


        Toast.makeText(applicationContext,"Phamacy save successfully", Toast.LENGTH_LONG).show()
        var i = Intent(this, listPhamacy::class.java)
        i.putExtra("name1",name)//ส่งไป showstudent
        startActivity(i)//รีเฟรชกลับไปหน้าก่อนนี้เพื่อรีเฟรชfirebase มาแสดง
    }
}
