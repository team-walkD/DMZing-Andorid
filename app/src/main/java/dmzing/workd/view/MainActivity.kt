package dmzing.workd.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import dmzing.workd.R
import dmzing.workd.view.adapter.DmzingFragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingTab()
    }
    fun settingTab(){
        mainViewPager.adapter = DmzingFragmentStatePagerAdapter(supportFragmentManager, 4)
        mainViewPager.offscreenPageLimit = 4
        mainTab.setupWithViewPager(mainViewPager)
        val bottomTabLayout : View = this.layoutInflater.inflate(R.layout.bottom_main_tab, null, false)

        mainTab.getTabAt(0)!!.customView = bottomTabLayout.findViewById(R.id.tab_home_btn) as LinearLayout
        mainTab.getTabAt(1)!!.customView = bottomTabLayout.findViewById(R.id.tab_map_btn) as LinearLayout
        mainTab.getTabAt(2)!!.customView = bottomTabLayout.findViewById(R.id.tab_review_btn) as LinearLayout
        mainTab.getTabAt(3)!!.customView = bottomTabLayout.findViewById(R.id.tab_mypage_btn) as LinearLayout
    }
}
