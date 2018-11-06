package dmzing.workd.view.mypage.setting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dmzing.workd.R
import kotlinx.android.synthetic.main.activity_service_detail.*
import org.jetbrains.anko.toast

class ServiceDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)
        getData()
    }

    fun getData(){
        var data = intent.getStringExtra(ServiceActivity.DETAIL_TITLE)
        serviceDetailTitle.text = data.toString()
    }
}
