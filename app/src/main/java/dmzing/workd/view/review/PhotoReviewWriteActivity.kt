package dmzing.workd.view.review

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import dmzing.workd.R
import kotlinx.android.synthetic.main.activity_photo_review_write.*
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class PhotoReviewWriteActivity : AppCompatActivity() {
    val GALLERY_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_review_write)

        var place = intent.getStringExtra("place")
        var courseId = intent.getIntExtra("courseId",0)

        when(courseId){
            1->{
                photo_review_write_map.text = "데이트 맵"
            }
        }

        var date = Calendar.getInstance()
        var format = SimpleDateFormat("yyyy. MM. dd")
        photo_review_write_date.text = format.format(date.time)

        if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    0)
        }

        photo_review_write_add.setOnClickListener {
            selectImage()
        }

        photo_review_write_complete.setOnClickListener {

        }

        photo_review_write_close.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            GALLERY_CODE->{
                if(resultCode == Activity.RESULT_OK){
                    var inStream : InputStream = contentResolver.openInputStream(data!!.data)
                    var img : Bitmap = BitmapFactory.decodeStream(inStream)
                    inStream.close()

                    photo_review_write_image.background = BitmapDrawable(img)
                    photo_review_write_plus_ic.visibility = View.GONE
                } else {

                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            0->{
                for(i in 0 until grantResults.size-1){
                    if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                        finish()
                    }
                }
            }
        }
    }

    fun selectImage(){
        var intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent,GALLERY_CODE)
    }
}
