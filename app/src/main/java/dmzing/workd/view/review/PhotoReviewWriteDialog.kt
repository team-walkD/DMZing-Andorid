package dmzing.workd.view.review

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import dmzing.workd.R
import android.view.WindowManager
import android.widget.Toast
import dmzing.workd.util.CourseType
import dmzing.workd.view.adapter.PhotoReviewPlaceAdapter
import kotlinx.android.synthetic.main.dialog_photo_review_write.*


class PhotoReviewWriteDialog(var courseId : Int,context : Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.8f
        window!!.attributes = lpWindow

        setContentView(R.layout.dialog_photo_review_write)
        var placeList = ArrayList<String>()
        when(courseId){
            CourseType.DATE.num ->{
                placeList.add("파주 임진각")
                placeList.add("통일공원(파주)")
                placeList.add("파주 카트랜드")
                placeList.add("신세계사이먼 파주 프리미엄 아울렛")
            }
            CourseType.HISTORY.num->{
                placeList.add("고성 DMZ박물관")
                placeList.add("통일전망대(고성)")
                placeList.add("이승만별장(고성)")
                placeList.add("화암사(고성)")
            }
            CourseType.ADVENTURE.num->{
                placeList.add("화천 하늘빛호수마을")
                placeList.add("화천 카트레일카")
                placeList.add("파로호(화천)")
                placeList.add("평화의댐(화천)")
            }
            CourseType.DMZ.num->{
                placeList.add("백마고지 위령비와 기념관")
                placeList.add("철원 노동당사")
                placeList.add("철원평화전망대")
                placeList.add("제2땅굴(철원)")
            }
        }
        var photoReviewPlaceAdpater = PhotoReviewPlaceAdapter(placeList,context)
        photoReviewPlaceAdpater.setOnItemSelectListener(object : PhotoReviewPlaceAdapter.setPlaceCheck{
            override fun onItemSelect(holder: PhotoReviewPlaceAdapter.PhotoReviewPlaceViewHolder, position: Int) {
                if(holder.isCheck){
                    holder.isChecked = false
                } else {
                    for (i in 0 until dilaog_photo_reivew_recycler.childCount){
                        var viewholder
                                = dilaog_photo_reivew_recycler.findViewHolderForAdapterPosition(i)
                                as PhotoReviewPlaceAdapter.PhotoReviewPlaceViewHolder
                        if(viewholder.isCheck){
                            viewholder.isChecked = false
                        }
                    }
                    holder.isChecked = true
                }
            }
        })
        dilaog_photo_reivew_recycler.layoutManager = LinearLayoutManager(context)
        dilaog_photo_reivew_recycler.adapter = photoReviewPlaceAdpater

        dialog_photo_review_select.setOnClickListener {
            var check : Boolean = false
            var text : String? = null
            for (i in 0 until dilaog_photo_reivew_recycler.childCount){
                var viewholder = dilaog_photo_reivew_recycler.findViewHolderForAdapterPosition(i) as PhotoReviewPlaceAdapter.PhotoReviewPlaceViewHolder
                if(viewholder.isCheck){
                    check = true
                    text = viewholder.placeText.text.toString()
                }
            }
            if(check){
                var intent = Intent(context,PhotoReviewWriteActivity::class.java)
                intent.putExtra("place",text)
                intent.putExtra("courseId",courseId)
                context.startActivity(intent)
                dismiss()
            } else {
                Toast.makeText(context,"장소를 선택해주세요!",Toast.LENGTH_LONG).show()
            }
        }
    }
}