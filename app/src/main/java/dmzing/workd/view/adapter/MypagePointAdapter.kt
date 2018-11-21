package dmzing.workd.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dmzing.workd.R
import dmzing.workd.model.mypage.GetMyDpPoint
import dmzing.workd.model.mypage.MypagePoint

/**
 * Created by VictoryWoo
 */
class MypagePointAdapter(var items : ArrayList<GetMyDpPoint>, var context : Context)
    : RecyclerView.Adapter<MypagePointAdapter.MypagePointViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MypagePointViewHolder {
        var view = LayoutInflater.from(parent.context!!).inflate(R.layout.item_point_list, parent,false)
        return MypagePointViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MypagePointViewHolder, position: Int) {

        holder.point_title.text = items[position].dpHistoryDtos[position].dpType

    }

    inner class MypagePointViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var point_title: TextView = itemView.findViewById(R.id.pointTitleItem)
        var point_number: TextView = itemView.findViewById(R.id.pointNumberItem)
        var point_date: TextView = itemView.findViewById(R.id.pointDateItem)
        var point_method: TextView = itemView.findViewById(R.id.pointMethodItem)


    }
}