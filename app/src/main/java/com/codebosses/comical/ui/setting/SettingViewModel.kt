package com.codebosses.comical.ui.setting

import androidx.lifecycle.ViewModel
import com.codebosses.comical.repository.repo.setting.SettingRepository
import javax.inject.Inject

class SettingViewModel @Inject constructor(private val settingRepository: SettingRepository) : ViewModel() {

    fun updateNotification(userId: Int, notificationStatus: Int) = settingRepository.updateNotification(userId, notificationStatus)

}