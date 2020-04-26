package com.codebosses.comical.ui.setting

import androidx.lifecycle.ViewModel
import com.codebosses.comical.repository.repo.setting.SettingRepository
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(private val settingRepository: SettingRepository) : ViewModel(){

    fun updateProfile(userId: Int, userName: String, phoneNumber: String, profileStatus: String) =
            settingRepository.updateProfile(userId, userName, phoneNumber, profileStatus)

}