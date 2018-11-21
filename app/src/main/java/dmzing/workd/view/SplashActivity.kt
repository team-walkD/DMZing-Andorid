package dmzing.workd.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import dmzing.workd.R
import dmzing.workd.util.SharedPreference
import dmzing.workd.view.login.LoginActivity
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        SharedPreference.instance!!.load(this)

        var handler = Handler()
        handler.postDelayed({
            if(SharedPreference.instance!!.getPrefStringData("jwt")!!.isNotEmpty())
                startActivity(Intent(this, MainActivity::class.java))
            else
                startActivity<LoginActivity>()

            finish()

        },1000)


    }
}
