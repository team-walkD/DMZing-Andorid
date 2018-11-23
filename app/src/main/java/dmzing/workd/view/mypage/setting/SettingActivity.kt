package dmzing.workd.view.mypage.setting

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import dmzing.workd.R
import dmzing.workd.dialog.LogoutDialog
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.startActivity

class SettingActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            settingServiceBtn -> startActivity<ServiceActivity>()
            settingLogoutBtn->{
                var dialog = LogoutDialog(this@SettingActivity)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setCanceledOnTouchOutside(true)
                dialog.show()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        init()
    }

    fun init() {
        settingServiceBtn.setOnClickListener(this)
        settingLogoutBtn.setOnClickListener(this)

    }
}
