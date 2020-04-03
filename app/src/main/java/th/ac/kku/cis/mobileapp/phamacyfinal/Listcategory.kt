package th.ac.kku.cis.mobileapp.phamacyfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_listcategory.*
//----------แสดงหมวดหมู่
class Listcategory : AppCompatActivity() {
    lateinit var mDatabase: DatabaseReference
    lateinit var adapter: CategoryAdapter
    private var listViewItems: ListView? = null
    var toDoItemList: MutableList<CatePhamacy>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listcategory)
        if (supportActionBar != null) // เอาแถบบนออก
            supportActionBar?.hide()


        //แสดงหมวดหมู่
        listViewItems = findViewById<View>(R.id.view1) as ListView
        toDoItemList = mutableListOf<CatePhamacy>()
        adapter = CategoryAdapter(this, toDoItemList!!)
        listViewItems!!.setAdapter(adapter)

        mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase.child("Categoryphamacy").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val items = dataSnapshot.children.iterator()
                // Check if current database contains any collection
                if (items.hasNext()) {
                    while (items.hasNext()) {
                        val toDoListindex = items.next()
                        val map = toDoListindex.getValue() as HashMap<String, Any>
                        // add data to object
                        val todoItem = CatePhamacy.create()
                        todoItem.Namephamacy = map.get("namephamacy") as String?
                        toDoItemList!!.add(todoItem);
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })


//คลิกประเภทยาจะไปหน้าแสดงรายการยา แต่ละหมวดหมู่
        view1.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as CatePhamacy
            //Toast.makeText(this,selectedItem,Toast.LENGTH_SHORT).show()
            val intent = Intent(this, listPhamacy::class.java)
            intent.putExtra("name1", selectedItem.Namephamacy)//ส่งค่าไปยัง listPhamacy
            startActivity(intent)
        }
    }
}
