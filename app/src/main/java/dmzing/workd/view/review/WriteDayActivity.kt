package dmzing.workd.view.review

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dmzing.workd.R
import dmzing.workd.model.review.ImageDto
import dmzing.workd.model.review.PostDto
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import kotlinx.android.synthetic.main.activity_photo_review_write.*
import kotlinx.android.synthetic.main.activity_review_write.*
import kotlinx.android.synthetic.main.activity_write_day.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WriteDayActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var startDate : String
    lateinit var imageList : ArrayList<ImageView>
    lateinit var imageUrlList : ArrayList<String>
    lateinit var networkService : NetworkService
    lateinit var jwt : String

    var imageCount = 0
    var day : Int = 0
    var writed : PostDto? = null
    val GALLERY_CODE_MULT = 100
    val GALLERY_CODE_ONE = 101
    val GALLERY_CODE_TWO = 102
    val GALLERY_CODE_THREE = 103
    val GALLERY_CODE_FOUR = 104
    val GALLERY_CODE_FIVE = 105


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_day)
        networkService = ApplicationController.instance!!.networkService
        imageUrlList = ArrayList()
        jwt = SharedPreference.instance!!.getPrefStringData("jwt")!!
        startDate = intent.getStringExtra("date")
        day = intent.getIntExtra("day",0)
        setDefaultValue()
        if(intent.extras.get("writed") != null){
            writed = intent.extras.get("writed") as PostDto
            setWritedValue()
        }
    }

    override fun onClick(v: View?) {
        when(v){
            write_day_image1 -> {
                selectImage(false)
            }
            write_day_image2 -> {
                selectImage(false)
            }
            write_day_image3 -> {
                selectImage(false)
            }
            write_day_image4 -> {
                selectImage(false)
            }
            write_day_image5 -> {
                selectImage(false)
            }
            write_day_add_image -> {
                selectImage(true)
            }
            write_day_complete -> {
                var returnIntent : Intent = Intent()
                var post = PostDto(day+1,imageUrlList,write_day_title.text.toString(),write_day_text.text.toString())
                returnIntent.putExtra("day",day)
                returnIntent.putExtra("PostDto",post)
                setResult(Activity.RESULT_OK,returnIntent)
                finish()
            }
            write_day_back -> {
                var returnIntent : Intent = Intent()
                returnIntent.putExtra("day",day)
                setResult(Activity.RESULT_CANCELED,returnIntent)
                finish()
            }
        }
    }

    fun setWritedValue(){
        write_day_title.setText(writed!!.title)
        write_day_text.setText(writed!!.content)
        for(i in 0 until writed!!.postImgUrl!!.size){
            imageUrlList.add(writed!!.postImgUrl!!.get(i))
            imageList.get(i).visibility = View.VISIBLE
            Glide.with(this).load(writed!!.postImgUrl!!.get(i)).apply(RequestOptions().centerCrop()).into(imageList.get(i))
            if(i == 4){
                write_day_add_image.visibility = View.GONE
            }
        }
    }

    fun setDefaultValue(){
        write_day_complete.setOnClickListener(this)
        write_day_back.setOnClickListener(this)
        write_day_image1.setOnClickListener(this)
        write_day_image2.setOnClickListener(this)
        write_day_image3.setOnClickListener(this)
        write_day_image4.setOnClickListener(this)
        write_day_image5.setOnClickListener(this)
        write_day_add_image.setOnClickListener(this)



        var simpleFormat : SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
        var cal = Calendar.getInstance()
        cal.time = simpleFormat.parse(startDate)
        cal.add(Calendar.DAY_OF_MONTH,day)
        write_day_day.text = "DAY "+(day+1)
        write_day_date.text = simpleFormat.format(cal.time)

        imageList = ArrayList()
        var image : ImageView = findViewById(R.id.write_day_image1)
        imageList.add(image)
        image = findViewById(R.id.write_day_image2)
        imageList.add(image)
        image = findViewById(R.id.write_day_image3)
        imageList.add(image)
        image = findViewById(R.id.write_day_image4)
        imageList.add(image)
        image = findViewById(R.id.write_day_image5)
        imageList.add(image)
    }

    fun selectImage(mult : Boolean){
        var intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        if(mult){
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(intent,GALLERY_CODE_MULT)
        } else {
            startActivityForResult(intent,GALLERY_CODE_ONE)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            GALLERY_CODE_ONE->{
                if(resultCode == Activity.RESULT_OK){
                    try {
//                        var inStream : InputStream = contentResolver.openInputStream(data!!.data)
//                        var img : Bitmap = BitmapFactory.decodeStream(inStream)
//                        inStream.close()
//                        write_day_image1.background = BitmapDrawable(img)
                        var image = getImageData(data!!.data)
                        //이미지 통신
                        val registPhotoResponse = networkService.postRegistImage(jwt,image)
                        registPhotoResponse.enqueue(object : Callback<ImageDto> {
                            override fun onFailure(call: Call<ImageDto>, t: Throwable) {

                            }

                            override fun onResponse(call: Call<ImageDto>, response: Response<ImageDto>) {
                                when(response.code()){
                                    201->{
                                        imageUrlList.set(0,response.body()!!.image)
                                        Glide.with(this@WriteDayActivity).load(response.body()!!.image).apply(RequestOptions().centerCrop()).into(write_day_image1)
                                    }
                                    401->{

                                    }
                                    501->{

                                    }
                                }
                            }

                        })
                    } catch (e : Exception){
                        e.printStackTrace()
                    }
                }
            }
            GALLERY_CODE_TWO->{
                if(resultCode == Activity.RESULT_OK){
                    try {
//                        var inStream : InputStream = contentResolver.openInputStream(data!!.data)
//                        var img : Bitmap = BitmapFactory.decodeStream(inStream)
//                        inStream.close()
//                        write_day_image2.background = BitmapDrawable(img)
                        //이미지 통신
                        var image = getImageData(data!!.data)
                        //이미지 통신
                        val registPhotoResponse = networkService.postRegistImage(jwt,image)
                        registPhotoResponse.enqueue(object : Callback<ImageDto> {
                            override fun onFailure(call: Call<ImageDto>, t: Throwable) {

                            }

                            override fun onResponse(call: Call<ImageDto>, response: Response<ImageDto>) {
                                when(response.code()){
                                    201->{
                                        imageUrlList.set(1,response.body()!!.image)
                                        Glide.with(this@WriteDayActivity).load(response.body()!!.image).apply(RequestOptions().centerCrop()).into(write_day_image2)
                                    }
                                    401->{

                                    }
                                    501->{

                                    }
                                }
                            }

                        })
                    } catch (e : Exception){
                        e.printStackTrace()
                    }
                }
            }
            GALLERY_CODE_THREE->{
                if(resultCode == Activity.RESULT_OK){
                    try {
//                        var inStream : InputStream = contentResolver.openInputStream(data!!.data)
//                        var img : Bitmap = BitmapFactory.decodeStream(inStream)
//                        inStream.close()
//                        write_day_image3.background = BitmapDrawable(img)
                        //이미지 통신
                        var image = getImageData(data!!.data)
                        //이미지 통신
                        val registPhotoResponse = networkService.postRegistImage(jwt,image)
                        registPhotoResponse.enqueue(object : Callback<ImageDto> {
                            override fun onFailure(call: Call<ImageDto>, t: Throwable) {

                            }

                            override fun onResponse(call: Call<ImageDto>, response: Response<ImageDto>) {
                                when(response.code()){
                                    201->{
                                        imageUrlList.set(2,response.body()!!.image)
                                        Glide.with(this@WriteDayActivity).load(response.body()!!.image).apply(RequestOptions().centerCrop()).into(write_day_image3)
                                    }
                                    401->{

                                    }
                                    501->{

                                    }
                                }
                            }

                        })
                    } catch (e : Exception){
                        e.printStackTrace()
                    }
                }
            }
            GALLERY_CODE_FOUR->{
                if(resultCode == Activity.RESULT_OK){
                    try {
//                        var inStream : InputStream = contentResolver.openInputStream(data!!.data)
//                        var img : Bitmap = BitmapFactory.decodeStream(inStream)
//                        inStream.close()
//                        write_day_image4.background = BitmapDrawable(img)
                        var image = getImageData(data!!.data)
                        //이미지 통신
                        val registPhotoResponse = networkService.postRegistImage(jwt,image)
                        registPhotoResponse.enqueue(object : Callback<ImageDto> {
                            override fun onFailure(call: Call<ImageDto>, t: Throwable) {

                            }

                            override fun onResponse(call: Call<ImageDto>, response: Response<ImageDto>) {
                                when(response.code()){
                                    201->{
                                        imageUrlList.set(3,response.body()!!.image)
                                        Glide.with(this@WriteDayActivity).load(response.body()!!.image).apply(RequestOptions().centerCrop()).into(write_day_image4)
                                    }
                                    401->{

                                    }
                                    501->{

                                    }
                                }
                            }

                        })
                        //이미지 통신
                    } catch (e : Exception){
                        e.printStackTrace()
                    }
                }
            }
            GALLERY_CODE_FIVE->{
                if(resultCode == Activity.RESULT_OK){
                    try {
//                        var inStream : InputStream = contentResolver.openInputStream(data!!.data)
//                        var img : Bitmap = BitmapFactory.decodeStream(inStream)
//                        inStream.close()
//                        write_day_image5.background = BitmapDrawable(img)
                        //이미지 통신
                        var image = getImageData(data!!.data)
                        //이미지 통신
                        val registPhotoResponse = networkService.postRegistImage(jwt,image)
                        registPhotoResponse.enqueue(object : Callback<ImageDto> {
                            override fun onFailure(call: Call<ImageDto>, t: Throwable) {

                            }

                            override fun onResponse(call: Call<ImageDto>, response: Response<ImageDto>) {
                                when(response.code()){
                                    201->{
                                        imageUrlList.set(4,response.body()!!.image)
                                        Glide.with(this@WriteDayActivity).load(response.body()!!.image).apply(RequestOptions().centerCrop()).into(write_day_image5)
                                    }
                                    401->{

                                    }
                                    501->{

                                    }
                                }
                            }

                        })
                    } catch (e : Exception){
                        e.printStackTrace()
                    }
                }
            }
            GALLERY_CODE_MULT->{
                if(resultCode == Activity.RESULT_OK){
                    try {
                        if(data!!.clipData == null){
//                            var inStream : InputStream = contentResolver.openInputStream(data!!.data)
//                            var img : Bitmap = BitmapFactory.decodeStream(inStream)
//                            inStream.close()

                            var image = getImageData(data!!.data)
                            //이미지 통신

//                            val testRequest = networkService.postTestImage(jwt,image)
                            //val testResponse = testRequest.await()

                            Log.d("reviewWrite : ","clipData null")
                            val registPhotoResponse = networkService.postRegistImage(jwt,image)
                            registPhotoResponse.enqueue(object : Callback<ImageDto> {
                                override fun onFailure(call: Call<ImageDto>, t: Throwable) {

                                }

                                override fun onResponse(call: Call<ImageDto>, response: Response<ImageDto>) {
                                    when(response.code()){
                                        201->{
                                            imageUrlList.add(response.body()!!.image)
                                            Log.d("reviewWrite :",imageUrlList.size.toString())
                                            Glide.with(this@WriteDayActivity).load(response.body()!!.image).apply(RequestOptions().centerCrop()).into(imageList.get(imageCount))
                                            imageList.get(imageCount).visibility = View.VISIBLE
                                            imageCount += 1
                                            if(imageCount == 5){
                                                write_day_add_image.visibility = View.GONE
                                            }
                                        }
                                        401->{

                                        }
                                        501->{

                                        }
                                    }
                                }

                            })

                        } else {
                            Log.d("aaaa",data!!.clipData.itemCount.toString())
                            if(data!!.clipData.itemCount > 5 - imageCount){
                                Toast.makeText(this,"사진은 "+(5-imageCount)+"개까지 선택 가능합니다.",Toast.LENGTH_LONG)
                            } else {
                                for(i in 0 until data!!.clipData.itemCount){
                                    var inStream : InputStream = contentResolver.openInputStream(data!!.clipData.getItemAt(i).uri)
                                    var img : Bitmap = BitmapFactory.decodeStream(inStream)
                                    inStream.close()
                                    imageList.get(imageCount+i).visibility = View.VISIBLE
                                    imageList.get(imageCount+i).background = BitmapDrawable(img)

                                }
                                imageCount += data!!.clipData.itemCount

                                if(imageCount == 5){
                                    write_day_add_image.visibility = View.GONE
                                }
                            }
                        }
                    } catch (e : Exception){
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun getImageData(data : Uri) : MultipartBody.Part{
        var image : MultipartBody.Part?=null
        var inStream : InputStream = contentResolver.openInputStream(data)
        var options = BitmapFactory.Options()
        var img : Bitmap = BitmapFactory.decodeStream(inStream,null,options)
        inStream.close()

        val baos = ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.JPEG, 20, baos)
        val photoBody = RequestBody.create(MediaType.parse("image/jpg"),baos.toByteArray())
        val photo = File(data.toString())

        image = MultipartBody.Part.createFormData("data",photo.name,photoBody)

        return image
    }
}
