package com.codebosses.comical.ui.registration.login

import androidx.lifecycle.ViewModel
import com.codebosses.comical.repository.repo.user.UserRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private fun login(email: String, password: String,deviceId: Int,deviceToken: String) = userRepository.login(email, password,deviceId,deviceToken)

    fun signInWithEmailAndPassword(email: String, password: String,deviceId: Int,deviceToken: String) =
            login(email, password,deviceId,deviceToken)

    private fun fbLogin(userName: String, deviceId: Int, deviceToken: String, fbId: String,gId: String, profileImageUrl: String,
                        profileImageThumbUrl: String) = userRepository.fbLogin(userName, deviceId, deviceToken, fbId,gId, profileImageUrl, profileImageThumbUrl)

    fun loginWithFacebook(userName: String, deviceId: Int, deviceToken: String, fbId: String,gId: String, profileImageUrl: String,
                          profileImageThumbUrl: String) = fbLogin(userName, deviceId, deviceToken, fbId,gId, profileImageUrl, profileImageThumbUrl)

}