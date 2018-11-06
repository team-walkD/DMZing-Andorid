package dmzing.workd.view.review

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dmzing.workd.R
import dmzing.workd.model.review.PhotoReviewModel
import dmzing.workd.util.GridItemDecoration
import dmzing.workd.view.adapter.PhotoReviewListAdapter
import kotlinx.android.synthetic.main.fragment_photo_review_list.view.*

class PhotoReviewFragment : Fragment() {
    lateinit var mGridLayoutManager: GridLayoutManager
    lateinit var mPhotoReviewItems : ArrayList<PhotoReviewModel>
    lateinit var mPhotoReviewAdpater : PhotoReviewListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_photo_review_list, container, false)

        mPhotoReviewItems = ArrayList()

        mPhotoReviewItems.add(PhotoReviewModel("",0,"평화전망대","#뭐지 #뭐긴",1))
        mPhotoReviewItems.add(PhotoReviewModel("",0,"평화전망대","#뭐지 #뭐긴",2))
        mPhotoReviewItems.add(PhotoReviewModel("",0,"평화전망대","#뭐지 #뭐긴",3))
        mPhotoReviewItems.add(PhotoReviewModel("",0,"평화전망대","#뭐지 #뭐긴",4))
        mPhotoReviewItems.add(PhotoReviewModel("",0,"평화전망대","#뭐지 #뭐긴",5))
        mPhotoReviewItems.add(PhotoReviewModel("",0,"평화전망대","#뭐지 #뭐긴",6))

        mPhotoReviewAdpater = PhotoReviewListAdapter(mPhotoReviewItems,activity!!.applicationContext)

        mGridLayoutManager = GridLayoutManager(activity,2)
        view.photo_review_recycler.layoutManager = mGridLayoutManager
        view.photo_review_recycler.adapter = mPhotoReviewAdpater

        var px = Math.round(convertDpToPixel(10.2f,activity!!))
        view.photo_review_recycler.addItemDecoration(GridItemDecoration(px))

        return view
    }

    fun convertDpToPixel(dp : Float,context : Context) : Float{
        var resources = context.resources

        var metrics = resources.displayMetrics

        var px = dp * (metrics.densityDpi / 160f)

        return px
    }
}