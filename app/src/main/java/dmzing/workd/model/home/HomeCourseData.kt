package dmzing.workd.model.home

/**
 * Created by VictoryWoo
 */
data class HomeCourseData(

    var purchaseList: ArrayList<HomeFilterData>,
    var pickCourse: PickCourse

)

data class PickCourse(
    var id: Int,
    var title: String,
    var mainDescription: String,
    var subDescription: String,
    var imageUrl: String,
    var lineImageUrl: String,
    var level: String,
    var estimatedTime: String,
    var price: String,
    var backgroundImageUrl: String,
    var places: ArrayList<Places>,
    var reviewCount: Int
)

data class Places(
    var id: Int,
    var hint: String,
    var letterImageUrl: String?,
    var reward: Int,
    var sequence: Int,
    var mainImageUrl: String,
    var subImageUrl: String,
    var title: String,
    var latitude: Double?,
    var longitude: Double,
    var address: String,
    var description: String,
    var parking: String,
    var restDate: String,
    var infoCenter: String,
    var peripheries: ArrayList<Peripheries>
)

data class Peripheries(
    var title: String,
    var firstimage: String,
    var contenttypeid: Int
)

