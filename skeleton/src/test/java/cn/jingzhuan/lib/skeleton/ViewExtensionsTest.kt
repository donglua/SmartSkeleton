package cn.jingzhuan.lib.skeleton

import android.view.View
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertSame
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ViewExtensionsTest {

    @Test
    fun testAsSkeletonCaching() {
        val context = RuntimeEnvironment.getApplication()
        val view = View(context)

        // Initial show
        view.asSkeleton(true)
        val firstDrawable = view.background as? SkeletonDrawable
        assertNotNull("First drawable should not be null", firstDrawable)

        // Hide
        view.asSkeleton(false)

        // Show again
        view.asSkeleton(true)
        val secondDrawable = view.background as? SkeletonDrawable
        assertNotNull("Second drawable should not be null", secondDrawable)

        assertSame("SkeletonDrawable should be cached and reused", firstDrawable, secondDrawable)
    }
}
