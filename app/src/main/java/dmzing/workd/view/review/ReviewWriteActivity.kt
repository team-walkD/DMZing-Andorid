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
import dmzing.workd.model.review.PostDto
import dmzing.workd.model.review.reviewDto
import dmzing.workd.view.adapter.ReviewWriteDayAdapter
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_write)

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
            dayPost.sortWith(compareBy({it.day}))

            val title = review_write_title.text.toString()
            //val thumbnail =
            //val courseId =
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
        },2018,10,1)
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
            setDaySelector(review_write_start_date.text.toString(),review_write_end_date.text.toString())
        }
    }

    fun selectThumbnail(){
        var intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent,GALLERY_CODE)
    }

    fun setDaySelector(startDate : String, endDate : String){
        var dateFormat : SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
        var start = dateFormat.parse(startDate)
        var end = dateFormat.parse(endDate)
        var dayList : ArrayList<String> = ArrayList()

        var dayCount = (end.time - start.time) / (24*60*60*1000)
        dayCount = Math.abs(dayCount)
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
                        var inStream : InputStream = contentResolver.openInputStream(data!!.data)
                        var img : Bitmap = BitmapFactory.decodeStream(inStream)
                        inStream.close()
                        review_write_thumbnail.background = BitmapDrawable(img)
                        //이미지 통신
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
                } else if(resultCode == Activity.RESULT_CANCELED){

                }
            }
        }
    }
}
