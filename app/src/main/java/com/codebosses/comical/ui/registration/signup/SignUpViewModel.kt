package com.codebosses.comical.ui.registration.signup

import androidx.lifecycle.ViewModel
import com.codebosses.comical.repository.repo.user.UserRepository
import javax.inject.Inject

class SignUpViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private fun register(name: String, email: String, password: String, phoneNumber: String, deviceId: Int, deviceToken: String) =
            userRepository.register(name, email, password, phoneNumber, deviceId, deviceToken)

    fun createUserWithEmailAndPassword(name: String, email: String, password: String, phoneNumber: String, deviceId: Int, deviceToken: String) =
            register(name, email, password, phoneNumber, deviceId, deviceToken)

}