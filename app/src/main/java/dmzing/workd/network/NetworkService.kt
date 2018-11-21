package dmzing.workd.network

import dmzing.workd.base.BaseModel
import dmzing.workd.model.map.CourseDetailDto
import dmzing.workd.model.map.CourseMainDto
import dmzing.workd.model.mypage.CourseDatas
import dmzing.workd.model.mypage.GetMyDpPoint
import dmzing.workd.model.mypage.GetMypageCourse
import dmzing.workd.model.mypage.GetMypageInfomation
import dmzing.workd.model.review.PhotoReviewDto
import dmzing.workd.model.review.ReviewCountDto
import dmzing.workd.model.review.SimpleReviewDto
import dmzing.workd.model.review.reviewDto
import dmzing.workd.model.user.LoginUser
import dmzing.workd.model.user.UserDTO
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by VictoryWoo
 */
interface NetworkService {
    /*FIXME
    * Server 통신에 사용될 함수 정의
    * */

    /*== 민형 ==*/

    //리뷰 수 보기
    //edit by 이민형
    @GET("api/reviews/count")
    fun getReviewCounts(
        @Header("jwt") jwt: String
    ): Call<ArrayList<ReviewCountDto>>

    //사진리뷰 전체보기
    //edit by 이민형
    @GET("api/reviews/photo/last/{pid}/course/{type}")
    fun getPhotoReviews(
        @Header("jwt") jwt: String,
        @Path("pid") pid: Int,
        @Path("type") type: String
    ): Call<ArrayList<PhotoReviewDto>>

    //리뷰 전체보기
    //edit by 이민형
    @GET("api/reviews/last/{rid}/course/{type}")
    fun getSimpleReviews(
        @Header("jwt") jwt: String,
        @Path("rid") rid: Int,
        @Path("type") type: String
    ): Call<ArrayList<SimpleReviewDto>>

    //리뷰 상세보기
    //edit by 이민형
    @GET("api/reviews/{rid}")
    fun getDetailReview(
        @Header("jwt") jwt: String,
        @Path("rid") rid: Int
    ): Call<reviewDto>


    //전체 코스 종류 및 정보 보기
    //edit by 이민형
    @GET("api/course")
    fun getCourseList(
        @Header("jwt") jwt: String
    ): Call<ArrayList<CourseMainDto>>

    //코스 상세 보기
    //edit by 이민형
    @GET("api/course/{cid}")
    fun getCourseDetail(
        @Header("jwt") jwt: String,
        @Path("cid") cid: Int
    ): Call<CourseDetailDto>


    //코스 주문하기
    //edit by 이민형
    @POST("api/order/course/{cid}")
    fun postCourseOrder(
        @Header("jwt") jwt: String,
        @Path("cid") cid: Int
    ): Call<CourseDetailDto>


    /*== 승우 ==*/
    // 유저 생성
    // edit by 이승우
    @POST("api/users")
    fun postUserCreate(
        @Body userDTO: UserDTO
    ): Call<Any>


    // 유저 생성
    // edit by 이승우
    @GET("api/users/info")
    fun getMypageUserInformation(
        @Header("jwt") jwt: String
    ): Call<GetMypageInfomation>

    // 로그인 통신
    // edit by 이승우
    @POST("api/users/login")
    fun postLogin(
        @Body loginUser: LoginUser
    ): Call<Any>


    // 마이페이지 DP 조회
    // edit by 이승우
    @GET("api/users/dp")
    fun getDpPoint(
        @Header("jwt ") jwt: String
    ): Call<GetMyDpPoint>

    // 마이페이지 코스 조회
    // edit by 이승우
    @GET("api/users/course")
    fun getMypageCourse(
        @Header("jwt") jwt: String
    ) : Call<List<CourseDatas>>
}




