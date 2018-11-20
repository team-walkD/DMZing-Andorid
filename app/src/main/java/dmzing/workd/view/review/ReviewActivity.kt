package dmzing.workd.view.review

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dmzing.workd.R
import dmzing.workd.model.review.reviewDto

class ReviewActivity : AppCompatActivity() {
    lateinit var mReviewDto : reviewDto
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
    }
}
