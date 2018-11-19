package dmzing.workd.model.review

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dmzing.workd.base.BaseModel

data class ReviewCountDto (
    @SerializedName("typeName")
    @Expose
    var typeName : String? = null,
    @SerializedName("conut")
    @Expose
    var conut : Int? = null,
    @SerializedName("imageUrl")
    @Expose
    var imageUrl : String? = null,
    @SerializedName("courseId")
    @Expose
    var courseId : Int? = null

) : BaseModel()