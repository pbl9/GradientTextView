package com.pbl9.GradientTextView

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.google.android.material.slider.Slider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.pbl9.GradientTextView.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Slider.OnChangeListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tabLayout.addOnTabSelectedListener (object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                when(tab?.position) {
                    0 -> {
                        binding.linearGradientsOptions.isVisible = true
                        binding.radialSettingsLayout.isVisible = false
                        deInitSliders()
                        changeGradient(Orientation.TOP_BOTTOM)
                    }
                    1 -> {
                        binding.linearGradientsOptions.isVisible = false
                        binding.radialSettingsLayout.isVisible = true
                        setupSliders()
                        setRadialGradient()
                    }
                    else -> {}
                }
            }

            override fun onTabUnselected(tab: Tab?) {
            }

            override fun onTabReselected(tab: Tab?) {
            }
        })


        binding.linearGradientsOptions.adapter = GradientAdapter(::changeGradient)

    }

    private fun setupSliders() {
        binding.centerXSlider.addOnChangeListener(this)
        binding.centerYSlider.addOnChangeListener(this)
        binding.radiusPercentageSlider.addOnChangeListener(this)
    }

    private fun deInitSliders() {
        binding.centerXSlider.removeOnChangeListener(this)
        binding.centerYSlider.removeOnChangeListener(this)
        binding.radiusPercentageSlider.removeOnChangeListener(this)
    }
    
    private fun setRadialGradient() {
        val centerX = binding.centerXSlider.value
        val centerY = binding.centerYSlider.value
        val radiusPercentage = binding.radiusPercentageSlider.value

        val rDrawable = (ResourcesCompat.getDrawable(resources, R.drawable.sample_radial_gradient, null) as? GradientDrawable)

        if (rDrawable != null) {
            rDrawable.setGradientCenter(centerX, centerY);
            if(radiusPercentage > 0f) rDrawable.gradientRadius = radiusPercentage * binding.gradientTextView.width;
            binding.gradientTextView.setGradient(rDrawable)
        }
    }

    private fun changeGradient(orientation: Orientation) {
        val colors = (ResourcesCompat.getDrawable(resources, R.drawable.sample_gradient, null) as? GradientDrawable)?.colors ?: return
        binding.gradientTextView.setGradient(
            GradientDrawable(orientation, colors)
        )
    }

    override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
        if(fromUser) setRadialGradient()
    }
}