package dmzing.workd.view.course

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dmzing.workd.R
import dmzing.workd.model.map.CourseDetailDto
import org.jetbrains.anko.toast

class CourseDetailActivity : AppCompatActivity() {

    lateinit var courseDetailDto : CourseDetailDto
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)

        courseDetailDto = intent.extras.get("courseDetailDto") as CourseDetailDto
        var index = intent.getIntExtra("idx",0)
        //toast("${index}")
    }

    fun setViewData(){

    }
}
