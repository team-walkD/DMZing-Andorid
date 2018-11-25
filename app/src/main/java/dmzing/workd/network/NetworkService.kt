package dmzing.workd.network

import dmzing.workd.base.BaseModel
import dmzing.workd.model.home.HomeCourseData
import dmzing.workd.model.home.HomePostMission
import dmzing.workd.model.home.PickCourse
import dmzing.workd.model.home.Places
import dmzing.workd.model.map.CourseDetailDto
import dmzing.workd.model.map.CourseMainDto
import dmzing.workd.model.mypage.CourseDatas
import dmzing.workd.model.mypage.GetMyDpPoint
import dmzing.workd.model.mypage.GetMypageInfomation
import dmzing.workd.model.mypage.review.GetMypageReviewData
import dmzing.workd.model.review.*
import dmzing.workd.model.user.LoginUser
import dmzing.workd.model.user.UserDTO
import okhttp3.MultipartBody
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

    //리뷰 이미지 등록
    @Multipart
    @POST("api/reviews/images")
    fun postRegistImage(
            @Header("jwt") jwt : String,
            @Part data : MultipartBody.Part?
    ) : Call<ImageDto>

    //상세 리뷰 작성
    @Headers("Content-type: application/json")
    @POST("api/reviews")
    fun postDetailReview(
            @Header("jwt") jwt : String,
            @Body reviewDto : reviewDto
    ) : Call<Any>

    //사진 리뷰 작성
    @Headers("Content-type: application/json")
    @POST("api/reviews/photo")
    fun postPhotoReview(
            @Header("jwt") jwt : String,
            @Body writePhotoReviewDto: WritePhotoReviewDto
    ) : Call<Any>

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





    /*FIXME
    * 중요한 것!
    * 우리가 토큰을 헤더에 담아서 보내듯이
    * @Header가 1개 이상 지정될 수 있다.
    * 보통 Spring 서버와 통신을 할 때는
    * Content-type을 지정해서 보내줘야 하는데,
    * 이 경우 @Header("Content-type") Content-type : String
    * 하고 @Header("jwt") jwt : String
    * 해서 보낸다.
    * */
    /*== 승우 ==*/
    // 유저 생성
    // edit by 이승우

    // 여기는 @Headers가 없어야 함..
    //@Headers("Content-type: application/json")
    @POST("api/users")
    fun postUserCreate(
        @Body userDTO: UserDTO
    ): Call<Any>



    // 유저 정보
    // edit by 이승우
    //@Headers("Content-type: application/json")
    @GET("api/users/info")
    fun getMypageUserInformation(
        @Header("jwt") jwt: String
    ): Call<GetMypageInfomation>


    /*FIXME
    * 이 경우 서버에서 통신에 성공했다 하더라도 body를 날려주지 않고
    * header에 jwt 즉 토큰을 담아서 줄 수 있다.
    * 그러면 결국에 response는 아무것도 받는 것이 아니기 때문에
    * Call<~~> 지정하게 되면 에러를 경험할 수 있다.
    * 그래서 Any로 지정해주고
    * 하나의 클래스를 만들어서 (NullOnEmptyConverterFactory)
    * response의 body가 null인지 체크하고 예외를 처리해준다.
    * 즉, null 이어도 예외를 처리할 수 있다. 즉 null인 body를 변환해주는 것 같음.
    * 추후에 공부 필요.
    * */
    // 로그인 통신
    // edit by 이승우

    /*FIXME
    * retrofit 객체 생성 시에 헤더를 세팅해주면
    * 밑에 처럼 작성하지 않아도 됨. 리팩토링 할 것!
    * */
    // 로그인 구현
    // edit by 이승우
    @Headers("Content-type: application/json")
    @POST("api/users/login")
    fun postLogin(
        @Body loginUser: LoginUser
    ): Call<Any>


    // 마이페이지 DP 조회
    // edit by 이승우

    @GET("api/users/dp")
    fun getDpPoint(
        @Header("jwt") jwt: String
    ): Call<GetMyDpPoint>


    /*FIXME
    * 서버에서 응답이 그냥 객체로 쌓여서 올 때, 즉 키 값이 없을 때
    * 안에 키 값을 가진 객체가 하나 더 있다고 생각한다.
    * Expected BEGIN_OBJECT but was BEGIN_ARRAY at line 1 column 2 path $
    * 이 오류는 객체가 예상되지만 실제 데이터는 배열이라서 받는 형식을 배열로 변경해야 한다.
    * */

    // 마이페이지 코스 조회
    // edit by 이승우
    @Headers("Content-type: application/json")
    @GET("api/users/course")
    fun getMypageCourse(
        @Header("jwt") jwt: String
    ) : Call<List<CourseDatas>>

    // 마이페이지 리뷰 조회
    // edit by 이승우
    @GET("api/users/reviews")
    fun getMypageReviews(
        @Header("jwt") jwt: String
    ) : Call<List<GetMypageReviewData>>


    // 홈 화면에서 데이터 불러오기
    // edit by 이승우
    @GET("api/mission")
    fun getHomeMissions(
        @Header("jwt") jwt: String
    ) : Call<HomeCourseData>


    // 미션 찾기
    @Headers("Content-type: application/json")
    @POST("api/mission")
    fun postMission(
        @Header("jwt") jwt: String,
        @Body homePostMission: HomePostMission
    ) : Call<ArrayList<Places>>
    /*FIXME
    * post로 보낼 때 @Headers에 Content-type : application/json을 명시해주어야 한다.
    * 그렇지 않으면 서버에 값이 제대로 json 형태로 들어가지 않음.
    * */

    // curse pick 하기
    // edit by 이승우
    //@Headers("Content-type: application/json")
    @PUT("api/course/pick/{cid}")
    fun putCoursePick(
        @Header("jwt") jwt : String,
        @Path("cid") cid : Int
    ) : Call<PickCourse>
}




