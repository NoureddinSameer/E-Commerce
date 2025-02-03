package com.training.ecommerce

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.training.ecommerce.utils.AddToCartException
import com.training.ecommerce.utils.CrashlyticsUtils
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        initSplashScreen()
        super.onCreate(savedInstanceState)
//        Firestore.instance("/products/celpyYcw7lpI60c3lSOd/reviews/JdpFvSNMcrTo3986Ps9o")
//            .get(){
//
//            }
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.textView).setOnClickListener{
            Log.d("MainActivity","Crash button Clicked")

            lifecycleScope.launch(Main) {
                CrashlyticsUtils.sendLogToCrashlytics(
                    "Crash button clicked",
                    "button",
                    "clicked",
                    "crash"
                )
                val msg= "Crash button clicked"
                CrashlyticsUtils.sendCustomLogToCrashlytics<AddToCartException>(
                    msg,
                    Pair(CrashlyticsUtils.ADD_TOCART_KEY,"ad to card button clicked"),
                )
                throw AddToCartException(msg)
            }
        }
    }

    private fun initSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen()
            // Add a callback that's called when the splash screen is animating to the
            // app content.
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                // Create your custom animation.
                val slideUp = ObjectAnimator.ofFloat(
                    splashScreenView, View.TRANSLATION_Y, 0f, -splashScreenView.height.toFloat()
                )
                slideUp.interpolator = AnticipateInterpolator()
                slideUp.duration = 1000L

                // Call SplashScreenView.remove at the end of your custom animation.
                slideUp.doOnEnd { splashScreenView.remove() }

                // Run your animation.
                slideUp.start()
            }
        } else {
            setTheme(R.style.Theme_ECommerce)
        }
    }

}