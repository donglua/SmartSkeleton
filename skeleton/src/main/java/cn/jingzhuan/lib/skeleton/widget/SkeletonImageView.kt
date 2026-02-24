package cn.jingzhuan.lib.skeleton.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatImageView
import cn.jingzhuan.lib.skeleton.R
import cn.jingzhuan.lib.skeleton.SkeletonDrawable
import cn.jingzhuan.lib.skeleton.Skeletonable

class SkeletonImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), Skeletonable {

    override var isSkeletonShown: Boolean = false
    override var skeletonCornerRadius: Float = 8f
    private var skeletonBaseColor: Int = -0x1f1f20
    private var skeletonHighlightColor: Int = -0x111112

    private var originalBackground: Drawable? = null

    init {
        val defaultRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            8f,
            resources.displayMetrics
        )
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Skeletonable)
        skeletonCornerRadius = typedArray.getDimension(R.styleable.Skeletonable_skeletonRadius, defaultRadius)
        skeletonBaseColor = typedArray.getColor(R.styleable.Skeletonable_skeletonBaseColor, -0x1f1f20)
        skeletonHighlightColor = typedArray.getColor(R.styleable.Skeletonable_skeletonHighlightColor, -0x111112)
        typedArray.recycle()

        if (drawable == null) {
            showSkeleton()
        }
    }

    override fun showSkeleton() {
        if (isSkeletonShown) return

        originalBackground = background
        isSkeletonShown = true

        super.setImageDrawable(null)

        val skeletonDrawable = SkeletonDrawable(
            baseColor = skeletonBaseColor,
            highlightColor = skeletonHighlightColor,
            cornerRadius = skeletonCornerRadius
        )
        background = skeletonDrawable
        skeletonDrawable.start()
    }

    override fun hideSkeleton() {
        if (!isSkeletonShown) return

        (background as? SkeletonDrawable)?.stop()
        background = originalBackground
        isSkeletonShown = false
    }

    override fun setImageDrawable(drawable: Drawable?) {
        if (drawable != null) {
            if (isSkeletonShown) {
                hideSkeleton()
            }
            super.setImageDrawable(drawable)
        } else {
            if (!isSkeletonShown) {
                showSkeleton()
            } else {
                super.setImageDrawable(null)
            }
        }
    }

    fun bindImage(url: String?) {
        if (url.isNullOrEmpty()) {
            showSkeleton()
        }
    }
}
