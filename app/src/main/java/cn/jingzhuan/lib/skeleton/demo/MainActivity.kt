package cn.jingzhuan.lib.skeleton.demo

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import cn.jingzhuan.lib.skeleton.asSkeleton
import cn.jingzhuan.lib.skeleton.widget.SkeletonImageView
import cn.jingzhuan.lib.skeleton.widget.SkeletonTextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val ivAvatar = findViewById<SkeletonImageView>(R.id.iv_avatar)
        val tvName = findViewById<SkeletonTextView>(R.id.tv_name)
        val tvBio = findViewById<SkeletonTextView>(R.id.tv_bio)

        val ivNewsBanner = findViewById<SkeletonImageView>(R.id.iv_news_banner)
        val tvNewsTitle = findViewById<SkeletonTextView>(R.id.tv_news_title)
        val tvNewsSnippet = findViewById<SkeletonTextView>(R.id.tv_news_snippet)

        val clExtensionContainer = findViewById<View>(R.id.cl_extension_container)
        val fabRefresh = findViewById<FloatingActionButton>(R.id.fab_refresh)

        fun loadData() {
            // Reset to skeleton state (clear content)
            ivAvatar.setImageDrawable(null)
            tvName.text = ""
            tvBio.text = ""

            ivNewsBanner.setImageDrawable(null)
            tvNewsTitle.text = ""
            tvNewsSnippet.text = ""

            // For container extension, explicitly show skeleton
            clExtensionContainer.asSkeleton(true)

            fabRefresh.isEnabled = false

            // Simulate network delay
            Handler(Looper.getMainLooper()).postDelayed({
                if (isDestroyed || isFinishing) return@postDelayed

                // Populate Profile
                // Using a color drawable to simulate an image loaded
                ivAvatar.setImageDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.teal_200)))
                tvName.text = getString(R.string.profile_name)
                tvBio.text = getString(R.string.profile_bio)

                // Populate News
                ivNewsBanner.setImageDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.purple_200)))
                tvNewsTitle.text = getString(R.string.news_article_title)
                tvNewsSnippet.text = getString(R.string.news_article_snippet)

                // Hide skeleton for container extension
                clExtensionContainer.asSkeleton(false)

                fabRefresh.isEnabled = true
            }, 2000)
        }

        fabRefresh.setOnClickListener {
            loadData()
        }

        // Initial load
        loadData()
    }
}
