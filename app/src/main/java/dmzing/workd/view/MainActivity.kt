package dmzing.workd.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import dmzing.workd.R
import dmzing.workd.view.adapter.DmzingFragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {


    private var backPressedTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingTab()

    }

    // 홈 화면에서 back 버튼 처리
    override fun onBackPressed() {
        super.onBackPressed()

        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis()
            toast("\'뒤로가기\' 버튼을 한번 더 누르시면 종료됩니다.")
            return
        }
        if (System.currentTimeMillis() <= backPressedTime + 2000)
            this.finish()

    }

    fun settingTab() {
        mainViewPager.adapter = DmzingFragmentStatePagerAdapter(supportFragmentManager, 4)
        mainViewPager.offscreenPageLimit = 4
        mainViewPager.setPagingEnabled(false)
        mainTab.setupWithViewPager(mainViewPager)
        val bottomTabLayout: View = this.layoutInflater.inflate(R.layout.bottom_main_tab, null, false)

        mainTab.getTabAt(0)!!.customView = bottomTabLayout.findViewById(R.id.tab_home_btn) as LinearLayout
        mainTab.getTabAt(1)!!.customView = bottomTabLayout.findViewById(R.id.tab_map_btn) as LinearLayout
        mainTab.getTabAt(2)!!.customView = bottomTabLayout.findViewById(R.id.tab_review_btn) as LinearLayout
        mainTab.getTabAt(3)!!.customView = bottomTabLayout.findViewById(R.id.tab_mypage_btn) as LinearLayout
    }
}
