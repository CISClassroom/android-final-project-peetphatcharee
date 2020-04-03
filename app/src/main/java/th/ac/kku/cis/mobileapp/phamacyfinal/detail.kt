package th.ac.kku.cis.mobileapp.phamacyfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*

class detail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        if (supportActionBar != null) // เอาแถบบนออก
            supportActionBar?.hide()

        var itemPha = getIntent().getStringExtra("itemPha")
        var cure = getIntent().getStringExtra("cure")
        var more = getIntent().getStringExtra("more")

        textView10.text = itemPha
        textView12.text = cure
        textView14.text = more
    }
}
