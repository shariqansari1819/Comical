package com.codebosses.comical.ui.registration.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.codebosses.comical.R
import com.codebosses.comical.repository.model.user.UserResult
import com.codebosses.comical.ui.BaseActivity
import com.codebosses.comical.ui.main.MainActivity
import com.codebosses.comical.ui.registration.signup.SignUpActivity
import com.codebosses.comical.utils.ConnectivityUtil
import com.codebosses.comical.utils.PrefUtils
import com.codebosses.comical.utils.StringUtils
import com.codebosses.comical.utils.ToastUtil
import com.codebosses.comical.utils.extensions.getViewModel
import com.codebosses.comical.utils.extensions.observe
import com.codebosses.comical.utils.extensions.startActivity
import com.codebosses.comical.utils.extensions.startActivityNewTask
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginActivity : BaseActivity(), View.OnClickListener {

    //    View model field....
    private val loginViewModel by lazy {
        getViewModel<LoginViewModel>()
    }

    //    Android fields....
    private lateinit var sweetAlertDialog: SweetAlertDialog

    //    TODO: Facebook sign in fields....
    private lateinit var facebookManager: CallbackManager

    //    Google sign in fields....
    private lateinit var googleSigninClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        Dialog initialization....
        sweetAlertDialog = SweetAlertDialog((this))
        sweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE)
        sweetAlertDialog.titleText = "Please wait..."
        sweetAlertDialog.setCancelable(false)

        facebookManager = CallbackManager.Factory.create()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        googleSigninClient = GoogleSignIn.getClient(this, gso)

//        Listeners....
        buttonLogin.setOnClickListener(this)
        buttonFacebook.setOnClickListener(this)
        buttonGoogle.setOnClickListener(this)
        textViewDontHaveAccount.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.buttonLogin -> {
                val email = editTextEmailLogin.text.toString()
                val password = editTextPasswordLogin.text.toString()
                signInWithEmailAndPassword(email, password)
            }
            R.id.buttonFacebook -> {
                registerFacebookCallback()
            }
            R.id.buttonGoogle -> {
                signIn()
            }
            R.id.textViewDontHaveAccount -> {
                startActivity(SignUpActivity::class.java)
            }
        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        if (validateData(email, password)) {
            loginViewModel.signInWithEmailAndPassword(email, password)
                    .observe(this) {
                        when {
                            it.status.isLoading() -> {
                                sweetAlertDialog.show()
                            }
                            it.status.isSuccessful() -> {
                                sweetAlertDialog.dismiss()
                                val user = it.data
                                if (user!!.status) {
                                    saveDataInPref(it.data?.data!!,"email")
                                } else {
                                    ToastUtil.showCustomToast(this, user.message)
                                }
                            }
                            it.status.isError() -> {
                                sweetAlertDialog.dismiss()
                                ToastUtil.showCustomToast(this, it.errorMessage!!)
                            }
                        }
                    }
        }
    }

    private fun validateData(email: String, password: String): Boolean {
        if (ConnectivityUtil.isConnected(this)) {
            if (email.isNotEmpty() && password.isNotEmpty()) {
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
        } else {
            ToastUtil.showCustomToast(this, "There is some problem with your connection.")
            return false
        }
    }

    private fun registerFacebookCallback() {
        val loginManager = LoginManager.getInstance()
        val permissionList = ArrayList<String>()
        permissionList.add("email")
        permissionList.add("public_profile")
        loginManager.logInWithReadPermissions(this, permissionList)
        loginManager.registerCallback(facebookManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                val request = GraphRequest.newMeRequest(result?.accessToken) { data, response ->
                    try {
                        val email = data.getString("email")
                        val id = data.getString("id")
                        val firstName = data.getString("first_name")
                        val lastName = data.getString("last_name")
                        val profileImageUrl = data.getJSONObject("picture").getJSONObject("data").getString("url")
                        loginWithFacebook("$firstName $lastName", id, profileImageUrl,"facebook")
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }
                val parameters = Bundle()
                parameters.putString("fields", "id, first_name,last_name, email, gender,birthday,picture.type(large)")
                request.parameters = parameters
                request.executeAsync()
            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {
                ToastUtil.showCustomToast(this@LoginActivity, error?.message!!)
            }

        })
    }

    private fun loginWithFacebook(name: String, id: String, profileImage: String,loginType: String) {
        loginViewModel.loginWithFacebook(name, 1, PrefUtils.deviceToken, id, profileImage, profileImage)
                .observe(this) {
                    when {
                        it.status.isLoading() -> {
                            sweetAlertDialog.show()
                        }
                        it.status.isSuccessful() -> {
                            sweetAlertDialog.dismiss()
                            saveDataInPref(it.data!!.data,loginType)
                        }
                        it.status.isError() -> {
                            sweetAlertDialog.dismiss()
                            ToastUtil.showCustomToast(this, it.errorMessage!!)
                        }
                    }
                }
    }

    private fun signIn() {
        val intent = googleSigninClient.signInIntent
        startActivityForResult(intent, 5)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 5) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        } else {
            facebookManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) = try {
        val account = completedTask.getResult(ApiException::class.java)
        signInWithGoogle(account)
    } catch (e: Exception) {

    }

    private fun signInWithGoogle(account: GoogleSignInAccount?) {
        loginWithFacebook(account!!.displayName!!, account.id!!, account.photoUrl.toString(),"google")
    }

    private fun saveDataInPref(userResult: UserResult, loginType: String) {
        PrefUtils.isUserLoggedIn = true
        PrefUtils.userId = userResult.user_id
        PrefUtils.userName = userResult.user_name
        PrefUtils.userEmail = userResult.user_email
        PrefUtils.profileImageUrl = userResult.profile_image_url
        when (loginType) {
            "facebook" -> {
                PrefUtils.isFacebookLogIn = true
            }
            "google" -> {
                PrefUtils.isGoogleLogIn = true
            }
        }
        startActivityNewTask(MainActivity::class.java)
    }
}
