package th.ac.kku.cis.mobileapp.phamacyfinal

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

import com.squareup.picasso.Picasso

class home : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var Loggg: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (supportActionBar != null) // เอาแถบบนออก
            supportActionBar?.hide()


        //logout button
        val btlogout: FloatingActionButton = findViewById(R.id.floatingActionButton)
        btlogout.setOnClickListener({ v -> singOut() })
        //เชื่อมหน้า
        val goActivity: FloatingActionButton = findViewById(R.id.floatingActionButton)


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

        //เชื่อมหน้า ไปaddPhamacy
        val goaddPhamacy: Button = findViewById(R.id.button)
        goaddPhamacy.setOnClickListener {
            var i = Intent(this, addPhamacy::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }
        //ไปหมวดหมู่ยา  Listcategory
        val golistPhamacy: Button = findViewById(R.id.button2)
        golistPhamacy.setOnClickListener {
            var i = Intent(this, Listcategory::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }
    }
    //logout
    private fun passproject() {
        if (Loggg) {
            var i = Intent(this, MainActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }

    }
    private fun singOut() {
        auth.signOut()
        Loggg = true
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
                Loggg = false
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
                    Loggg = true
                    updateUI(user)
                } else {
                    Loggg = false
                    updateUI(null)
                }
            }
    }
}
