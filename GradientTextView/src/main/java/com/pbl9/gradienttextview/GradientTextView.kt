package com.pbl9.gradienttextview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.use


class GradientTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyle: Int = 0
) : AppCompatTextView(context, attrs, defStyle) {


    private var gradientFactory: GradientFactory? =
        context.obtainStyledAttributes(attrs, R.styleable.GradientTextView).use {
            kotlin.runCatching { it.getDrawable(R.styleable.GradientTextView_gradient) }.getOrNull()
        }?.takeIf { it is GradientDrawable && it.colors != null }?.let { it as? GradientDrawable }
            ?.let {
                GradientFactory.from(it)
            }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            applyGradient()
        }
    }

    private fun applyGradient() {
        gradientFactory?.let { factory ->
            paint.shader = factory.createGradient(width.toFloat(), height.toFloat())
        }
    }


    fun setGradient(gradientDrawable: GradientDrawable) {
        gradientFactory = GradientFactory.from(gradientDrawable)
        applyGradient()
        invalidate()
    }
}

