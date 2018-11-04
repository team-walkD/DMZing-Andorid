package dmzing.workd.view.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import dmzing.workd.R
import dmzing.workd.util.Utils
import kotlinx.android.synthetic.main.activity_sign_up_two.*

class SignUpTwoActivity : AppCompatActivity(), View.OnClickListener, Utils {
    override fun onClick(v: View?) {
        when(v){
            signUpTwoNextBtn-> startActivity(Intent(this, SignUpThreeActivity::class.java))
        }
    }

    override fun init() {
        signUpTwoNextBtn.setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_two)
        init()
    }
}
