package dmzing.workd.view.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import dmzing.workd.CommonData
import dmzing.workd.R
import dmzing.workd.util.Utils
import kotlinx.android.synthetic.main.activity_sign_up_two.*
import org.jetbrains.anko.toast

class SignUpTwoActivity : AppCompatActivity(), View.OnClickListener, Utils {
    override fun onClick(v: View?) {
        when (v) {
            signUpTwoNextBtn -> {
                if (signUpTwoId.text.length > 0 && signUpTwoPw.text.length > 0 && signUpTwoPwCheck.text.length > 0){
                    if(signUpTwoPw.text.toString().equals(signUpTwoPwCheck.text.toString())){

                        var intent = Intent(this@SignUpTwoActivity, SignUpThreeActivity::class.java)

                        CommonData.user_id = signUpTwoId.text.toString()
                        CommonData.user_pw = signUpTwoPw.text.toString()

                        startActivity(intent)
                    }else{
                        toast("비밀번호가 일치하지 않습니다.")
                    }
                }else{
                    toast("정보를 입력해주세요.")
                }

            }
        }
    }

    override fun init() {
        signUpTwoNextBtn.setOnClickListener(this)

    }

    lateinit var sign_two_name: String
    lateinit var sign_two_nickname: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_two)

        init()
    }
}
