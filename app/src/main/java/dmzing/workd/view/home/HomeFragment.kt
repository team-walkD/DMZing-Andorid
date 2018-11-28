package dmzing.workd.view.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.SnapHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dmzing.workd.CommonData
import dmzing.workd.R
import dmzing.workd.model.home.HomeCourseData
import dmzing.workd.model.home.HomeFilterData
import dmzing.workd.model.home.PickCourse
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import dmzing.workd.view.adapter.HomeCourseAdapter
import dmzing.workd.view.adapter.HomeFilterAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.v4.content.ContextCompat.getSystemService
import android.location.LocationManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v4.content.PermissionChecker.checkSelfPermission
import dmzing.workd.view.MainActivity
import org.jetbrains.anko.toast
import java.security.Permission
import java.util.jar.Manifest


/**
 * Created by VictoryWoo
 */
class HomeFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            v -> {
                var index = courseList.getChildAdapterPosition(v!!)
                toast("${index}")
            }
        }
    }

    lateinit var homeCourseAdapter: HomeCourseAdapter
    lateinit var courseItems: PickCourse

    lateinit var homeFilterAdapter: HomeFilterAdapter
    lateinit var filterItems: ArrayList<HomeFilterData>
    lateinit var mainActivity: MainActivity
    lateinit var hView: View
    val MY_LOCATION_REQUEST_CODE = 100
    val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10
    val MIN_TIME_BW_UPDATES: Float = 1F

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainActivity = this@HomeFragment.activity!! as MainActivity
    }

    override fun onResume() {
        super.onResume()
        getHomeMission(view!!)
    }


    lateinit var networkService: NetworkService
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        hView = view
        networkService = ApplicationController.instance.networkService
        SharedPreference.instance!!.load(context!!)


        getLocation()
        requestLocationPermission()


        filterItems = ArrayList()


        /*FIXME
        * android recyclerview를 viewpager처럼 아이템 하나씩 스크롤 할 수 있도록 하기 위한 방법으로
        * snapHelper라는 것을 사용하면 되고 코드는 아래의 두줄로 충분하다.
        * */
        var snapHelper: SnapHelper = PagerSnapHelper()
        //var snapHelper2 : SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(view.courseList)
        //snapHelper2.attachToRecyclerView(view.homeFilterRv)

        //settingHomeRecyclerview(view)
        //settingFilterRecyclerview(view)
        getHomeMission(view)

        return view
    }

    fun requestLocationPermission() {
        if (context!!.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            context!!.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!, android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
                ActivityCompat.requestPermissions(
                    mainActivity,
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ), MY_LOCATION_REQUEST_CODE
                )
            else {
                ActivityCompat.requestPermissions(
                    mainActivity,
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ), MY_LOCATION_REQUEST_CODE
                )
            }
        } else {
            Log.v("859 lat", "ㅇㅇㅇㅇㅇ else")
            getLocation()
        }
    }


    fun getLocation() {
        context!!.toast("testing")
        Log.v("859 lat", "ㅇㅇㅇㅇㅇ get")


        val locationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager

// Define a listener that responds to location updates
        val locationListener = object : LocationListener {

            override fun onLocationChanged(location: Location) {
                // Called when a new location is found by the network location provider.
                context!!.toast("testing2222")
                var lat: Double = location!!.latitude
                var lng: Double = location!!.longitude

                Log.v("859 lat", lat.toString())
                Log.v("859 lng", lng.toString())
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            }

            override fun onProviderEnabled(provider: String) {
            }

            override fun onProviderDisabled(provider: String) {
            }
        }

// Register the listener with the Location Manager to receive location updates
        try {
            Log.v("859 lat", "ㅇㅇㅇㅇㅇ try")
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        } catch (e: SecurityException) {
            e.printStackTrace()
            Log.v("859 lat", "ㅇㅇㅇㅇㅇ catch")
            context!!.toast("error")
        }
        /*  val locationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
          // gps
          var isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
          // 와이파이.
          var isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

          Log.v("855 woo",isGPSEnabled.toString())
          Log.v("855 woo",isNetworkEnabled.toString())

          var locationListener = object : LocationListener {
              override fun onLocationChanged(location: Location?) {
                  var lat : Double = location!!.latitude
                  var lng : Double = location!!.longitude

                  Log.v("859 lat",lat.toString())
                  Log.v("859 lng",lng.toString())
              }

              override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
              }

              override fun onProviderEnabled(p0: String?) {
                  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
              }

              override fun onProviderDisabled(p0: String?) {
                  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
              }
          }

          locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_DISTANCE_CHANGE_FOR_UPDATES,MIN_TIME_BW_UPDATES,locationListener)*/

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            0 -> {
                for (i in 0 until grantResults.size - 1) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        getLocation()
                        activity!!.finish()
                    }
                }
            }
        }
    }


    // 홈 편지 미션 통신
    fun getHomeMission(view: View) {
        var homeResponse = networkService
            .getHomeMissions(SharedPreference.instance!!.getPrefStringData(CommonData.JWT)!!)
        homeResponse.enqueue(object : Callback<HomeCourseData> {
            override fun onFailure(call: Call<HomeCourseData>, t: Throwable) {
                Log.v("853 woo f:", t.message)
            }

            override fun onResponse(call: Call<HomeCourseData>, response: Response<HomeCourseData>) {
                Log.v("853 woo r:", response.code().toString())
                Log.v("853 woo r:", response.body().toString())
                Log.v("853 woo size:", response.body()!!.purchaseList.size.toString())
                when (response.code()!!) {
                    200 -> {
                        settingFilterItems(view, response.body()!!.purchaseList)
                        settingHomeItems(view, response.body()!!.pickCourse)
                    }
                }
            }

        })


    }

    // 필터 아이템 통신
    fun settingFilterItems(view: View, items: ArrayList<HomeFilterData>) {
        filterItems = items
        view.homeFilterRv.setHasFixedSize(true)
        view.homeFilterRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeFilterAdapter = HomeFilterAdapter(filterItems, context!!)
        homeFilterAdapter.setItemClickListener(this)
        /*homeFilterAdapter.setOnFilterSelectListener(object : HomeFilterAdapter.setFilterSelect {
            override fun onFilterSelect(holder: HomeFilterAdapter.HomeFilterViewHolder, position: Int) {
                if (holder.isCheck) {
                    holder.isChecked = false
                } else {
                    // recyclerview의 아이템 들 중에 체크가 되어 있는지 for문을 통해서 검사하는 과정
                    Log.v("142 woo size", view.homeFilterRv.childCount.toString())
                    Log.v("142 woo item size", items.size.toString())
                    Log.v("142 woo item size2", view.homeFilterRv.adapter!!.itemCount.toString())
                    Log.v("142 woo item id?", homeFilterAdapter.getItemId(1).toString())

                    for (i in 0 until view.homeFilterRv.adapter!!.itemCount - 1) {
                        Log.v("142 woo i", i.toString())


                        var viewHolder = view.homeFilterRv.findViewHolderForLayoutPosition(i)!!
                                as HomeFilterAdapter.HomeFilterViewHolder
                        Log.v("142 woo holder position", viewHolder.adapterPosition.toString())
                        Log.v("142 woo viewholder", viewHolder.itemId.toString())
                        if (viewHolder.isCheck) {
                            viewHolder.isChecked = false
                        }
                    }
                    holder.isChecked = true
                    //toast("${holder.fiter_map.text}")
                    putCoursePick(view, items[position].id)
                    toast("${items[position].id}+${holder.fiter_map.text}")

                }
            }

        })*/
        view.homeFilterRv.adapter = homeFilterAdapter

    }

    // 코스 픽하기
    fun putCoursePick(view: View, cid: Int) {
        Log.v("woo 731 put:", "또잉")
        var coursePickResponse = networkService.putCoursePick(
            SharedPreference.instance!!
                .getPrefStringData("jwt")!!, cid
        )
        Log.v("woo 7311 put:", "또잉1")

        coursePickResponse.enqueue(object : Callback<PickCourse> {
            override fun onFailure(call: Call<PickCourse>, t: Throwable) {
                Log.v("woo 731 f:", t.message)
            }

            override fun onResponse(call: Call<PickCourse>, response: Response<PickCourse>) {
                Log.v("woo 731 r:", response.message())
                Log.v("woo 731 r:", response.code().toString())
                when (response!!.code()) {
                    200 -> {
                        settingHomeItems(view, response.body()!!)
                        homeCourseAdapter.notifyDataSetChanged()
                    }

                }
            }

        })
    }

    fun settingHomeItems(view: View, items: PickCourse) {
        courseItems = items
        view.courseList.setHasFixedSize(true)
        view.courseList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeCourseAdapter = HomeCourseAdapter(courseItems, context!!)
        homeCourseAdapter.setOnItemClick(this)
        view.courseList.adapter = homeCourseAdapter
    }


}