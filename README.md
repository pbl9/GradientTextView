# GradientTextView
Android TextView but GradientDrawable can be applied to it. Supports linear and radial gradient. Works for API above level 24.

## How to use it
Start from defining GradientDrawable in your app's resources. You should define colors in your GradientDrawable. 
If it is RadialGradient gradientRadius provided as fraction not working, better solution is set it is in dp units.
Next in XML set that drawable to GradientTextView attribute:
```
   app:gradient="@drawable/your_gradient_drawable"
```
To use it in Kotlin/Java GradientTextView has given method:
```
   setGradient(gradientDrawable: GradientDrawable)
