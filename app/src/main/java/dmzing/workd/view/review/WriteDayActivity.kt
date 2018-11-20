package dmzing.workd.view.review

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import dmzing.workd.R
import dmzing.workd.model.review.PostDto
import kotlinx.android.synthetic.main.activity_review_write.*
import kotlinx.android.synthetic.main.activity_write_day.*
import java.io.InputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WriteDayActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var startDate : String
    lateinit var imageList : ArrayList<ImageView>

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
        startDate = intent.getStringExtra("date")
        day = intent.getIntExtra("day",0)
        if(intent.extras.get("writed") != null){
            writed = intent.extras.get("writed") as PostDto
            setWritedValue()
        }
        setDefaultValue()
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
                var post = PostDto(day+1,null,write_day_title.text.toString(),write_day_text.text.toString())
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
                        var inStream : InputStream = contentResolver.openInputStream(data!!.data)
                        var img : Bitmap = BitmapFactory.decodeStream(inStream)
                        inStream.close()
                        write_day_image1.background = BitmapDrawable(img)
                        //이미지 통신
                    } catch (e : Exception){
                        e.printStackTrace()
                    }
                }
            }
            GALLERY_CODE_TWO->{
                if(resultCode == Activity.RESULT_OK){
                    try {
                        var inStream : InputStream = contentResolver.openInputStream(data!!.data)
                        var img : Bitmap = BitmapFactory.decodeStream(inStream)
                        inStream.close()
                        write_day_image2.background = BitmapDrawable(img)
                        //이미지 통신
                    } catch (e : Exception){
                        e.printStackTrace()
                    }
                }
            }
            GALLERY_CODE_THREE->{
                if(resultCode == Activity.RESULT_OK){
                    try {
                        var inStream : InputStream = contentResolver.openInputStream(data!!.data)
                        var img : Bitmap = BitmapFactory.decodeStream(inStream)
                        inStream.close()
                        write_day_image3.background = BitmapDrawable(img)
                        //이미지 통신
                    } catch (e : Exception){
                        e.printStackTrace()
                    }
                }
            }
            GALLERY_CODE_FOUR->{
                if(resultCode == Activity.RESULT_OK){
                    try {
                        var inStream : InputStream = contentResolver.openInputStream(data!!.data)
                        var img : Bitmap = BitmapFactory.decodeStream(inStream)
                        inStream.close()
                        write_day_image4.background = BitmapDrawable(img)
                        //이미지 통신
                    } catch (e : Exception){
                        e.printStackTrace()
                    }
                }
            }
            GALLERY_CODE_FIVE->{
                if(resultCode == Activity.RESULT_OK){
                    try {
                        var inStream : InputStream = contentResolver.openInputStream(data!!.data)
                        var img : Bitmap = BitmapFactory.decodeStream(inStream)
                        inStream.close()
                        write_day_image5.background = BitmapDrawable(img)
                        //이미지 통신
                    } catch (e : Exception){
                        e.printStackTrace()
                    }
                }
            }
            GALLERY_CODE_MULT->{
                if(resultCode == Activity.RESULT_OK){
                    try {
                        if(data!!.clipData == null){
                            var inStream : InputStream = contentResolver.openInputStream(data!!.data)
                            var img : Bitmap = BitmapFactory.decodeStream(inStream)
                            inStream.close()
                            imageList.get(imageCount).visibility = View.VISIBLE
                            imageList.get(imageCount).background = BitmapDrawable(img)
                            imageCount += 1

                            if(imageCount == 5){
                                write_day_add_image.visibility = View.GONE
                            }
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
}
