package com.pbl9.gradienttextview

import android.graphics.LinearGradient
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable

data class RadialGradientParams(
    val centerX: Float = 0f,
    val centerY: Float = 0f,
    val radius: Float = 0f
) {
    companion object {
        fun from(gradientDrawable: GradientDrawable) = RadialGradientParams(
            centerX = gradientDrawable.gradientCenterX,
            centerY = gradientDrawable.gradientCenterY,
            radius = gradientDrawable.gradientRadius
        )
    }
}

interface GradientFactory {
    fun createGradient(width: Float, height: Float): Shader
    companion object {
        @Throws(IllegalStateException::class)
        fun from(gradientDrawable: GradientDrawable): GradientFactory {
            val colors = gradientDrawable.colors ?: throw IllegalStateException("Provided GradientDrawable should have defined colors")
            return when(gradientDrawable.gradientType) {
                GradientDrawable.RADIAL_GRADIENT -> RadialGradientFactory(
                    colors,
                    RadialGradientParams.from(gradientDrawable)
                )
                else -> LinearGradientFactory(
                    colors,
                    gradientDrawable.orientation
                )
            }
        }
    }
}

class LinearGradientFactory(val colors: IntArray,
                            val orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM): GradientFactory {
    override fun createGradient(width: Float, height: Float): Shader {
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
}

class RadialGradientFactory(val colors: IntArray,
    val radialGradientParams: RadialGradientParams): GradientFactory {
    override fun createGradient(width: Float, height: Float): Shader {
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
