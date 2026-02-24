package cn.jingzhuan.lib.skeleton

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.view.animation.LinearInterpolator

class SkeletonDrawable(
    private val baseColor: Int = 0xFFE0E0E0.toInt(),
    private val highlightColor: Int = 0xFFEEEEEE.toInt(),
    private val cornerRadius: Float = 0f,
    private val shimmerDuration: Long = 1000L
) : Drawable(), Animatable {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val boundsRect = RectF()
    private val gradientMatrix = Matrix()
    private var valueAnimator: ValueAnimator? = null
    private var translateX = 0f

    private val colors = intArrayOf(baseColor, highlightColor, baseColor)
    private val positions = floatArrayOf(0.25f, 0.5f, 0.75f)
    private var lastWidth = -1f

    init {
        paint.style = Paint.Style.FILL
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        boundsRect.set(bounds)
        updateShader()
    }

    private fun updateShader() {
        if (boundsRect.isEmpty) return

        val width = boundsRect.width()
        if (width == lastWidth && paint.shader != null) return
        lastWidth = width

        val gradient = LinearGradient(
            0f, 0f, width, 0f,
            colors,
            positions,
            Shader.TileMode.CLAMP
        )
        paint.shader = gradient
    }

    override fun draw(canvas: Canvas) {
        if (boundsRect.isEmpty) return

        val width = boundsRect.width()
        // Map 0..1 to -width..width
        // But to make it smooth across the width, we usually translate from -width to width.
        // Let's recheck the logic.
        // 0 -> -width
        // 1 -> +width (or 2*width if we want full traverse)

        // Let's say gradient width is `width`.
        // We want the highlight (center) to move from left to right.
        // The highlight is at 0.5 * width in the gradient definition.
        // If translate is 0, highlight is at 0.5 * width.
        // If translate is -width/2, highlight is at 0.
        // If translate is width/2, highlight is at width.

        // So we want translation from -width to width?
        // Actually, the gradient is defined from 0 to width.
        // To animate, we translate the shader.
        // If we translate by T, the color at X becomes the color at X-T in the original gradient?
        // Let's just use standard logic: translate from -width to width.

        val translate = (translateX * 3 * width) - width // Move from -width to 2*width for a wider sweep?
        // Or simply:
        // gradient is 0..width.
        // We want to shift it so it passes through the view.

        gradientMatrix.setTranslate(translate, 0f)
        paint.shader?.setLocalMatrix(gradientMatrix)

        canvas.drawRoundRect(boundsRect, cornerRadius, cornerRadius, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    override fun start() {
        if (valueAnimator?.isRunning == true) return

        valueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = shimmerDuration
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                translateX = it.animatedValue as Float
                invalidateSelf()
            }
            start()
        }
    }

    override fun stop() {
        valueAnimator?.cancel()
        valueAnimator = null
    }

    override fun isRunning(): Boolean {
        return valueAnimator?.isRunning == true
    }
}
