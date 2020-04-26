package com.codebosses.comical.ui.setting

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.codebosses.comical.R
import com.codebosses.comical.ui.BaseActivity
import com.codebosses.comical.ui.detail.ChapterReadViewModel
import com.codebosses.comical.ui.detail.ImagesPagerAdapter
import com.codebosses.comical.ui.main.MainActivity
import com.codebosses.comical.utils.PrefUtils
import com.codebosses.comical.utils.ToastUtil
import com.codebosses.comical.utils.extensions.*
import com.codebosses.comical.utils.intentOpenWebsite
import com.codebosses.comical.utils.intentShareText
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer
import kotlinx.android.synthetic.main.activity_chapter_read.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.app_bar_main.view.*
import kotlinx.android.synthetic.main.app_bar_edit_profile.view.*

class SettingActivity : BaseActivity(), View.OnClickListener {

    //    View model field....
    private val settingViewModel by lazy {
        getViewModel<SettingViewModel>()
    }

    //    Android fields....
    private lateinit var toolbar: Toolbar

    //    TODO: Instance fields....
    private lateinit var mGoogleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

//        Setting action bar....
        toolbar = appBarSetting as Toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        switchButtonNotificationSetting.isChecked = PrefUtils.isNotificationStatus
        if(!PrefUtils.isFacebookLogIn || !PrefUtils.isGoogleLogIn){
            relativeLayoutChangePasswordSetting.gone()
        }else{
            relativeLayoutChangePasswordSetting.visible()
        }

//        All click events....
        relativeLayoutChangePasswordSetting.setOnClickListener(this)
        relativeLayoutEditProfileSetting.setOnClickListener(this)
        textViewLogOutSetting.setOnClickListener(this)
        relativeLayoutPrivacyPolicySetting.setOnClickListener(this)
        relativeLayoutShareAppSetting.setOnClickListener(this)
        switchButtonNotificationSetting.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                updateNotificationStatus(1)
            } else {
                updateNotificationStatus(0)
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.relativeLayoutChangePasswordSetting -> {
                startActivity(ChangePasswordActivity::class.java)
            }
            R.id.relativeLayoutEditProfileSetting -> {
                startActivity(EditProfileActivity::class.java)
            }
            R.id.textViewLogOutSetting -> {
                logOutUser()
            }
            R.id.relativeLayoutPrivacyPolicySetting -> {
                intentOpenWebsite(this, "https://codebosses.blogspot.com/p/privacy-policy.html")
            }
            R.id.relativeLayoutShareAppSetting -> {
                intentShareText(this, "https://play.google.com/store/apps/details?id=com.codebosses.comical&hl=en")
            }
        }
    }

    fun logOutUser() {
        PrefUtils.isUserLoggedIn = false
        PrefUtils.userId = 0
        PrefUtils.userName = ""
        PrefUtils.userEmail = ""
        PrefUtils.profileImageUrl = ""
        PrefUtils.accountType = ""
        PrefUtils.password = ""
        PrefUtils.profileStatus = ""
        PrefUtils.isNotificationStatus = true
        PrefUtils.phoneNumber = ""
        if (PrefUtils.isFacebookLogIn) {
            PrefUtils.isFacebookLogIn = false
            LoginManager.getInstance().logOut()
            startActivityNewTask(MainActivity::class.java)
        } else if (PrefUtils.isGoogleLogIn) {
            PrefUtils.isGoogleLogIn = false
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {
                startActivityNewTask(MainActivity::class.java)
            }
        } else {
            startActivityNewTask(MainActivity::class.java)
        }
    }

    override fun onStart() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
        mGoogleApiClient.connect()
        super.onStart()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateNotificationStatus(notificationStatus: Int) {
        settingViewModel.updateNotification(PrefUtils.userId, notificationStatus).observe(this) {
            when {
                it.status.isLoading() -> {

                }
                it.status.isSuccessful() -> {
                    PrefUtils.isNotificationStatus = notificationStatus == 1
                }
                it.status.isError() -> {

                }
            }
        }
    }

}

