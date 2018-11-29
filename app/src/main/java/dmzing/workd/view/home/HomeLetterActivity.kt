package dmzing.workd.view.home

import android.content.Context
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dmzing.workd.R
import kotlinx.android.synthetic.main.activity_home_letter.*
import kotlinx.android.synthetic.main.dialog_letter_x.*

class HomeLetterActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            homeLetterOkayBtn -> finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_letter)
        homeLetterOkayBtn.setOnClickListener(this)


        var wm = applicationContext!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var display = wm.defaultDisplay
        var size = Point()
        display.getSize(size)


        var letterImage = intent.getStringExtra("letter")
        homeLetterImage.clipToOutline = true
        Glide.with(this)
            .load(letterImage)
            .apply(RequestOptions().override(size.x*0.9f.toInt()))
            .into(homeLetterImage)
    }
}
