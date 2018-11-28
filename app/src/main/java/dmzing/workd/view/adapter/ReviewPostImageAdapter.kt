package dmzing.workd.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dmzing.workd.R
import kotlinx.android.synthetic.main.reivew_post_image_item.view.*

class ReviewPostImageAdapter(var itemList : ArrayList<String>?,var context : Context) : RecyclerView.Adapter<ReviewPostImageAdapter.ReviewPostImageViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ReviewPostImageViewHolder {
        var view = LayoutInflater.from(p0.context).inflate(R.layout.reivew_post_image_item,p0,false)

        return ReviewPostImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

    override fun onBindViewHolder(p0: ReviewPostImageViewHolder, p1: Int) {
        Glide.with(context).load(itemList!!.get(p1)).apply(RequestOptions().centerCrop()).into(p0.postImage)
    }


    inner class ReviewPostImageViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var postImage : ImageView = itemView.findViewById(R.id.review_post_imageview)
    }
}