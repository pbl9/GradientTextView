package com.pbl9.GradientTextView

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.Orientation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.pbl9.gradienttextview.GradientTextView

class MainActivity : AppCompatActivity() {
    private lateinit var gradientTextView: GradientTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gradientTextView = findViewById(R.id.gradient_text_view)

        val list = findViewById<RecyclerView>(R.id.linear_gradients_options)
        list.adapter = GradientAdapter(::changeGradient)
    }
    private fun changeGradient(orientation: Orientation) {
        val colors = (ResourcesCompat.getDrawable(resources, R.drawable.sample_gradient, null) as? GradientDrawable)?.colors ?: return
        gradientTextView.setGradient(
            GradientDrawable(orientation, colors)
        )
    }
}