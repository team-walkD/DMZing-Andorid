package dmzing.workd.view.login

import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import dmzing.workd.R
import dmzing.workd.view.MainActivity
import dmzing.workd.view.signup.SignUpOneActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import kotlin.math.log
import kotlin.math.sign

class LoginActivity : AppCompatActivity(),View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!){
            loginBtn->{
                startActivity<MainActivity>()
                finish()
            }
            loginToSignBtn->{
                startActivity(Intent(this, SignUpOneActivity::class.java))
            }
        }
    }

    fun init(){
        loginBtn.setOnClickListener(this)
        loginToSignBtn.setOnClickListener(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var signUpText : String = "회원가입"
        var content : SpannableString = SpannableString(signUpText)
        content.setSpan(UnderlineSpan(), 0,4,0)

        loginToSignBtn.text = content
        loginToSignBtn.setTypeface(loginToSignBtn.typeface, Typeface.BOLD)
        init()

    }
}
