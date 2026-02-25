package cn.jingzhuan.lib.skeleton

import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View

fun View.asSkeleton(show: Boolean, radius: Float = Skeletonable.DEFAULT_CORNER_RADIUS_DP) {
    val isCurrentlyShown = getTag(R.id.skeleton_is_shown) as? Boolean ?: false

    if (show && !isCurrentlyShown) {
        // Enable skeleton
        // Save original background
        setTag(R.id.skeleton_original_background, background)

        val radiusPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            radius,
            resources.displayMetrics
        )
        val skeletonDrawable = SkeletonDrawable(cornerRadius = radiusPx)
        background = skeletonDrawable
        skeletonDrawable.start()

        setTag(R.id.skeleton_is_shown, true)
    } else if (!show && isCurrentlyShown) {
        // Disable skeleton
        val skeleton = background as? SkeletonDrawable
        skeleton?.stop()

        // Restore original background
        val originalBackground = getTag(R.id.skeleton_original_background) as? Drawable
        background = originalBackground

        setTag(R.id.skeleton_original_background, null)
        setTag(R.id.skeleton_is_shown, false)
    }
}
