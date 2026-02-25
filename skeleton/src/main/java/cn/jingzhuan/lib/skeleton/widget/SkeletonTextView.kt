package cn.jingzhuan.lib.skeleton.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import cn.jingzhuan.lib.skeleton.R
import cn.jingzhuan.lib.skeleton.SkeletonDrawable
import cn.jingzhuan.lib.skeleton.Skeletonable

class SkeletonTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr), Skeletonable {

    override var isSkeletonShown: Boolean = false
    override var skeletonCornerRadius: Float = 8f
    private var skeletonBaseColor: Int = Skeletonable.DEFAULT_BASE_COLOR
    private var skeletonHighlightColor: Int = Skeletonable.DEFAULT_HIGHLIGHT_COLOR

    private var originalBackground: Drawable? = null
    private var originalTextColor: Int = 0

    init {
        val defaultRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            Skeletonable.DEFAULT_CORNER_RADIUS_DP,
            resources.displayMetrics
        )
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Skeletonable)
        skeletonCornerRadius = typedArray.getDimension(R.styleable.Skeletonable_skeletonRadius, defaultRadius)
        skeletonBaseColor = typedArray.getColor(R.styleable.Skeletonable_skeletonBaseColor, Skeletonable.DEFAULT_BASE_COLOR)
        skeletonHighlightColor = typedArray.getColor(R.styleable.Skeletonable_skeletonHighlightColor, Skeletonable.DEFAULT_HIGHLIGHT_COLOR)
        typedArray.recycle()

        if (text.isNullOrEmpty()) {
            showSkeleton()
        }
    }

    override fun showSkeleton() {
        if (isSkeletonShown) return

        originalBackground = background
        originalTextColor = currentTextColor
        isSkeletonShown = true

        val skeletonDrawable = SkeletonDrawable(
            baseColor = skeletonBaseColor,
            highlightColor = skeletonHighlightColor,
            cornerRadius = skeletonCornerRadius
        )
        background = skeletonDrawable
        skeletonDrawable.start()

        setTextColor(Color.TRANSPARENT)
    }

    override fun hideSkeleton() {
        if (!isSkeletonShown) return

        (background as? SkeletonDrawable)?.stop()
        background = originalBackground
        setTextColor(originalTextColor)
        isSkeletonShown = false
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)

        if (text.isNullOrEmpty()) {
            if (!isSkeletonShown) showSkeleton()
        } else {
            if (isSkeletonShown) hideSkeleton()
        }
    }
}
