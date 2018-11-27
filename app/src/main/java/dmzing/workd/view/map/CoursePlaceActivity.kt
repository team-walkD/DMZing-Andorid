package dmzing.workd.view.map

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dmzing.workd.R
import dmzing.workd.model.map.PlaceDto
import kotlinx.android.synthetic.main.activity_course_place.*

class CoursePlaceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_place)

        var placeDto = intent.extras.get("place") as PlaceDto

        Glide.with(this).load(placeDto.mainImageUrl).apply(RequestOptions().centerCrop()).into(course_place_image)
        course_place_image.setColorFilter(Color.parseColor("#7f000000"), PorterDuff.Mode.SRC_OVER)
        if(placeDto.sequence == 100){
            course_place_sequence.text = "0"+4
        } else {
            course_place_sequence.text = "0"+placeDto.sequence
        }
        course_place_title.text = placeDto.title
        course_place_descrip.text = Html.fromHtml(placeDto.description).toString()
        course_place_descrip.movementMethod = ScrollingMovementMethod()
        course_place_rest.text = placeDto.restDate
        course_place_parking.text = placeDto.parking
        course_place_info.text = Html.fromHtml(placeDto.infoCenter).toString()

        course_place_close.setOnClickListener {
            finish()
        }
    }
}
