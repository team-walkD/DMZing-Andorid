package dmzing.workd.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dmzing.workd.R
import dmzing.workd.model.review.ReviewMapData
import kotlinx.android.synthetic.main.my_letter_item_list.view.*
import java.util.ArrayList

class MypageLetterAdapter(var letterItems : ArrayList<String>, var context : Context) : RecyclerView.Adapter<MypageLetterAdapter.MypageLetterViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MypageLetterViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.my_letter_item_list,p0,false)

        return MypageLetterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return letterItems.size
    }

    override fun onBindViewHolder(p0: MypageLetterViewHolder, p1: Int) {
        p0.testText.text = letterItems.get(p1)
    }


    class MypageLetterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var testText : TextView = itemView.findViewById(R.id.letter_test)
    }
}