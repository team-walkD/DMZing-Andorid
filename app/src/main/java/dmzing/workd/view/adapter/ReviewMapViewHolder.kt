package dmzing.workd.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import dmzing.workd.R

class ReviewMapViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    var mapImage : CircleImageView = itemView.findViewById(R.id.review_map_image)
    var mapText : TextView = itemView.findViewById(R.id.review_map_title)
    var mapCount : TextView = itemView.findViewById(R.id.review_map_count)
}