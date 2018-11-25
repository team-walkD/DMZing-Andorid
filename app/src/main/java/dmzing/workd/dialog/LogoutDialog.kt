package dmzing.workd.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import dmzing.workd.R
import dmzing.workd.util.SharedPreference
import dmzing.workd.view.login.LoginActivity
import kotlinx.android.synthetic.main.dialog_logout.*
import org.jetbrains.anko.startActivity

/**
 * Created by VictoryWoo
 */
class LogoutDialog(context: Context) : Dialog(context), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            dialog_logout_okay -> {
                SharedPreference.instance!!.removeAllData(context)
                var intent = Intent(context, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context!!.startActivity(intent)

            }
            dialog_logout_cancel -> {
                dismiss()
            }
        }
    }

    companion object {
        private val LAYOUT = R.layout.dialog_logout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)
        SharedPreference.instance!!.load(context)
        dialog_logout_okay.setOnClickListener(this)
        dialog_logout_cancel.setOnClickListener(this)

    }
}