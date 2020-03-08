package com.codebosses.comical.ui.registration.signup

import android.os.Bundle
import android.view.View
import com.codebosses.comical.R
import com.codebosses.comical.repository.model.user.UserResult
import com.codebosses.comical.ui.BaseActivity
import com.codebosses.comical.ui.main.MainActivity
import com.codebosses.comical.ui.registration.login.LoginViewModel
import com.codebosses.comical.utils.PrefUtils
import com.codebosses.comical.utils.StringUtils
import com.codebosses.comical.utils.ToastUtil
import com.codebosses.comical.utils.extensions.getViewModel
import com.codebosses.comical.utils.extensions.observe
import com.codebosses.comical.utils.extensions.startActivityNewTask
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity(), View.OnClickListener {


    //    View model field....
    private val signUpViewModel by lazy {
        getViewModel<SignUpViewModel>()
    }

    //    Android fields....
    private lateinit var sweetAlertDialog: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //        Dialog initialization....
        sweetAlertDialog = SweetAlertDialog((this))
        sweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE)
        sweetAlertDialog.titleText = "Please wait..."
        sweetAlertDialog.setCancelable(false)

//        Listeners....
        textViewEnterWithLogin.setOnClickListener(this)
        buttonSignUp.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.textViewEnterWithLogin -> {
                finish()
            }
            R.id.buttonSignUp -> {
                val name = editTextNameSignUp.text.toString()
                val email = editTextEmailSignUp.text.toString()
                val password = editTextPasswordSignUp.text.toString()
                createUserWithEmailAndPassword(name, email, password)
            }
        }
    }

    private fun createUserWithEmailAndPassword(name: String, email: String, password: String) {
        if (validateData(name, email, password)) {
            signUpViewModel.createUserWithEmailAndPassword(name, email, password, "", 1, PrefUtils.deviceToken)
                    .observe(this) {
                        when {
                            it.status.isLoading() -> {
                                sweetAlertDialog.show()
                            }
                            it.status.isSuccessful() -> {
                                sweetAlertDialog.dismiss()
                                saveDataInPref(it.data!!.data)
                            }
                            it.status.isError() -> {
                                sweetAlertDialog.dismiss()
                                ToastUtil.showCustomToast(this, it.errorMessage!!)
                            }
                        }
                    }
        }
    }

    private fun validateData(name: String, email: String, password: String): Boolean {
        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            if (StringUtils.isValidEmail(email)) {
                if (password.length > 5) {
                    return true
                } else {
                    ToastUtil.showCustomToast(this, resources.getString(R.string.invalid_password))
                    return false
                }
            } else {
                ToastUtil.showCustomToast(this, resources.getString(R.string.invalid_email_address))
                return false
            }
        } else {
            ToastUtil.showCustomToast(this, resources.getString(R.string.please_fill_all_fields))
            return false
        }
    }

    private fun saveDataInPref(userResult: UserResult) {
        PrefUtils.isUserLoggedIn = true
        PrefUtils.userId = userResult.user_id
        PrefUtils.userName = userResult.user_name
        PrefUtils.userEmail = userResult.user_email
        PrefUtils.profileImageUrl = userResult.profile_image_url
        startActivityNewTask(MainActivity::class.java)
    }
}
