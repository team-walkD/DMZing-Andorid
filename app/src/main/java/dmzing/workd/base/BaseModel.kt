package dmzing.workd.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by VictoryWoo
 */
open class BaseModel(
    @SerializedName("field")
    @Expose
    open var field: String? = null,
    @SerializedName("message")
    @Expose
    open var message: String? = null

)