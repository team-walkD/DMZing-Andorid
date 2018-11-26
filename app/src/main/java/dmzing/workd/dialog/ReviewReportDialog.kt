package dmzing.workd.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import dmzing.workd.R
import dmzing.workd.model.review.ReportDto
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import kotlinx.android.synthetic.main.dialog_review_report.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewReportDialog(var rid : Int,var reportType : String,context : Context) : Dialog(context) {
    lateinit var networkService : NetworkService
    lateinit var jwt : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkService = ApplicationController.instance.networkService

        jwt = SharedPreference.instance!!.getPrefStringData("jwt", "")!!

        //뒷 배경 투명
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.8f
        window!!.attributes = lpWindow

        setContentView(R.layout.dialog_review_report)

        dialog_review_report_close.setOnClickListener {
            dismiss()
        }
        dialog_review_report.setOnClickListener {
            reviewReport(rid,reportType)
        }

    }

    fun reviewReport(rid : Int,reportType : String){
        var reportDto  = ReportDto(rid,reportType,null)
        val reportResponse = networkService.postReportReview(jwt,reportDto)
        reportResponse.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {

            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                when(response.code()){
                    201->{
                        Toast.makeText(context,"접수되었습니다.",Toast.LENGTH_LONG).show()
                        dismiss()
                    }
                    400->{

                    }
                    500->{

                    }
                }
            }

        })
    }
}