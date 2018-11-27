package dmzing.workd.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dmzing.workd.R
import dmzing.workd.model.letter.MypageLetterDto
import kotlinx.android.synthetic.main.my_letter_item_list.view.*
import java.util.ArrayList

class MypageLetterAdapter(var letterItems: ArrayList<MypageLetterDto>, var context: Context) :
    RecyclerView.Adapter<MypageLetterAdapter.MypageLetterViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MypageLetterViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.my_letter_item_list, p0, false)

        return MypageLetterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return letterItems.size
    }

    override fun onBindViewHolder(holder: MypageLetterViewHolder, position: Int) {

      /*  var requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.my_myreview_icon)
        requestOptions.override(1000)*/

        Glide.with(context)
            .load(letterItems[position].letterImageUrl)
            .into(holder.letterImage)
    }


    class MypageLetterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var letterImage: ImageView = itemView.mypageLetterImageItem
    }
}