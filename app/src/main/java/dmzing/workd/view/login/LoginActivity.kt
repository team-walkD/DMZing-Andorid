package dmzing.workd.view.login

import android.content.Intent
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
import dmzing.workd.view.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            loginBtn -> postLogin()
            loginToSignBtn -> startActivity<SignUpActivity>()
        }
    }


    private val networkService by lazy {
        ApplicationController.instance.networkService
    }
    private var overlapNetworking: String = ""
    lateinit var loginUser: LoginUser


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

    fun init() {
        loginBtn.setOnClickListener(this)
        loginToSignBtn.setOnClickListener(this)
        SharedPreference.instance!!.load(this)
    }

    // 로그인 통신
    fun postLogin() {

        loginUser = LoginUser(loginId.text.toString(), loginPw.text.toString())

        // 중복 통신 막기 위한 방법
        if (overlapNetworking == "") {

            overlapNetworking = "networking"

            var loginResponse = networkService.postLogin(loginUser)
            loginResponse.enqueue(object : Callback<Any> {
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.v("Error LoginActivity:", t.message)
                    overlapNetworking = ""
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    response?.let {
                        when (it.code()) {
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
                                SharedPreference.instance!!.setPrefData("jwt", response!!.headers().value(0))
                                startActivity<MainActivity>()
                                finish()
                            }
                            403 -> {
                                toast("정보가 정확하지 않습니다.")
                            }
                            500 -> {
                                toast("정보가 정확하지 않습니다.")
                            }
                            else -> {
                                toast("error!")
                            }
                        }
                    }?.also {
                        overlapNetworking = ""
                    }
                }

            })
        }

    }
}
