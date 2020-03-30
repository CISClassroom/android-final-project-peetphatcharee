package th.ac.kku.cis.mobileapp.afinal

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

//----
class add : AppCompatActivity() {
    private val PERMISSION_CODE = 1000;
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null
    var newData: note = note.create()

    lateinit var mDB: DatabaseReference
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        if (supportActionBar != null)
            supportActionBar?.hide()


        photo.setOnClickListener { v ->
            //if system os is Marshmallow or Above, we need to request runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                ) {
                    //permission was not enabled
                    val permission = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    //show popup to request permission
                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    //permission already granted
                    openCamera()
                }
            } else {
                //system os is < marshmallow
                openCamera()
            }
        }
        //2 เพิ่ม
        auth = FirebaseAuth.getInstance()
        // Id = auth.currentUser!!.uid
        mDB = FirebaseDatabase.getInstance().reference
        save.setOnClickListener {
            AddData("String")
        }
    }
        fun AddData(data: String) {

            val obj = mDB.child("note").push()
            newData.Date = date.text.toString()
            newData.detail = d.text.toString()
            newData.title = t.text.toString()
            newData.noteId = obj.key
            obj.setValue(newData)

            Toast.makeText(applicationContext, "Note save successfully", Toast.LENGTH_LONG).show()
            //   var i = Intent(this, activity_home::class.java)

            finish()
        }
        //----


        private fun openCamera() {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, "New Picture")
            values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
            image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            //camera intent
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
            startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            //called when user presses ALLOW or DENY from Permission Request Popup
            when (requestCode) {
                PERMISSION_CODE -> {
                    if (grantResults.size > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED
                    ) {
                        //permission from popup was granted
                        openCamera()
                    } else {
                        //permission from popup was denied
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    //เพิ่มภาพ
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            //called when image was captured from camera intent
            if (resultCode == Activity.RESULT_OK) {
                //set image captured to image view
                imageView.setImageURI(image_uri)

            }
            uploadImageToFirebaseStorage()
        }


        private fun uploadImageToFirebaseStorage() {
            if (image_uri == null) return

            val filename = "images/" + UUID.randomUUID().toString()
            val storage = FirebaseStorage.getInstance()
            val ref = storage.getReference(filename)

            ref.putFile(image_uri!!)
                .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot?> {
                    // get the image Url of the file uploaded
                    ref.getDownloadUrl()
                        // .addOnCanceledListener {
                        //     Log.d("image","image: ${it.metadata?.path}")
                        // }
                        .addOnSuccessListener(OnSuccessListener<Uri> { uri ->
                            // getting image uri and converting into string
                            newData.image_uri = uri.toString()
                            //newData(image_uri.toString())
                            // newData.image_uri = it.metadata?.path
                        })
                })

        }
    }
