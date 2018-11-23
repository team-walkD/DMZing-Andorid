package dmzing.workd.view.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import android.widget.TextView
import dmzing.workd.R
import kotlinx.android.synthetic.main.dialog_photo_review_place_item.view.*

class PhotoReviewPlaceAdapter(var itemList: ArrayList<String>, var context: Context)
    : RecyclerView.Adapter<PhotoReviewPlaceAdapter.PhotoReviewPlaceViewHolder>() {
    var itemSelect : setPlaceCheck? = null
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PhotoReviewPlaceViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.dialog_photo_review_place_item,p0,false)

        return PhotoReviewPlaceViewHolder(view,context)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(p0: PhotoReviewPlaceViewHolder, p1: Int) {
        p0.placeText.text = itemList.get(p1)
        p0.itemView.setOnClickListener {

            var selector = itemSelect
            if(selector != null){
                selector.onItemSelect(p0,p1)
            }
        }
    }

    fun setOnItemSelectListener(select : setPlaceCheck){
        itemSelect = select
    }

    interface setPlaceCheck{
        fun onItemSelect(holder : PhotoReviewPlaceViewHolder,position : Int)
    }


    inner class PhotoReviewPlaceViewHolder(itemView : View,var context : Context) : RecyclerView.ViewHolder(itemView), Checkable{
        var isCheck : Boolean = false
        var placeText : TextView = itemView.findViewById(R.id.dialog_photo_review_place_text)
        override fun isChecked(): Boolean {
            return isCheck
        }

        override fun toggle() {
            isChecked = !isCheck
        }

        override fun setChecked(checked: Boolean) {
            if(checked){
                isCheck = checked
                itemView.background = context.getDrawable(R.drawable.place_selected_border)
                placeText.setTextColor(Color.parseColor("#ffffff"))
            } else {
                isCheck = checked
                itemView.background = context.getDrawable(R.drawable.mypage_border)
                placeText.setTextColor(Color.parseColor("#1C3748"))
            }
        }

    }
}