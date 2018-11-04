package dmzing.workd.view.mypage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Adapter
import dmzing.workd.R
import dmzing.workd.view.adapter.MypageLetterAdapter
import kotlinx.android.synthetic.main.activity_my_letter.*

class MyLetterActivity : AppCompatActivity() {

    lateinit var letterAdapter: MypageLetterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_letter)

        var testList : ArrayList<String> = ArrayList()

        testList.add("test 1")
        testList.add("test 1")
        testList.add("test 1")
        testList.add("test 1")
        testList.add("test 1")
        testList.add("test 1")
        testList.add("test 1")
        testList.add("test 1")
        testList.add("test 1")
        testList.add("test 1")
        testList.add("test 1")
        testList.add("test 1")
        testList.add("test 1")
        testList.add("test 1")
        testList.add("test 1")
        testList.add("test 1")
        testList.add("test 1")
        testList.add("test 1")
        testList.add("test 1")
        testList.add("test 1")

        letterAdapter = MypageLetterAdapter(testList,this)

        letter_recycler.layoutManager = LinearLayoutManager(this)
        letter_recycler.adapter = letterAdapter


    }
}
