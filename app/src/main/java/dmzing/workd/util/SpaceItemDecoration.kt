package dmzing.workd.util

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class SpaceItemDecoration(var space : Int,var side : Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val isLast = position == state.itemCount - 1
        if (position == state.itemCount - 1) {
            outRect.right = side //don't forget about recycling...
            outRect.left = space
        } else if (position == 0) {
            outRect.right = space //don't forget about recycling...
            outRect.left = side
        } else {
            outRect.left = space
            outRect.right = space
        }
    }

}