package dmzing.workd.model.review

data class ReportDto (
        var reviewId : Int,
        var reportType : String,
        var content : String? = null
)