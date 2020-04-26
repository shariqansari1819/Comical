package com.codebosses.comical.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.app_bar_edit_profile.view.*
import kotlinx.android.synthetic.main.app_bar_setting.view.*

class ChangePasswordActivity : BaseActivity(), View.OnClickListener {

    //    View model field....
    private val changePasswordViewModel by lazy {
        getViewModel<ChangePasswordViewModel>()
    }

    //    Android fields....
    private lateinit var toolbar: Toolbar
    private lateinit var sweetAlertDialog: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        //        Dialog initialization....
        sweetAlertDialog = SweetAlertDialog((this))
        sweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE)
        sweetAlertDialog.titleText = "Please wait..."
        sweetAlertDialog.setCancelable(false)

        //        Setting action bar....
        toolbar = appBarChangePassword as Toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            toolbar.textViewToolbarTitleMain.text = resources.getString(R.string.change_password)
        }

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
                val currentPassword = editTextCurrentPasswordHeaderPasswordUpdate.text.toString()
                val newPassword = editTextNewPasswordHeaderPasswordUpdate.text.toString()
                if (currentPassword.isNotEmpty() && newPassword.isNotEmpty()) {
                    if (currentPassword == PrefUtils.password) {
                        updatePassword(newPassword)
                    } else {
                        ToastUtil.showCustomToast(this@ChangePasswordActivity, "Your current password did not match.")
                    }
                }
            }
        }
    }

    fun updatePassword(newPassword: String) {
        changePasswordViewModel.updatePassword(PrefUtils.userEmail, PrefUtils.password, newPassword).observe(this) {
            when {
                it.status.isLoading() -> {
                    sweetAlertDialog.show()
                }
                it.status.isSuccessful() -> {
                    sweetAlertDialog.dismiss()
                    ToastUtil.showCustomToast(this,"Your password updated successfully.")
                }
                it.status.isError() -> {
                    sweetAlertDialog.dismiss()
                    ToastUtil.showCustomToast(this,it.errorMessage.toString())
                }
            }
        }
    }


}
