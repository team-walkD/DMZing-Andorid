package dmzing.workd.view.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dmzing.workd.R
import dmzing.workd.model.review.ReviewMapData
import java.util.ArrayList

class ReviewMapAdapter(var reviewMapItems : ArrayList<ReviewMapData>, var context : Context) : RecyclerView.Adapter<ReviewMapViewHolder>(){
    private lateinit var onItemClick : View.OnClickListener
    var itemClick : Itemclick? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ReviewMapViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.review_map_item,p0,false)

        //view.setOnClickListener(onItemClick)

        return ReviewMapViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviewMapItems.size
    }

    override fun onBindViewHolder(p0: ReviewMapViewHolder, p1: Int) {
        var oval : ShapeDrawable = ShapeDrawable(OvalShape())
        oval.paint.color = Color.parseColor("#6da8c7")
        p0.mapText.text = reviewMapItems.get(p1).mapTitle
        p0.mapCount.text = reviewMapItems.get(p1).mapCount
        p0.mapCount.background = ShapeDrawable(OvalShape())
        p0.mapCount.background = oval
        p0.mapImage.setBackgroundResource(R.drawable.bmo)

        p0.itemView.setOnClickListener{v : View ->
            val click = itemClick
            if(click != null){
                click.onClick(v!!,p1)
            }
        }
    }
    fun setOnItemClickListener(click : Itemclick){
        itemClick = click
    }

    interface Itemclick{
        fun onClick(view : View, position : Int)
    }

}