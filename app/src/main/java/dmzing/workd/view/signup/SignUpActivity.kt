package dmzing.workd.view.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            signUpCompleteBtn -> {
                var email = signUpEmail.text.toString()
                var pw = signUpPw.text.toString()

                /*var phone: String = PhoneNumberUtils.formatNumber(signUpPhone.text!!.toString(),Locale.KOREA)*/

                /*signUpPhone.setInputType(android.text.InputType.TYPE_CLASS_PHONE) // 먼저 EditText에 번호만 입력되도록 바꾼 뒤


                signUpPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher()) // 이렇게 리스너를 걸어주면*/


                var phone = signUpPhone.text.toString()




                /*    signUpPhone.addTextChangedListener(object : TextWatcher{
                        override fun afterTextChanged(text: Editable?) {
                            if (text!!.length == 3 || text.length == 7) {
                                text.append('-')
                                phone = text.toString()
                            }
                        }

                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                    })*/
                var nickname = signUpNickname.text.toString()
                if (email.length > 0 && pw.length > 0 && phone.length > 0 && nickname.length > 0) {

                    //유효성 검사
                    if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        if(Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$",pw)){
                            if(Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", phone)){
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


    lateinit var networkService: NetworkService
    lateinit var userDTO: UserDTO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        signUpPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        init()

        //이메일 실시간 유효성 검사
        signUpEmail.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                    emailCheckText.text = "유효한 이메일이 아닙니다."
                } else {
                    emailCheckText.text = "유효한 이메일 입니다."
                }
            }

        })

        //비밀번호 실시간 유효성 검사
        signUpPw.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$",s)){
                    signUpPwTv.text = "특수문자,영문,숫자를 포함해 주세요."
                } else {
                    signUpPwTv.text = "유효한 비밀번호 입니다."
                }
            }

        })
    }

    fun postUserCreate(userEmail: String, userPw: String, userPhone: String, userNickname: String) {
        Log.v("woo phone",userPhone.toString())
        userDTO = UserDTO(userEmail, userPw, userNickname,userPhone)
        Log.v("woo 1994 :", "woo??")
        var userCreateResponse = networkService.postUserCreate(userDTO)
        userCreateResponse!!.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.v("woo 1994 fail : ", t.message)
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.v("woo success : ", response!!.body().toString())
                Log.v("woo success : ", response!!.message().toString())
                Log.v("woo success : ", response!!.code().toString())
                when (response!!.code()) {
                    201 -> {
                        SharedPreference.instance!!.setPrefData("jwt", response.headers().value(0))
                        Log.v("woo 1994 :", response.headers().toString())
                        Log.v("woo 1994 :", response.message().toString())
                        startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                        finish()
                    }
                    400 -> {
//                        var errorMessage = response.body()!! as ArrayList<BaseModel>
//                        for(i in 0 until errorMessage.size){
//                            Toast.makeText(this@SignUpActivity,errorMessage[i].message,Toast.LENGTH_SHORT).show()
//                        }
                        toast("중복된 이메일입니다.")
                    }
                    401 -> {

                    }
                    500 -> {

                    }

                }
            }


        })
    }
}
