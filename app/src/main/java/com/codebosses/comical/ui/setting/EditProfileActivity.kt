package com.codebosses.comical.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.codebosses.comical.R
import com.codebosses.comical.ui.BaseActivity
import com.codebosses.comical.utils.PrefUtils
import com.codebosses.comical.utils.ToastUtil
import com.codebosses.comical.utils.extensions.getViewModel
import com.codebosses.comical.utils.extensions.observe
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.app_bar_edit_profile.view.*

class EditProfileActivity : BaseActivity(), View.OnClickListener {

    //    View model field....
    private val editProfileViewModel by lazy {
        getViewModel<EditProfileViewModel>()
    }

    //    Android fields....
    private lateinit var toolbar: Toolbar
    private lateinit var sweetAlertDialog: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        //        Dialog initialization....
        sweetAlertDialog = SweetAlertDialog((this))
        sweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE)
        sweetAlertDialog.titleText = "Please wait..."
        sweetAlertDialog.setCancelable(false)

        //        Setting action bar....
        toolbar = appBarEditProfile as Toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            toolbar.textViewToolbarTitleMain.text = resources.getString(R.string.edit_profile)
        }
        editTextNameEditProfile.setText(PrefUtils.userName)
        editTextPhoneEditProfile.setText(PrefUtils.phoneNumber)
        editTextStatusEditProfile.setText(PrefUtils.profileStatus)


//        All event listeners....
        toolbar.imageViewCloseAppBarSetting.setOnClickListener(this)
        toolbar.imageViewTickAppBarSetting.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imageViewCloseAppBarSetting -> {
                onBackPressed()
            }
            R.id.imageViewTickAppBarSetting -> {
                val userName = editTextNameEditProfile.text.toString()
                val phoneNumber = editTextPhoneEditProfile.text.toString()
                val status = editTextStatusEditProfile.text.toString()
                if (userName.isNotEmpty()) {
                    updateProfile(userName, phoneNumber, status)
                }
            }
        }
    }

    fun updateProfile(username: String, phoneNumber: String, status: String) {
        editProfileViewModel.updateProfile(PrefUtils.userId, username, phoneNumber, status).observe(this) {
            when {
                it.status.isLoading() -> {
                    sweetAlertDialog.show()
                }
                it.status.isSuccessful() -> {
                    sweetAlertDialog.dismiss()
                    PrefUtils.userName = username
                    PrefUtils.phoneNumber = phoneNumber
                    PrefUtils.profileStatus = status
                    ToastUtil.showCustomToast(this, "Profile updated successfully.")
                }
                it.status.isError() -> {
                    sweetAlertDialog.dismiss()
                    ToastUtil.showCustomToast(this, it.errorMessage!!)
                }
            }
        }
    }

}
