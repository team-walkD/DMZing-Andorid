package dmzing.workd.view.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import dmzing.workd.CommonData
import dmzing.workd.R
import dmzing.workd.base.BaseModel
import dmzing.workd.model.user.UserDTO
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import dmzing.workd.util.Utils
import dmzing.workd.view.MainActivity
import kotlinx.android.synthetic.main.activity_sign_up_three.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpThreeActivity : AppCompatActivity(), View.OnClickListener, Utils {
    override fun onClick(v: View?) {
        when (v!!) {
            signUpThreeNextBtn -> {
                if (signUpThreePhone.text.length > 0) {
                    postUserCreate()
                }

            }
        }
    }

    override fun init() {
        signUpThreeNextBtn.setOnClickListener(this)
        networkService = ApplicationController.instance.networkService
        SharedPreference.instance!!.load(this)
    }

    lateinit var networkService: NetworkService
    lateinit var userDTO: UserDTO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_three)
        init()
    }

    fun postUserCreate() {
        Log.v("woo 1994 :", "woo??")
        Log.v("woo 413 :", CommonData.user_id)
        Log.v("woo 413 :", CommonData.user_pw)
        Log.v("woo 413 :", CommonData.user_nickname)
        Log.v("woo 413 :", signUpThreePhone.text
            .toString())
        userDTO = UserDTO(
            CommonData.user_id,
            CommonData.user_pw,
            CommonData.user_nickname,
            "010-4899-0373"
        )
        Log.v("woo 1994 :", "woo??")
        var userCreateResponse = networkService.postUserCreate(userDTO)
        userCreateResponse!!.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.v("woo 1994 fail : ",t.message)
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.v("woo success : ", response!!.body().toString())
                Log.v("woo success : ", response!!.message().toString())
                Log.v("woo success : ", response!!.code().toString())
                    when (response!!.code()) {
                        201 -> {
                            SharedPreference.instance!!.setPrefData("jwt",response.headers().toString())
                            Log.v("woo 1994 :", response.headers().toString())
                            Log.v("woo 1994 :", response.message().toString())
                            startActivity(Intent(this@SignUpThreeActivity, MainActivity::class.java))
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
