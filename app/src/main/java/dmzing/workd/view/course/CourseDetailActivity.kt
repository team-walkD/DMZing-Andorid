package dmzing.workd.view.course

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dmzing.workd.R
import org.jetbrains.anko.toast

class CourseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)

        var index = intent.getIntExtra("idx",0)
        toast("${index}")
    }
}
