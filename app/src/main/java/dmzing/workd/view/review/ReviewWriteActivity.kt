package dmzing.workd.view.review

import android.app.DatePickerDialog
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import dmzing.workd.R
import kotlinx.android.synthetic.main.activity_review_write.*
import java.util.jar.Manifest
import android.Manifest.permission
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dmzing.workd.model.review.ImageDto
import dmzing.workd.model.review.PostDto
import dmzing.workd.model.review.reviewDto
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import dmzing.workd.view.adapter.ReviewWriteDayAdapter
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


class ReviewWriteActivity : AppCompatActivity() {

    val START_DATE = 1
    val END_DATE = 2
    val GALLERY_CODE = 100
    val WRITE_REVIEW_CODE = 200

    lateinit var dayPost : ArrayList<PostDto>
    lateinit var reviewWrite : reviewDto
    lateinit var reviewWriteDayAdapter : ReviewWriteDayAdapter
    lateinit var networkService : NetworkService
    lateinit var jwt : String

    var thumbnailImageUrl : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_write)

        networkService = ApplicationController.instance!!.networkService
        jwt = SharedPreference.instance!!.getPrefStringData("jwt")!!

        review_complete_button.isEnabled = false
        if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    0)
        }

        review_write_start_date.setOnClickListener {
            showDialog(START_DATE)
        }

        review_write_end_date.setOnClickListener {
            showDialog(END_DATE)
        }

        review_write_close_button.setOnClickListener {
            finish()
        }

        review_write_add_thumbnail.setOnClickListener {
            selectThumbnail()
        }

        review_complete_button.setOnClickListener {
            if(dayPost.size == 0 || review_write_title.text.toString().length == 0|| thumbnailImageUrl == null || review_write_start_date.text.equals("START DATE") || review_write_end_date.text.equals("END DATE")){
                Toast.makeText(this,"리뷰를 모두 작성해주세요",Toast.LENGTH_LONG).show()
            } else {
                dayPost.sortWith(compareBy({it.day}))

                val title = review_write_title.text.toString()
                val courseId = intent.getIntExtra("courseId",0)
                var dateFormat = SimpleDateFormat("yyyy.mm.dd")
                val startDate = dateFormat.parse(review_write_start_date.text.toString())
                val endDate = dateFormat.parse(review_write_end_date.text.toString())

                reviewWrite = reviewDto(title,thumbnailImageUrl,courseId,startDate.time,endDate.time,dayPost)

                val postDetailReviewResponse = networkService.postDetailReview(jwt,reviewWrite)
                postDetailReviewResponse.enqueue(object : Callback<Any>{
                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        Log.d("reviewWrite:","Fail")
                    }

                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        when(response.code()){
                            201->{
                                Log.d("reviewWrite:","Success")
                                Toast.makeText(this@ReviewWriteActivity,"작성 완료 하였습니다.",Toast.LENGTH_LONG).show()
                                finish()
                            }
                            401->{
                                Log.d("reviewWrite:","401")
                            }
                            500->{
                                Log.d("reviewWrite:","500")
                            }
                        }
                    }

                })
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

    override fun onCreateDialog(id: Int): Dialog {
        //var date = System.currentTimeMillis()
        var calendar = Calendar.getInstance(Locale.KOREA)
        var datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            when(id){
                START_DATE ->{
                    //review_write_start_date.text = year.toString()+"."+(month+1)+"."+dayOfMonth.toString()
                    var dateStr = year.toString()+"."+(month+1)+"."+dayOfMonth.toString()
                    var before = SimpleDateFormat("yyyy.mm.dd").parse(dateStr)
                    val after = SimpleDateFormat("yyyy.mm.dd").format(before)
                    review_write_start_date.text = after.toString()
                    checkDate()
                }
                END_DATE->{
                    var dateStr = year.toString()+"."+(month+1)+"."+dayOfMonth.toString()
                    var before = SimpleDateFormat("yyyy.mm.dd").parse(dateStr)
                    Log.d("aaaaa",before.time.toString())
                    val after = SimpleDateFormat("yyyy.mm.dd").format(before)
                    review_write_end_date.text = after
                    checkDate()
                }
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))
        return datePickerDialog
    }

    fun findWritedDay(day : Int) : Int{
        for(i in 0 until dayPost.size){
            if(dayPost.get(i).day == day+1){
                return i
            }
        }
        return -1
    }

    fun checkDate(){
        if(!review_write_start_date.text.equals("START DATE") && !review_write_end_date.text.equals("END DATE")){
            var dateFormat : SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
            var startDate = review_write_start_date.text.toString()
            var endDate = review_write_end_date.text.toString()
            var start = dateFormat.parse(startDate)
            var end = dateFormat.parse(endDate)
            var dayCount = (end.time - start.time) / (24*60*60*1000)
            if(dayCount < 0){
                Toast.makeText(this,"날짜를 다시 설정해주세요.",Toast.LENGTH_LONG).show()
                review_write_start_date.text = "START DATE"
                review_write_end_date.text = "END DATE"
                review_write_choose_day_text.visibility = View.VISIBLE
                review_write_day_layout.visibility = View.GONE
            } else {
                dayCount = Math.abs(dayCount)
                setDaySelector(startDate,dayCount)
            }
        }
    }

    fun selectThumbnail(){
        var intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent,GALLERY_CODE)
    }

    fun setDaySelector(startDate : String,dayCount : Long){
        var dayList : ArrayList<String> = ArrayList()

        for(i in 1 .. dayCount+1){
            dayList.add("day "+i)
            Log.d("day","day"+i)
        }

        dayPost = ArrayList()

        reviewWriteDayAdapter = ReviewWriteDayAdapter(dayList,this)
        reviewWriteDayAdapter.setItemClickListener(object : ReviewWriteDayAdapter.ItemClick{
            override fun onClick(view: View, position: Int, dayText: TextView) {
                var intent = Intent(this@ReviewWriteActivity,WriteDayActivity::class.java)
                intent.putExtra("day",position)
                intent.putExtra("date",startDate)
                var writedDay = findWritedDay(position)
                if(writedDay != -1){ //작성한 날이면
                    intent.putExtra("writed",dayPost.get(writedDay))
                }
                startActivityForResult(intent,WRITE_REVIEW_CODE)
            }
        })
        review_write_day_recycler.layoutManager = LinearLayoutManager(this)
        review_write_day_recycler.adapter = reviewWriteDayAdapter

        review_write_choose_day_text.visibility = View.GONE
        review_write_day_layout.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            GALLERY_CODE -> {
                if(resultCode == Activity.RESULT_OK){
                    try {
                        var image : MultipartBody.Part?=null
                        var options = BitmapFactory.Options()
                        var inStream : InputStream = contentResolver.openInputStream(data!!.data)
                        var img : Bitmap = BitmapFactory.decodeStream(inStream)
                        inStream.close()

                        val baos = ByteArrayOutputStream()
                        img.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                        val photoBody = RequestBody.create(MediaType.parse("image/jpg"),baos.toByteArray())
                        val photo = File(data.toString())
                        image = MultipartBody.Part.createFormData("data",photo.name,photoBody)

                        //review_write_thumbnail.background = BitmapDrawable(img)
                        //이미지 통신
                        val postRegistImageResponse = networkService.postRegistImage(jwt,image)
                        postRegistImageResponse.enqueue(object : Callback<ImageDto> {
                            override fun onFailure(call: Call<ImageDto>, t: Throwable) {

                            }

                            override fun onResponse(call: Call<ImageDto>, response: Response<ImageDto>) {
                                when(response.code()){
                                    201->{
                                        Glide.with(this@ReviewWriteActivity).load(response.body()!!.image).apply(
                                            RequestOptions().centerCrop()
                                        ).into(review_write_thumbnail)
                                        thumbnailImageUrl = response.body()!!.image
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
            WRITE_REVIEW_CODE -> {
                if(resultCode == Activity.RESULT_OK){
                    var day = data!!.extras.getInt("day")
                    var dayText : TextView = review_write_day_recycler.layoutManager!!.findViewByPosition(day)!!.findViewById(R.id.review_day_select_text) as TextView
                    dayText.setTextColor(Color.parseColor("#1c3748"))
                    var writed = findWritedDay(day)
                    if(writed == -1){
                        dayPost.add(data!!.extras.get("PostDto") as PostDto)
                    } else {
                        dayPost.set(writed,data!!.extras.get("PostDto") as PostDto)
                    }
                    review_complete_button.setBackgroundColor(Color.parseColor("#6da8c7"))
                    review_complete_button.isEnabled = true
                    review_complete_text.setTextColor(Color.parseColor("#ffffff"))
                } else if(resultCode == Activity.RESULT_CANCELED){

                }
            }
        }
    }
}
