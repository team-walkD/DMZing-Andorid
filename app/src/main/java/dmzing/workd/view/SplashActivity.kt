package dmzing.workd.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import dmzing.workd.R
import dmzing.workd.view.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        },1000)


    }
}
