package com.codebosses.comical.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.codebosses.comical.R
import com.codebosses.comical.ui.intro.IntroActivity
import com.codebosses.comical.utils.PrefUtils
import com.codebosses.comical.utils.extensions.startActivityWithFinish
import com.codebosses.comical.ui.main.MainActivity
import com.codebosses.comical.utils.LogUtil

class SplashActivity : AppCompatActivity() {

    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler = Handler()

        LogUtil.debug("notification",PrefUtils.deviceToken)

        handler?.postDelayed({ ->
            startActivityWithFinish(if(PrefUtils.firstRun){
                IntroActivity::class.java
            }
            else{
                MainActivity::class.java
            })
        },3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(handler != null){
            handler?.removeCallbacks(null)
        }
    }
}
