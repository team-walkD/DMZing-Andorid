package dmzing.workd.view.review

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dmzing.workd.R
import kotlinx.android.synthetic.main.activity_photo_detail.*

class PhotoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)
        var photo = intent.getStringExtra("photo")

        Glide.with(this).load(photo).apply(RequestOptions().centerCrop()).into(photo_detail_image)

        photo_detail_close.setOnClickListener { v: View? ->
            finish()
        }
    }
}
