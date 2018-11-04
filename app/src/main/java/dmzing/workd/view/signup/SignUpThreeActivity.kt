package dmzing.workd.view.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import dmzing.workd.R
import dmzing.workd.util.Utils
import dmzing.workd.view.MainActivity
import kotlinx.android.synthetic.main.activity_sign_up_three.*

class SignUpThreeActivity : AppCompatActivity(), View.OnClickListener, Utils {
    override fun onClick(v: View?) {
        when(v!!){
            signUpThreeNextBtn->{
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun init() {
        signUpThreeNextBtn.setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_three)
        init()
    }
}
