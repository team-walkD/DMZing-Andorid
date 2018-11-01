package dmzing.workd.view.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import dmzing.workd.R
import dmzing.workd.util.Utils
import kotlinx.android.synthetic.main.activity_sign_up_one.*

class SignUpOneActivity : AppCompatActivity(), View.OnClickListener, Utils {

    // 초기화
    override fun init() {
        signUpOneNextBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!){
            signUpOneNextBtn-> startActivity(Intent(this, SignUpTwoActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_one)
        init()
    }
}
