package dmzing.workd.view.review

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dmzing.workd.R
import org.jetbrains.anko.textSizeDimen
import android.os.Build
import android.widget.LinearLayout
import java.io.File.separator
import android.graphics.drawable.GradientDrawable
import android.widget.Toast
import dmzing.workd.view.adapter.ReviewListTabAdapter
import kotlinx.android.synthetic.main.activity_review_list.*


class ReviewListActivity : AppCompatActivity() {


    lateinit var mTabLayout : TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_list)

        settingTab()

        review_list_back_button.setOnClickListener {
            //뒤로가기 버튼
        }

        review_list_walkd_button.setOnClickListener {
            //챗봇 버튼
        }

        review_list_write_button.setOnClickListener{
            //리뷰 쓰기 버튼
            when(mTabLayout.selectedTabPosition){
                0->{
                    Toast.makeText(this,"사진 리뷰",Toast.LENGTH_LONG).show()
                }
                1->{
                    startActivity(Intent(this,ReviewWriteActivity::class.java))
                }
            }
        }

    }

    fun settingTab(){
        mTabLayout = findViewById(R.id.review_list_tabLayout)
        review_list_viewpager.adapter = ReviewListTabAdapter(supportFragmentManager,2)
        mTabLayout.setupWithViewPager(review_list_viewpager)
        mTabLayout.getTabAt(0)!!.setText("사진 리뷰")
        mTabLayout.getTabAt(1)!!.setText("상세 리뷰")


        setCustomFont()
        setDivider()
    }

    fun setDivider(){
        val root = mTabLayout.getChildAt(0)
        if (root is LinearLayout) {
            (root as LinearLayout).showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
            val drawable = GradientDrawable()
            drawable.setColor(Color.parseColor("#b0bebebe"))
            drawable.setSize(2, 4)
            (root as LinearLayout).dividerPadding = 10
            (root as LinearLayout).dividerDrawable = drawable
        }
    }

    public fun setCustomFont(){
        var vg = mTabLayout.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount

        for (i in 0 until tabsCount){
            var vgTab : ViewGroup = vg.getChildAt(i) as ViewGroup
            var tabChildCount : Int = vgTab.childCount

            for(j in 0 until tabChildCount){
                var tabViewChild : View = vgTab.getChildAt(j)
                if(tabViewChild is TextView){
                    (tabViewChild as TextView).typeface = resources.getFont(R.font.notosanscjkkr_bold)
                    (tabViewChild as TextView).setTextSize(TypedValue.COMPLEX_UNIT_SP,16f)
                }
            }

        }
    }

    fun wrapTabIndicatorToTitle(tabLayout: TabLayout, externalMargin: Int, internalMargin: Int) {
        val tabStrip = tabLayout.getChildAt(0)
        if (tabStrip is ViewGroup) {
            val childCount = tabStrip.childCount
            for (i in 0 until childCount) {
                val tabView = tabStrip.getChildAt(i)
                //set minimum width to 0 for instead for small texts, indicator is not wrapped as expected
                tabView.minimumWidth = 0
                // set padding to 0 for wrapping indicator as title
                tabView.setPadding(0, tabView.paddingTop, 0, tabView.paddingBottom)
                // setting custom margin between tabs
                if (tabView.layoutParams is ViewGroup.MarginLayoutParams) {
                    val layoutParams = tabView.layoutParams as ViewGroup.MarginLayoutParams
                    if (i == 0) {
                        // left
                        settingMargin(layoutParams, externalMargin, internalMargin)
                    } else if (i == childCount - 1) {
                        // right
                        settingMargin(layoutParams, internalMargin, externalMargin)
                    } else {
                        // internal
                        settingMargin(layoutParams, internalMargin, internalMargin)
                    }
                }
            }

            tabLayout.requestLayout()
        }
    }

    private fun settingMargin(layoutParams: ViewGroup.MarginLayoutParams, start: Int, end: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.marginStart = start
            layoutParams.marginEnd = end
            layoutParams.leftMargin = start
            layoutParams.rightMargin = end
        } else {
            layoutParams.leftMargin = start
            layoutParams.rightMargin = end
        }
    }

    fun reduceMarginsInTabs(tabLayout: TabLayout, marginOffset: Int) {

        val tabStrip = tabLayout.getChildAt(0)
        if (tabStrip is ViewGroup) {
            for (i in 0 until tabStrip.childCount) {
                val tabView = tabStrip.getChildAt(i)
                if (tabView.layoutParams is ViewGroup.MarginLayoutParams) {
                    (tabView.layoutParams as ViewGroup.MarginLayoutParams).leftMargin = marginOffset
                    (tabView.layoutParams as ViewGroup.MarginLayoutParams).rightMargin = marginOffset
                }
            }

            tabLayout.requestLayout()
        }
    }

    fun convertDpToPixel(dp : Float,context : Context) : Float{
        var resources = context.resources

        var metrics = resources.displayMetrics

        var px = dp * (metrics.densityDpi / 160f)

        return px
    }
}
