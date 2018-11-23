package dmzing.workd.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import dmzing.workd.R
import kotlinx.android.synthetic.main.dialog_letter_find.*

/**
 * Created by VictoryWoo
 */
class HomeLetterFindDialog(context: Context) : Dialog(context), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!){
            letterFindDialogOkayBtn->{
                dismiss()
            }
        }
    }


    companion object {
        private val LAYOUT = R.layout.dialog_letter_find
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)
        letterFindDialogOkayBtn.setOnClickListener(this)

    }
}