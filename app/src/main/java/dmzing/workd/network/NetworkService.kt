package dmzing.workd.network

import dmzing.workd.model.review.ReviewCountDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * Created by VictoryWoo
 */
interface NetworkService {
    /*FIXME
    * Server 통신에 사용될 함수 정의
    * */

    //리뷰 수 보기
    //edit by 이민형
    @GET("api/reviews/count")
    fun getReviewCounts(
        @Header("jwt") jwt : String
    ) : Call<ArrayList<ReviewCountDto>>

}