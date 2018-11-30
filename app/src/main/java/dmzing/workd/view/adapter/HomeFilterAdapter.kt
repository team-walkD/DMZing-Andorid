package dmzing.workd.view.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import android.widget.RelativeLayout
import android.widget.TextView
import dmzing.workd.R
import dmzing.workd.model.home.HomeFilterData
import kotlinx.android.synthetic.main.home_course_filter_list.view.*
import org.jetbrains.anko.toast

/**
 * Created by VictoryWoo
 */
class HomeFilterAdapter(var items: ArrayList<HomeFilterData>, var context: Context) :
    RecyclerView.Adapter<HomeFilterAdapter.HomeFilterViewHolder>() {

    var itemSelect : setFilterSelect? = null

    lateinit var onItemClick :View.OnClickListener
    lateinit var holderArr : ArrayList<HomeFilterViewHolder>

    fun setItemClickListener(l : View.OnClickListener){
        onItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFilterViewHolder {
        var view = LayoutInflater
            .from(parent.context).inflate(R.layout.home_course_filter_list, parent, false)

        view.setOnClickListener(onItemClick)
        return HomeFilterViewHolder(view)
    }

    // 인터페이스 선언
    interface setFilterSelect{
        fun onFilterSelect(holder : HomeFilterViewHolder, position: Int)
    }

    // Filter Item이 Select 되었는지 확인하는 함수가
    //  내가 만든 인터페이스를 매개변수로 받는다.
    // 그리고 전역으로 선언되어 있는 인터페이스 객체에 select를 담는다.
    fun setOnFilterSelectListener(select: setFilterSelect){
        itemSelect = select
    }

    override fun getItemCount(): Int {
        Log.v("209", items.size.toString())
        return items.size
    }

    override fun onBindViewHolder(holder: HomeFilterViewHolder, position: Int) {
        when(items[position].id){
            1->{
                if(items[position].isPicked){
                    holder.filter_btn.background = ContextCompat.getDrawable(context, R.drawable.filter_background)
                    holder.isChecked = true
                }
                Log.v("209-1", "11")
                holder.fiter_map.text = "DMZ탐방 맵"
            }
            2->{
                if(items[position].isPicked){
                    holder.filter_btn.background = ContextCompat.getDrawable(context, R.drawable.filter_background)
                    holder.isChecked = true
                }
                Log.v("209-2", "22")
                holder.fiter_map.text = "데이트 맵"
            }
            3->{
                if(items[position].isPicked){
                    holder.filter_btn.background = ContextCompat.getDrawable(context, R.drawable.filter_background)
                    holder.isChecked = true
                }
                Log.v("209-3", "33")
                holder.fiter_map.text = "역사기행 맵"
            }
            4->{
                if(items[position].isPicked){
                    holder.filter_btn.background = ContextCompat.getDrawable(context, R.drawable.filter_background)
                    holder.isChecked = true
                }
                Log.v("209-4", "44")
                holder.fiter_map.text = "자연탐방 맵"
            }
        }

        /*FIXME
        * holder의 itemView 즉 하나의 아이템이 담긴 뷰를 클릭하면
        * selector이라는 객체에 itemSelect를 담는다.
        * 그러면 selector이 null이 아니면 selector라는 인터페이스의
        * onFilterSelect 함수를 호출하면서 holder, position을 받는다.
        * setFilterSelect라는 인터페이스의 역할은 함수를 정의하고
        * 함수를 사용하는 곳에서 사용자가 원하는 기능에 맞게 구현해서 사용할 수 있도록 함이다.
        * 이렇게 매개변수를 holder, position을 전달할 수 있기 때문에
        * recyclerview에 접근하는 것이 자유롭다.
        * */


        //holder.filter_btn.isSelected = true




        holder.itemView.setOnClickListener{
/*            var selector = itemSelect
            if(selector !=null)
                selector!!.onFilterSelect(holder,position)*/
            Log.v("847 holder id ",holder.itemView.id.toString())
            holder.itemView
            context!!.toast("${position} 번쨰 클릭.")
            Log.v("825 aPosition:",holder.adapterPosition.toString())

            when(position){
                0->{
                    if(!holder.filter_btn.isSelected){
                        Log.v("1128 woo","Selected")
                        holder.filter_btn.isSelected = true
                        holder.filter_btn.background = ContextCompat.getDrawable(context, R.drawable.filter_background)
                      /*  if(holder.adapterPosition == 1){

                            holder.filter_btn.isSelected =false
                        }*/
                    }else{
                        Log.v("1128 woo","UnSelected")
                        holder.filter_btn.isSelected = false
                        holder.filter_btn.background = ContextCompat.getDrawable(context, R.drawable.filter_opacity_background)

                    }

                }
                1->{
                    if(holder.filter_btn.isSelected){
                        Log.v("1128 woo","Selected")
                        holder.filter_btn.isSelected = false
                        holder.filter_btn.background = ContextCompat.getDrawable(context, R.drawable.filter_background)
                    }else{
                        Log.v("1128 woo","UnSelected")
                        holder.filter_btn.isSelected = true
                        holder.filter_btn.background = ContextCompat.getDrawable(context, R.drawable.filter_opacity_background)

                    }
                }
                2->{
                    if(holder.filter_btn.isSelected){
                        Log.v("1128 woo","Selected")
                        holder.filter_btn.isSelected = false
                        holder.filter_btn.background = ContextCompat.getDrawable(context, R.drawable.filter_background)
                    }else{
                        Log.v("1128 woo","UnSelected")
                        holder.filter_btn.isSelected = true
                        holder.filter_btn.background = ContextCompat.getDrawable(context, R.drawable.filter_opacity_background)

                    }
                }
                3->{
                    if(holder.filter_btn.isSelected){
                        Log.v("1128 woo","Selected")
                        holder.filter_btn.isSelected = false
                        holder.filter_btn.background = ContextCompat.getDrawable(context, R.drawable.filter_background)
                    }else{
                        Log.v("1128 woo","UnSelected")
                        holder.filter_btn.isSelected = true
                        holder.filter_btn.background = ContextCompat.getDrawable(context, R.drawable.filter_opacity_background)

                    }
                }
            }
        }

    }

    inner class HomeFilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), Checkable {
        var isCheck : Boolean = false

        var fiter_map: TextView = itemView.filterMapItem
        var filter_btn : RelativeLayout = itemView.filterBtn

        override fun isChecked(): Boolean {
            return isChecked
        }

        override fun toggle() {
            // isCheck가 setCheck와 같음.
            isCheck = !isCheck
        }

        override fun setChecked(checked: Boolean) {
            if(checked){
                isCheck = checked
                filter_btn.background = ContextCompat.getDrawable(context, R.drawable.filter_background)
            }else{
                isCheck = checked
                filter_btn.background = ContextCompat.getDrawable(context, R.drawable.filter_opacity_background)
            }

        }

    }

}