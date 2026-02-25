package cn.jingzhuan.lib.skeleton

interface Skeletonable {
    var isSkeletonShown: Boolean
    var skeletonCornerRadius: Float
    fun showSkeleton()
    fun hideSkeleton()

    companion object {
        const val DEFAULT_BASE_COLOR = -0x1f1f20
        const val DEFAULT_HIGHLIGHT_COLOR = -0x111112
        const val DEFAULT_CORNER_RADIUS_DP = 4f
    }
}
