package th.ac.kku.cis.mobileapp.afinal

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class activity_home : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var googleClient: GoogleSignInClient
    var newpropro: Boolean = false


//    lateinit var listView: ListView
//    lateinit var ref: DatabaseReference
//    lateinit var items:MutableList<note>


    lateinit var mDatabase: DatabaseReference
    lateinit var adapter: ToDoItemAdapter
    private var listViewItems: ListView? = null
    var toDoItemList: MutableList<note>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (supportActionBar != null) // เอาแถบบนออก
            supportActionBar?.hide()
        //เชื่อมหน้า
        val goActivity: FloatingActionButton = findViewById(R.id.floatingActionButton)

//        var name = getIntent().getStringExtra("name1") //รับ name1 จาก หน้ากิจกกรรม
        goActivity.setOnClickListener {
            var i = Intent(this, add::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            i.putExtra("name1", name)
            startActivity(i)

        }


//ล็อคอิน ดึงข้อมูลล็อคอินมาแสดง
        val NameSetting: TextView = findViewById(R.id.name)
        val Profile: ImageView = findViewById(R.id.image)
        val Email: TextView = findViewById(R.id.mail)
        auth = FirebaseAuth.getInstance()
        auth.currentUser!!.email
        val xx: Uri? = auth.currentUser!!.photoUrl
        NameSetting.text = auth.currentUser!!.displayName.toString()
        Picasso.get().load(xx).into(Profile)
        Email.text = auth.currentUser!!.email



//logout button
        val btlogout: FloatingActionButton = findViewById(R.id.sigout)
        btlogout.setOnClickListener({ v -> singOut() })
    }
        private fun passproject() {
            if (newpropro) {
                var i = Intent(this, MainActivity::class.java)
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }

        }
    //logout
        private fun singOut() {
            auth.signOut()
            newpropro = true
            passproject()
        }

        private fun updateUI(user: FirebaseUser?) {
            if (user == null) {
                //show.text = "No User"
            } else {
                //show.text = user.email.toString()
                passproject()
            }

        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == 101) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuth(account!!)
                    //FirebaseAuth(account)
                } catch (e: ApiException) {
                    Log.i("Error OOP", e.toString())
                    newpropro = false
                    updateUI(null)
                }
            }
        }

        private fun firebaseAuth(account: GoogleSignInAccount) {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        newpropro = true
                        updateUI(user)
                    } else {
                        newpropro = false
                        updateUI(null)
                    }
                }


//            //แสดง note-----------
//            listViewItems = findViewById<View>(R.id.view) as ListView
//            toDoItemList = mutableListOf<note>()
//            adapter = ToDoItemAdapter(this, toDoItemList!!)
//            listViewItems!!.setAdapter(adapter)
//
//            mDatabase = FirebaseDatabase.getInstance().reference
//            mDatabase.child("Note").addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    val items = dataSnapshot.children.iterator()
//                    // Check if current database contains any collection
//                    if (items.hasNext()) {
//                        while (items.hasNext()) {
//                            val toDoListindex = items.next()
//                            val map = toDoListindex.getValue() as HashMap<String, Any>
//                            // add data to object
//                            val todoItem = note.create()
//                            todoItem.Date = map.get("Date") as String?
//                            todoItem.detail = map.get("detail") as String?
//                            todoItem.title = map.get("title") as String?
//                            toDoItemList!!.add(todoItem);
//                            adapter.notifyDataSetChanged()
//                        }
//                    }
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                }
//            })
//            //-------------------
        }

    }
















