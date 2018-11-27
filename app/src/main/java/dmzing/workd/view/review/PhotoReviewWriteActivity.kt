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
import android.util.Log
import android.view.View
import android.widget.Toast
import dmzing.workd.R
import dmzing.workd.model.review.ImageDto
import dmzing.workd.model.review.PhotoReviewDto
import dmzing.workd.model.review.WritePhotoReviewDto
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import kotlinx.android.synthetic.main.activity_photo_review_write.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class PhotoReviewWriteActivity : AppCompatActivity() {
    val GALLERY_CODE = 100
    var imageUrl : String? = null
    lateinit var writePhotoReviewDto : WritePhotoReviewDto
    lateinit var networkService : NetworkService
    lateinit var jwt : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_review_write)
        networkService = ApplicationController.instance!!.networkService
        jwt = SharedPreference.instance!!.getPrefStringData("jwt")!!

        var place = intent.getStringExtra("place")
        var courseId = intent.getIntExtra("courseId",0)

        when(courseId){
            4->{
                photo_review_write_map.text = "데이트 맵"
            }
            2->{
                photo_review_write_map.text = "역사기행 맵"
            }
            3->{
                photo_review_write_map.text = "자연탐방 맵"
            }
            1->{
                photo_review_write_map.text = "DMZ탐방 맵"
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
            if(imageUrl == null){
                Toast.makeText(this,"사진을 선택해주세요",Toast.LENGTH_LONG).show()
            } else {
                writePhotoReviewDto = WritePhotoReviewDto(date.timeInMillis,place,"",courseId,imageUrl)
                val postPhotoReviewResponse = networkService.postPhotoReview(jwt,writePhotoReviewDto)
                postPhotoReviewResponse.enqueue(object : Callback<Any>{
                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        Log.d("photoReview","Fail")
                    }

                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        when(response.code()){
                            201->{
                                Toast.makeText(this@PhotoReviewWriteActivity,"사진 리뷰를 작성하였습니다",Toast.LENGTH_LONG).show()
                                finish()
                                Log.d("photoReview","Succ")
                            }
                            401->{

                            }
                            500->{

                            }
                        }
                    }

                })
            }
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
                    var image : MultipartBody.Part?=null
                    var inStream : InputStream = contentResolver.openInputStream(data!!.data)
                    var options = BitmapFactory.Options()
                    var img : Bitmap = BitmapFactory.decodeStream(inStream,null,options)
                    inStream.close()

                    val baos = ByteArrayOutputStream()
                    img.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                    val photoBody = RequestBody.create(MediaType.parse("image/jpg"),baos.toByteArray())
                    val photo = File(data!!.data.toString())

                    image = MultipartBody.Part.createFormData("data",photo.name,photoBody)

                    val registPhotoResponse = networkService.postRegistImage(jwt,image)
                    registPhotoResponse.enqueue(object : Callback<ImageDto>{
                        override fun onFailure(call: Call<ImageDto>, t: Throwable) {

                        }

                        override fun onResponse(call: Call<ImageDto>, response: Response<ImageDto>) {
                            when(response.code()){
                                201->{
                                    imageUrl = response.body()!!.image
                                    Log.d("photoReview",imageUrl)

                                    photo_review_write_image.background = BitmapDrawable(img)
                                    photo_review_write_plus_ic.visibility = View.GONE
                                }
                                401->{

                                }
                                501->{

                                }
                            }
                        }

                    })
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
