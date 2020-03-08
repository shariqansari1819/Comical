package com.codebosses.comical.ui.registration.login

import androidx.lifecycle.ViewModel
import com.codebosses.comical.repository.repo.user.UserRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private fun login(email: String, password: String) = userRepository.login(email, password)

    fun signInWithEmailAndPassword(email: String, password: String) =
            login(email, password)

    private fun fbLogin(userName: String, deviceId: Int, deviceToken: String, fbId: String, profileImageUrl: String,
                        profileImageThumbUrl: String) = userRepository.fbLogin(userName, deviceId, deviceToken, fbId, profileImageUrl, profileImageThumbUrl)

    fun loginWithFacebook(userName: String, deviceId: Int, deviceToken: String, fbId: String, profileImageUrl: String,
                          profileImageThumbUrl: String) = fbLogin(userName, deviceId, deviceToken, fbId, profileImageUrl, profileImageThumbUrl)

}