package dmzing.workd.view.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import dmzing.workd.R
import dmzing.workd.model.user.UserDTO
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import dmzing.workd.view.MainActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.telephony.PhoneNumberFormattingTextWatcher
import android.telephony.PhoneNumberUtils
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import dmzing.workd.base.BaseModel
import org.jetbrains.anko.textColor
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class SignUpActivity : AppCompatActivity(), View.OnClickListener {


    override fun onClick(v: View?) {
        when (v!!) {
            signUpCompleteBtn -> {
                var email = signUpEmail.text.toString()
                var pw = signUpPw.text.toString()
                var phone = signUpPhone.text.toString()
                var nickname = signUpNickname.text.toString()

                if (email.length > 0 && pw.length > 0 && phone.length > 0 && nickname.length > 0) {

                    //유효성 검사
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        if (Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", pw)) {
                            if (Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", phone)) {
                                postUserCreate(email, pw, phone, nickname)
                            } else {
                                toast("유효한 전화번호가 아닙니다.")
                            }
                        } else {
                            toast("유효한 비밀번호가 아닙니다.")
                        }
                    } else {
                        toast("유효한 이메일이 아닙니다.")
                    }

                } else {
                    toast("정보를 정확히 입력해주세요.")
                }
            }
            signUpXBtn -> {
                finish()
            }
        }
    }

    fun init() {
        signUpCompleteBtn.setOnClickListener(this)
        signUpXBtn.setOnClickListener(this)
        networkService = ApplicationController.instance.networkService
        SharedPreference.instance!!.load(this)
    }

    // 이메일 유효성 검사
    var emailWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            emailCheckText.visibility = View.VISIBLE
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                emailCheckText.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorAccent))
                emailCheckText.text = "유효한 이메일이 아닙니다."
            } else {
                emailCheckText.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.mainColor))
                emailCheckText.text = "유효한 이메일 입니다."
            }
        }

    }

    // 비밀번호 유효성 검사
    var pwTextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            signUpPwTv.visibility = View.VISIBLE
            if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", s)) {
                signUpPwTv.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorAccent))
                signUpPwTv.text = "특수문자,영문,숫자를 포함해 주세요."
            } else {
                signUpPwText.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.mainColor))
                signUpPwTv.text = "유효한 비밀번호 입니다."
            }

        }

    }
    lateinit var networkService: NetworkService
    lateinit var userDTO: UserDTO
    private var overlapNetworking: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        init()

        //이메일 실시간 유효성 검사
        signUpEmail.addTextChangedListener(emailWatcher)


        //비밀번호 실시간 유효성 검사
        signUpPw.addTextChangedListener(pwTextWatcher)

    }

    fun postUserCreate(userEmail: String, userPw: String, userPhone: String, userNickname: String) {

        userDTO = UserDTO(userEmail, userPw, userNickname, userPhone)

        if (overlapNetworking == "") {
            overlapNetworking = "networking"

            var userCreateResponse = networkService.postUserCreate(userDTO)
            userCreateResponse!!.enqueue(object : Callback<Any> {
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.v("Error LoginActivity : ", t.message)
                    overlapNetworking = ""
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {

                    response?.let {
                        when (it.code()) {
                            201 -> {
                                SharedPreference.instance!!.setPrefData("jwt", response.headers().value(0))
                                Log.v("woo 1994 :", response.headers().toString())
                                Log.v("woo 1994 :", response.message().toString())
                                startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                                finish()
                            }
                            400 -> {
                                Log.v("400 woo", response.message())
                                Log.v("400 woo", response.errorBody().toString())
                                toast("중복된 이메일입니다.")
                            }
                            401 -> {

                            }
                            500 -> {

                            }
                            else -> {
                                toast("error")
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
