package dmzing.workd.view.login

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import dmzing.workd.R
import dmzing.workd.model.user.LoginUser
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import dmzing.workd.view.MainActivity
import dmzing.workd.view.signup.SignUpOneActivity
import dmzing.workd.view.signup.SignUpThreeActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log
import kotlin.math.sign

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            loginBtn -> {
                postLogin()

                // 임시 방편임.
                /*SharedPreference.instance!!.setPrefData("jwt", jwt)
                startActivity<MainActivity>()
                finish()*/
            }
            loginToSignBtn -> {
                startActivity(Intent(this, SignUpOneActivity::class.java))
            }
        }
    }

    fun init() {
        loginBtn.setOnClickListener(this)
        loginToSignBtn.setOnClickListener(this)
        networkService = ApplicationController.instance.networkService
        SharedPreference.instance!!.load(this)
    }

    lateinit var networkService: NetworkService
    lateinit var loginUser: LoginUser
    companion object {
        //var jwt : String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdXRob3JpdHkiOiJVU0VSIiwiaXNzIjoiZG16aW5nIiwiZXhwIjoxNTUxNDUyNDQxLCJlbWFpbCI6ImV4YW1wbGVAZ21haWwuY29tIn0.FoOgDpTqPMgescMJB09-sC_Detc_dCMBmqqQoahl1Cw "
        //var jwt : String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdXRob3JpdHkiOiJVU0VSIiwiaXNzIjoiZG16aW5nIiwiZXhwIjoxNTUxNDQ2NDU0LCJlbWFpbCI6ImxlYWtAbGVhay5jb20ifQ.PdS0L1t2JesL3aMN5e-sbYv2aEyv_v3thwEOGxKdk6o"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.isSelected = true
        var signUpText: String = "회원가입"
        var content: SpannableString = SpannableString(signUpText)
        content.setSpan(UnderlineSpan(), 0, 4, 0)

        loginToSignBtn.text = content
        loginToSignBtn.setTypeface(loginToSignBtn.typeface, Typeface.BOLD)
        init()

    }

    fun postLogin() {
        Log.v("1011 woo in:", "ㅇ?")
        loginUser = LoginUser(loginId.text.toString(), loginPw.text.toString())
        Log.v("1011 woo in:", "${loginId.text.toString()}")
        Log.v("1011 woo in:", "${loginPw.text.toString()}")
        var loginResponse = networkService.postLogin(loginUser)
        loginResponse.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.v("1011 woo f:", t.message)
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.v("1011 woo r:", response!!.body().toString())
                Log.v("1011 woo r:", response!!.errorBody().toString())
                Log.v("1011 woo r:", response!!.toString())
                when (response!!.code()) {
                    200 -> {
                        Log.v("1011 woo s:", response!!.body().toString())
                        Log.v("1011 woo s:", response!!.code().toString())
                        Log.v("1011 woo s:", response!!.headers().toString())


                        /*FIXME
                        * response의 header에 토큰이 담겨서 올 경우 header를 바로 사용할 수 없고,
                        * header를 보고 그 안에서 jwt를 뽑아내야 한다.
                        * 지금의 경우에는 0번째 인덱스에 jwt라는 키값이 있는 걸 확인했고
                        * 그래서 0번째 인덱스의 value를 뽑아내어 jwt 값을 가지고 왔다.
                        * */
                        Log.v("1011 woo s:", response!!.headers().value(0))
                        SharedPreference.instance!!.setPrefData("jwt",response!!.headers().value(0))
                        startActivity<MainActivity>()
                        finish()
                    }
                    403 -> {

                    }
                    500 -> {

                    }

                }
            }

        })
    }
}
