package dmzing.workd.view.map

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dmzing.workd.R
import dmzing.workd.model.map.CourseDetailDto
import dmzing.workd.view.course.CourseDetailActivity
import kotlinx.android.synthetic.main.activity_course_main.*

class CourseMainActivity : AppCompatActivity() {

    lateinit var courseDetailDto : CourseDetailDto
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_main)

        courseDetailDto = intent.extras.get("courseDetailDto") as CourseDetailDto

        Glide.with(this)
                .load(courseDetailDto.imageUrl)
                //.apply(RequestOptions().override(resources.displayMetrics.widthPixels,resources.displayMetrics.heightPixels).centerCrop())
                .apply(RequestOptions().centerCrop())
                .into(course_main_background_image)
        course_main_background_image.setColorFilter(Color.parseColor("#7f000000"), PorterDuff.Mode.SRC_OVER)

        course_main_subDescrip.text = courseDetailDto.subDescription
        course_main_title.text = courseDetailDto.title

        course_main_pick_count.text = courseDetailDto.pickCount.toString()+"명이 PICK한 그 곳"

        course_main_more.setOnClickListener {//보러가기
            var intent = Intent(this,CourseDetailActivity::class.java)
            intent.putExtra("courseDetailDto",courseDetailDto)
            startActivity(intent)
        }

        course_main_back.setOnClickListener {
            finish()
        }

        course_main_walk_d.setOnClickListener {//챗봇

        }

    }
}
