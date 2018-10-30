package dmzing.workd.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import dmzing.workd.view.home.HomeFragment
import dmzing.workd.view.map.MapFragment
import dmzing.workd.view.mypage.MypageFragment
import dmzing.workd.view.review.ReviewFragment

/**
 * Created by VictoryWoo
 */
class DmzingFragmentStatePagerAdapter(fm: FragmentManager, var fragmentCount: Int) : FragmentStatePagerAdapter(fm) {
    var homFragment = HomeFragment()
    var mapFragment = MapFragment()
    var reviewFragment = ReviewFragment()
    var mypageFragment = MypageFragment()
    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return homFragment
            1 -> return mapFragment
            2 -> return reviewFragment
            3 -> return mypageFragment
            else -> return null
        }
    }

    override fun getCount(): Int {
        return fragmentCount
    }
}