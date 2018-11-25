package dmzing.workd.view.map

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import dmzing.workd.R
import dmzing.workd.model.map.CourseDetailDto
import dmzing.workd.view.course.CourseDetailActivity
import dmzing.workd.view.review.ReviewActivity
import kotlinx.android.synthetic.main.dialog_course_order_result.*

class CourseOrderResultDialog(var courseDetailDto: CourseDetailDto?,context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //뒷 배경 투명
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.8f
        window!!.attributes = lpWindow

        setContentView(R.layout.dialog_course_order_result)

        if(courseDetailDto != null){
            dialog_course_order_result_title.text = courseDetailDto!!.title
        } else {
            dialog_course_order_result_success.visibility = View.GONE
            dialog_course_order_result_fail.visibility = View.VISIBLE
        }

        dialog_course_order_result_close.setOnClickListener {
            if(courseDetailDto != null){
                var intent = Intent(context,CourseDetailActivity::class.java)
                intent.putExtra("courseDetailDto",courseDetailDto)
                context.startActivity(intent)
                dismiss()
            } else {
                dismiss()
            }
        }
    }
}