package dmzing.workd.view.mypage.setting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import dmzing.workd.R
import kotlinx.android.synthetic.main.activity_service.*
import org.jetbrains.anko.startActivity

class ServiceActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            serviceDMZingBtn -> {
                titleValue = "DMZing 안내"
                startActivity<ServiceDetailActivity>(DETAIL_TITLE to titleValue)
            }
            serviceTermsBtn -> {
                titleValue = "이용 약관"
                startActivity<ServiceDetailActivity>(DETAIL_TITLE to titleValue)
            }
            servicePolicyBtn -> {
                titleValue = "개인정보 보호 정책"
                startActivity<ServiceDetailActivity>(DETAIL_TITLE to titleValue)
            }
            serviceBackBtn -> finish()
            serviceXBtn -> finish()
        }
    }

    private fun init() {
        servicePolicyBtn.setOnClickListener(this)
        serviceDMZingBtn.setOnClickListener(this)
        serviceTermsBtn.setOnClickListener(this)
        serviceXBtn.setOnClickListener(this)
        serviceBackBtn.setOnClickListener(this)
    }

    companion object {
        val DETAIL_TITLE: String = "title"
    }

    lateinit var titleValue: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        init()
    }
}
