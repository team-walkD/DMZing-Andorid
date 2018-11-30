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
import java.util.*


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
                    postUserCreate(email, pw, phone, nickname)
                } else {
                    //toast("정보를 정확히 입력해주세요.")
                    toast(phone)
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
    }

    fun postUserCreate(userEmail: String, userPw: String, userPhone: String, userNickname: String) {

        userDTO = UserDTO(userEmail, userPw, userPhone, userNickname)
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
                    401 -> {

                    }
                    500 -> {

                    }

                }
            }


        })
    }
}
