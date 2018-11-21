package dmzing.workd.view.map

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import dmzing.workd.R
import dmzing.workd.model.map.CourseDetailDto
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import kotlinx.android.synthetic.main.dialog_course_order.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseOrderDialog(var courseId : Int,var courseTitle : String,context : Context) : Dialog(context){
    lateinit var courseDetailDto: CourseDetailDto
    lateinit var networkService : NetworkService
    val jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdXRob3JpdHkiOiJVU0VSIiwiaXNzIjoiZG16aW5nIiwiZXhwIjoxNTUxMjQ3Mzk3LCJlbWFpbCI6ImV4YW1wbGVAZ21haWwuY29tIn0.fZ7C8_U9_p02cr6koo_kppY2L_0sIZCUJBu0KW4834c"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //뒷 배경 투명
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.8f
        window!!.attributes = lpWindow

        setContentView(R.layout.dialog_course_order)

        networkService = ApplicationController.instance!!.networkService

        dialog_course_title.text = courseTitle

        dialog_course_order_purchase.setOnClickListener {
            val getOrderCourseResponse = networkService.postCourseOrder(jwt,courseId)
            getOrderCourseResponse.enqueue(object : Callback<CourseDetailDto> {
                override fun onFailure(call: Call<CourseDetailDto>, t: Throwable) {

                }

                override fun onResponse(call: Call<CourseDetailDto>, response: Response<CourseDetailDto>) {
                    when(response.code()){
                        201->{
                            courseDetailDto = response.body()!!
                        }
                        401->{

                        }
                        500->{

                        }
                    }
                }

            })
        }
        dialog_course_order_close.setOnClickListener {
            dismiss()
        }
    }

}