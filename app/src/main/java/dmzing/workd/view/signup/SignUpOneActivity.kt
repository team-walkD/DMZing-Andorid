package dmzing.workd.view.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import dmzing.workd.R
import dmzing.workd.util.Utils
import kotlinx.android.synthetic.main.activity_sign_up_one.*
import org.jetbrains.anko.toast

class SignUpOneActivity : AppCompatActivity(), View.OnClickListener, Utils {

    // 초기화
    override fun init() {
        signUpOneNextBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!){
            signUpOneNextBtn->{
                if(signUpOneName.text.length > 0 && signUpOneNickname.text.length > 0){
                    var name : String = signUpOneName.text.toString()
                    var nickname : String = signUpOneNickname.text.toString()
                    var intent = Intent(this@SignUpOneActivity, SignUpTwoActivity::class.java)
                    intent.putExtra("name",name)
                    intent.putExtra("nickname",nickname)
                    startActivity(intent)
                }else{
                    toast("정보를 입력해주세요.")

                }

            }


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_one)
        init()
    }
}
