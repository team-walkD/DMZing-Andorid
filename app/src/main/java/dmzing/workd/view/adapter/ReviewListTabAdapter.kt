package dmzing.workd.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import dmzing.workd.view.review.DetailReviewListFragment
import dmzing.workd.view.review.PhotoReviewFragment

class ReviewListTabAdapter(fm : FragmentManager,var tabCount : Int) : FragmentStatePagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment? {
        var photoReview = PhotoReviewFragment()
        var detailReview = DetailReviewListFragment()

        when(p0){
            0 -> return photoReview
            1 -> return detailReview
            else -> return null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }

}