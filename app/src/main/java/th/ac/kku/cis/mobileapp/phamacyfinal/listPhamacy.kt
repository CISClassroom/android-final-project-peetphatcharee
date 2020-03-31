package th.ac.kku.cis.mobileapp.phamacyfinal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
//


        listViewItems = findViewById<View>(R.id.view2) as ListView
        toDoPhamacyList = mutableListOf<Phamacy>()
        adapter = PhamacyAdapter(this, toDoPhamacyList!!)
        listViewItems!!.setAdapter(adapter)

        mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase.child("Phamacy").addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val items = dataSnapshot.children.iterator()

                var name = getIntent().getStringExtra("name1") //รับมา

                // Check if current database contains any collection
                if (items.hasNext()) {
                    while (items.hasNext()) {
                        val toDoListindex = items.next()
                        val map = toDoListindex.getValue() as HashMap<String, Any>

                        if (map.get("newName") == name) {
                            // add data to object
                            val todoItem = Phamacy.create()
                          //  todoItem.NewName = map.get("newName") as String?
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

    }
}
