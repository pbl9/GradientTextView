package com.pbl9.gradienttextview

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.LINEAR_GRADIENT
import android.graphics.drawable.GradientDrawable.RADIAL_GRADIENT
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.use


data class RadialGradientParams(
    val centerX: Float = 0f,
    val centerY: Float = 0f,
    val radius: Float = 0f
)

class GradientFactory(
    gradientDrawable: GradientDrawable,
    val colors: IntArray,
    val orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM,
    val radialGradientParams: RadialGradientParams = RadialGradientParams()
) {
    val gradientType = gradientDrawable.gradientType
    init {
        require(gradientType == LINEAR_GRADIENT || gradientType == RADIAL_GRADIENT)
    }
    fun createGradient(width: Float, height: Float): Shader = when (gradientType) {
        RADIAL_GRADIENT -> createRadialGradient(width, height)
        else -> createLinearGradient(width, height)
    }

    private fun createLinearGradient(width: Float, height: Float): Shader {
        return when (orientation) {
            GradientDrawable.Orientation.TOP_BOTTOM -> LinearGradient(
                0f,
                0f,
                0f,
                height,
                colors,
                null,
                Shader.TileMode.CLAMP
            )
            GradientDrawable.Orientation.TR_BL -> LinearGradient(
                width,
                0f,
                0f,
                height,
                colors,
                null,
                Shader.TileMode.CLAMP
            )
            GradientDrawable.Orientation.RIGHT_LEFT -> LinearGradient(
                width,
                0f,
                0f,
                0f,
                colors,
                null,
                Shader.TileMode.CLAMP
            )
            GradientDrawable.Orientation.BR_TL -> LinearGradient(
                width,
                height,
                0f,
                0f,
                colors,
                null,
                Shader.TileMode.CLAMP
            )
            GradientDrawable.Orientation.BOTTOM_TOP -> LinearGradient(
                0f,
                height,
                0f,
                0f,
                colors,
                null,
                Shader.TileMode.CLAMP
            )
            GradientDrawable.Orientation.BL_TR -> LinearGradient(
                0f,
                height,
                width,
                0f,
                colors,
                null,
                Shader.TileMode.CLAMP
            )
            GradientDrawable.Orientation.LEFT_RIGHT -> LinearGradient(
                0f,
                0f,
                width,
                0f,
                colors,
                null,
                Shader.TileMode.CLAMP
            )
            GradientDrawable.Orientation.TL_BR -> LinearGradient(
                0f,
                height,
                width,
                height,
                colors,
                null,
                Shader.TileMode.CLAMP
            )
        }

    }

    private fun createRadialGradient(width: Float, height: Float): Shader {
        return RadialGradient(
            radialGradientParams.centerX * width,
            radialGradientParams.centerY * height,
            radialGradientParams.radius,
            colors,
            null,
            Shader.TileMode.CLAMP
        )
    }
}


class GradientTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyle: Int = 0
) : AppCompatTextView(context, attrs, defStyle) {


    private var gradientFactory =
        context.obtainStyledAttributes(attrs, R.styleable.GradientTextView).use {
            kotlin.runCatching { it.getDrawable(R.styleable.GradientTextView_gradient) }.getOrNull()
        }?.takeIf { it is GradientDrawable && it.colors != null }?.let { it as? GradientDrawable }
            ?.toGradientFactory()

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            applyGradient()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        applyGradient()
        super.onDraw(canvas)
    }

    private fun applyGradient() {
        gradientFactory?.let { factory ->
            paint.shader = factory.createGradient(width.toFloat(), height.toFloat())
        }
    }

    private fun GradientDrawable.toGradientFactory(): GradientFactory {
        return GradientFactory(
            this,
            colors!!,
            orientation,
            RadialGradientParams(
                gradientCenterX,
                gradientCenterY,
                gradientRadius
            )
        )
        }

    fun setGradient(gradientDrawable: GradientDrawable) {
        gradientFactory = gradientDrawable.toGradientFactory()
        invalidate()
    }
}

