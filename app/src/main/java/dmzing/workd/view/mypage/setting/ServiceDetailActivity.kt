package dmzing.workd.view.mypage.setting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import dmzing.workd.R
import kotlinx.android.synthetic.main.activity_service_detail.*
import org.jetbrains.anko.toast

class ServiceDetailActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            serviceDetailBackBtn -> finish()
            serviceDetailXBtn -> finish()
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)

        init()
        getData()
    }

    private fun getData() {
        var data = intent.getStringExtra(ServiceActivity.DETAIL_TITLE)
        serviceDetailTitle.text = data.toString()
    }

    private fun init() {
        serviceDetailBackBtn.setOnClickListener(this)
        serviceDetailXBtn.setOnClickListener(this)
    }
}
