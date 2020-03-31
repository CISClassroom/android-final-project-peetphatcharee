package th.ac.kku.cis.mobileapp.phamacyfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class listPhamacy : AppCompatActivity() {


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

    }
}
