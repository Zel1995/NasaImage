package com.example.nasaimage

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.ChangeImageTransform
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val imageView = findViewById<ImageView>(R.id.splash_image_view)
        val layout = findViewById<ConstraintLayout>(R.id.splash_layout)
        imageView.animate()
            .setDuration(2000)
            .rotationBy(1000f)
            .scaleXBy(-1f)
            .scaleYBy(-1f)
            .rotationBy(100f)
            .rotationXBy(100f)
            .alpha(0.5f)
            .setInterpolator(AccelerateInterpolator())
            .setListener(object : Animator.AnimatorListener{
                override fun onAnimationEnd(animation: Animator?) {
                    startActivity(Intent(this@SplashActivity,MainActivity::class.java))
                    finish()
                }

                override fun onAnimationStart(animation: Animator?) {}

                override fun onAnimationCancel(animation: Animator?) {}

                override fun onAnimationRepeat(animation: Animator?) {}
            })
    }
}