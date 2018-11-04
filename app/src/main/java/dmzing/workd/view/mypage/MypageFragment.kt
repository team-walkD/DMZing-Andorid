package dmzing.workd.view.mypage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dmzing.workd.R
import dmzing.workd.util.Utils
import dmzing.workd.view.mypage.point.MypagePointActivity
import kotlinx.android.synthetic.main.fragment_mypage.*
import kotlinx.android.synthetic.main.fragment_mypage.view.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by VictoryWoo
 */
class MypageFragment : Fragment(), View.OnClickListener, Utils {
    override fun init() {

    }

    fun init(view : View){
        view.mypageCourseBtn.setOnClickListener(this)
        view.mypageReviewBtn.setOnClickListener(this)
        view.mypagePointBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!) {
            mypageCourseBtn -> startActivity<MypagePointActivity>()
            mypageReviewBtn -> startActivity<MypageReviewActivity>()
            mypagePointBtn -> startActivity<MypagePointActivity>()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_mypage, container, false)
        init(view)

        return view
    }
}