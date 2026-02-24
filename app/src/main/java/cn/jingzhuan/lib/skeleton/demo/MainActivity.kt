package cn.jingzhuan.lib.skeleton.demo

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.jingzhuan.lib.skeleton.asSkeleton
import cn.jingzhuan.lib.skeleton.widget.SkeletonImageView
import cn.jingzhuan.lib.skeleton.widget.SkeletonTextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ivAvatar = findViewById<SkeletonImageView>(R.id.iv_avatar)
        val tvTitle = findViewById<SkeletonTextView>(R.id.tv_title)
        val tvDescription = findViewById<SkeletonTextView>(R.id.tv_description)
        val clContainer = findViewById<View>(R.id.cl_container)

        // Initial state: Skeleton shows automatically because text/drawable are null/empty.
        // For container, we use extension
        clContainer.asSkeleton(true)

        // Simulate network request
        Handler(Looper.getMainLooper()).postDelayed({
            // Update data
            tvTitle.text = getString(R.string.demo_title)
            tvDescription.text = getString(R.string.demo_description)

            // For ImageView, we set a drawable
            ivAvatar.setImageDrawable(ColorDrawable(Color.BLUE)) // Simulate an image

            // Extension
            clContainer.asSkeleton(false)

        }, 2000)
    }
}
