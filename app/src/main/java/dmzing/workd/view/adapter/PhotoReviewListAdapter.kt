package dmzing.workd.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import dmzing.workd.R
import dmzing.workd.model.review.PhotoReviewModel

class PhotoReviewListAdapter(var itemList : ArrayList<PhotoReviewModel>,var context : Context) : RecyclerView.Adapter<PhotoReviewListAdapter.PhotoReviewListViewHolder>() {
    var itemClick : ItemClick? = null
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PhotoReviewListViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.photo_review_item,p0,false)

        return PhotoReviewListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(p0: PhotoReviewListViewHolder, p1: Int) {
        //Glide.with(context).load(itemList.get(p1).imageUrl).into(p0.photoReviewImage)
        p0.photoReviewPlace.text = itemList.get(p1).placeName
        //p0.photoReviewDate.text = itemList.get(p1).

        p0.itemView.setOnClickListener { v: View? ->
            if(itemClick != null){
                var click = itemClick
                click!!.onClick(v!!,p1)
            }
        }
    }

    fun SetOnItemClickListener(click : ItemClick){
        itemClick = click
    }

    interface ItemClick{
        fun onClick(view : View, position : Int)
    }

    class PhotoReviewListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var photoReviewImage : ImageView = itemView.findViewById(R.id.photo_review_item_image)
        var photoReviewDate : TextView = itemView.findViewById(R.id.photo_review_item_date)
        var photoReviewPlace : TextView = itemView.findViewById(R.id.photo_review_item_place)
    }
}