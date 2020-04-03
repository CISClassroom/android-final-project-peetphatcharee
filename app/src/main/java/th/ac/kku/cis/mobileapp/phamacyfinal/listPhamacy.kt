package th.ac.kku.cis.mobileapp.phamacyfinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_list_phamacy.*

class listPhamacy : AppCompatActivity() {
    lateinit var mDatabase: DatabaseReference
    lateinit var adapter: PhamacyAdapter
    private var listViewItems: ListView? = null
    var toDoPhamacyList: MutableList<Phamacy>? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_phamacy)
        if (supportActionBar != null) // เอาแถบบนออก
            supportActionBar?.hide()


        //เชื่อมหน้า ไป addItemPhamacy
        val goaddItemPhamacy: FloatingActionButton = findViewById(R.id.button)

        var name = getIntent().getStringExtra("name1") //รับ name1 จาก หน้านักศึกษา
        goaddItemPhamacy.setOnClickListener {
            var i = Intent(this, addItemPhamacy::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.putExtra("name1", name)
            startActivity(i)
        }
      // แสดง view2
      listViewItems = findViewById<View>(R.id.view2) as ListView
        toDoPhamacyList = mutableListOf<Phamacy>()
        adapter = PhamacyAdapter(this, toDoPhamacyList!!)
        listViewItems!!.setAdapter(adapter)

        mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase.child("Phamacy").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val items = dataSnapshot.children.iterator()

                var name = getIntent().getStringExtra("name1") //รับมาจาก หน้าแสดงหมวดหมู่

                // Check if current database contains any collection
                if (items.hasNext()) {
                    while (items.hasNext()) {
                        val toDoListindex = items.next()
                        val map = toDoListindex.getValue() as HashMap<String, Any>

                        if (map.get("newName") == name) {
                            // add data to object
                            val todoItem = Phamacy.create()
                            todoItem.NewName = map.get("newName") as String?
                            todoItem.itemPha = map.get("itemPha") as String?
                            todoItem.cure = map.get("cure") as String?
                            todoItem.more = map.get("more") as String?
                            toDoPhamacyList!!.add(todoItem);
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
//กลับไปหน้าหมวดหมู่
        val goback: FloatingActionButton = findViewById(R.id.floatingActionButton2)
        goback.setOnClickListener {
            var i = Intent(this, Listcategory::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }
        view2.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as Phamacy //ดึง
            //Toast.makeText(this,selectedItem,Toast.LENGTH_SHORT).show()
            val intent = Intent(this, detail::class.java)
            intent.putExtra("itemPha", selectedItem.itemPha)//ส่งไปยัง ListProduct
            intent.putExtra("cure", selectedItem.cure)//ส่งไปยัง ListProduct
            intent.putExtra("more", selectedItem.more)//ส่งไปยัง ListProduct
            startActivity(intent)
        }
    }

}
