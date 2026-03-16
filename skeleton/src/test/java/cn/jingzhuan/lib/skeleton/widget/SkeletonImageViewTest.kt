package cn.jingzhuan.lib.skeleton.widget

import cn.jingzhuan.lib.skeleton.SkeletonDrawable
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertSame
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class SkeletonImageViewTest {

    @Test
    fun testSkeletonDrawableCaching() {
        val context = RuntimeEnvironment.getApplication()
        val skeletonImageView = SkeletonImageView(context)

        // Initial show
        skeletonImageView.showSkeleton()
        val firstDrawable = skeletonImageView.background as? SkeletonDrawable
        assertNotNull("First drawable should not be null", firstDrawable)

        // Hide
        skeletonImageView.hideSkeleton()

        // Show again
        skeletonImageView.showSkeleton()
        val secondDrawable = skeletonImageView.background as? SkeletonDrawable
        assertNotNull("Second drawable should not be null", secondDrawable)

        assertSame("SkeletonDrawable should be cached and reused", firstDrawable, secondDrawable)
    }
}
