package com.codebosses.comical.ui.setting

import androidx.lifecycle.ViewModel
import com.codebosses.comical.repository.repo.setting.SettingRepository
import javax.inject.Inject

class ChangePasswordViewModel @Inject constructor(private val settingRepository: SettingRepository) : ViewModel() {

    fun updatePassword(email: String, oldPassword: String, newPassword: String) =
            settingRepository.updatePassword(email, oldPassword, newPassword)

}