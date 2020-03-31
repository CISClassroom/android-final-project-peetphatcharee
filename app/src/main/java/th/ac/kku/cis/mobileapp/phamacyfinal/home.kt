package th.ac.kku.cis.mobileapp.phamacyfinal

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

import com.squareup.picasso.Picasso

class home : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (supportActionBar != null) // เอาแถบบนออก
            supportActionBar?.hide()
        //ล็อคอิน ดึงข้อมูลล็อคอินมาแสดง
        val NameSetting: TextView = findViewById(R.id.Username)
        val Profile: ImageView = findViewById(R.id.imageView2)
        val Email: TextView = findViewById(R.id.Email)
        auth = FirebaseAuth.getInstance()
        auth.currentUser!!.email
        val xx: Uri? = auth.currentUser!!.photoUrl
        NameSetting.text = auth.currentUser!!.displayName.toString()
        Picasso.get().load(xx).into(Profile)
        Email.text = auth.currentUser!!.email
        auth.currentUser!!.email

        //เชื่อมหน้า
        val goaddPhamacy: Button = findViewById(R.id.button)
        goaddPhamacy.setOnClickListener {

            var i = Intent(this, addPhamacy::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }
        val golistPhamacy: Button = findViewById(R.id.button2)
        golistPhamacy.setOnClickListener {

            var i = Intent(this, Listcategory::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }
    }
}
