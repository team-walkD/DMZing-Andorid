package dmzing.workd.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import dmzing.workd.R
import kotlinx.android.synthetic.main.dialog_point_get.*

/**
 * Created by VictoryWoo
 */
class HomePointGetDialog(context: Context) : Dialog(context), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!){
            pointGetDialogOkayBtn->{
                dismiss()
            }
        }
    }

    companion object {
        private val LAYOUT = R.layout.dialog_point_get
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)
        pointGetDialogOkayBtn.setOnClickListener(this)
    }
}