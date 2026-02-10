package cn.jingzhuan.lib.skeleton

interface Skeletonable {
    var isSkeletonShown: Boolean
    var skeletonCornerRadius: Float
    fun showSkeleton()
    fun hideSkeleton()
}
