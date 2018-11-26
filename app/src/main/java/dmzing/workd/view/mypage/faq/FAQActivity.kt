package dmzing.workd.view.mypage.faq

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import dmzing.workd.R
import dmzing.workd.util.Utils
import kotlinx.android.synthetic.main.activity_faq.*

class FAQActivity : AppCompatActivity(), View.OnClickListener, Utils {
    override fun init() {
        faqXBtn.setOnClickListener(this)
        faqOneAnswer.setOnClickListener(this)
        faqTwoAnswer.setOnClickListener(this)
        faqThreeAnswer.setOnClickListener(this)
        faqFourAnswer.setOnClickListener(this)
        faqFiveAnswer.setOnClickListener(this)

        faqOneAnswer.isSelected = true
        faqTwoAnswer.isSelected = true
        faqThreeAnswer.isSelected = true
        faqFourAnswer.isSelected = true
        faqFiveAnswer.isSelected = true


        answerList = ArrayList()


    }

    fun setData() {
        answerList.add("'디엠징(DMZING)’은 디엠지라는 특수한 지역을 대상으로,맵과 챗봇을 통해 정보를 얻거나, 미션을 통해 즐거움을얻을 수 있도록 하는 관광 어플리케이션 입니다.")
        answerList.add("지정된 장소에서 편지를 찾으면 DP(DMZing Point)가 제공됩니다. 해당 DP를 이용하여 잠겨있는 맵을 구매할 수 있습니다.")
        answerList.add("기본으로 제공되거나 구매한 맵을 메인화면에서 선택하면 편지 장소에 대한 힌트를 볼 수 있습니다. 해당 장소에서 편지 찾기 버튼을 클릭하면 미션에 참여할 수 있습니다.")
        answerList.add("사진 리뷰 화면에서 우측 상단 글쓰기 버튼을 클릭하면 사진 리뷰를 작성할 수 있고, 상세 리뷰 화면에서 클릭하면 글 리뷰를 작성할 수 있습니다.")
        answerList.add("한반도 비무장 지대(韓半島非武裝地帶, Korean Demilitarized Zone, DMZ)는 한국 전쟁 이후 1953년 체결된 정전 협정에 따라 설정된 비무장 지대입니다")

        fqaOneAnswerText.text = answerList[0]
        fqaTwoAnswerText.text = answerList[1]
        fqaThreeAnswerText.text = answerList[2]
        fqaFourAnswerText.text = answerList[3]
        fqaFiveAnswerText.text = answerList[4]
    }

    override fun onClick(v: View?) {
        when (v!!) {
            faqXBtn -> finish()
            faqOneAnswer -> {
                if (faqOneAnswer.isSelected) {
                    faqOneAnswer.isSelected = false
                    fqaOneAnswerText.visibility = View.VISIBLE
                    faqOneMore.setImageResource(R.drawable.my_faq_more_reverse_icon)
                } else {
                    faqOneAnswer.isSelected = true
                    fqaOneAnswerText.visibility = View.GONE
                    faqOneMore.setImageResource(R.drawable.my_faq_more_icon)
                }
            }
            faqTwoAnswer -> {
                if (faqTwoAnswer.isSelected) {
                    faqTwoAnswer.isSelected = false
                    fqaTwoAnswerText.visibility = View.VISIBLE
                    faqTwoMore.setImageResource(R.drawable.my_faq_more_reverse_icon)
                } else {
                    faqTwoAnswer.isSelected = true
                    fqaTwoAnswerText.visibility = View.GONE
                    faqTwoMore.setImageResource(R.drawable.my_faq_more_icon)
                }

            }
            faqThreeAnswer -> {
                if (faqThreeAnswer.isSelected) {
                    faqThreeAnswer.isSelected = false
                    fqaThreeAnswerText.visibility = View.VISIBLE
                    faqThreeMore.setImageResource(R.drawable.my_faq_more_reverse_icon)
                } else {
                    faqThreeAnswer.isSelected = true
                    fqaThreeAnswerText.visibility = View.GONE
                    faqThreeMore.setImageResource(R.drawable.my_faq_more_icon)
                }

            }
            faqFourAnswer -> {
                if (faqFourAnswer.isSelected) {
                    faqFourAnswer.isSelected = false
                    fqaFourAnswerText.visibility = View.VISIBLE
                    faqFourMore.setImageResource(R.drawable.my_faq_more_reverse_icon)
                } else {
                    faqFourAnswer.isSelected = true
                    fqaFourAnswerText.visibility = View.GONE
                    faqFourMore.setImageResource(R.drawable.my_faq_more_icon)
                }

            }
            faqFiveAnswer -> {
                if (faqFiveAnswer.isSelected) {
                    faqFiveAnswer.isSelected = false
                    fqaFiveAnswerText.visibility = View.VISIBLE
                    faqFiveMore.setImageResource(R.drawable.my_faq_more_reverse_icon)
                } else {
                    faqFiveAnswer.isSelected = true
                    fqaFiveAnswerText.visibility = View.GONE
                    faqFiveMore.setImageResource(R.drawable.my_faq_more_icon)
                }

            }
        }
    }

    lateinit var answerList: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)

        init()
        setData()
    }
}
