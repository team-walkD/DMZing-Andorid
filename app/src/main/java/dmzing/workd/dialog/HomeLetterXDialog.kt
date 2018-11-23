package dmzing.workd.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.View
import dmzing.workd.R
import kotlinx.android.synthetic.main.dialog_letter_x.*

/**
 * Created by VictoryWoo
 */
class HomeLetterXDialog(context : Context, var flag : Int) : Dialog(context), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!){
            letterDialogOkayBtn->{
                dismiss()
            }
        }
    }

    //var titles = listOf<String>("편지가 없어요!","숨겨진 편지를")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)
        letterDialogOkayBtn.setOnClickListener(this)

        //setting(flag)
    }

   /* fun setting(flag : Int){
        when(flag){
            0->{
                var spb = SpannableString(titles[0])
                spb.setSpan(context.resources.getFont(R.font.notosanscjkkr_bold),0,4,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                letterTextX.text = spb
            }
        }
    }*/

    companion object {
        private val LAYOUT = R.layout.dialog_letter_x
    }
}