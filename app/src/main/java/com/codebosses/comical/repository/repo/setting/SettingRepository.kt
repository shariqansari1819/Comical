package com.codebosses.comical.repository.repo.setting

import androidx.lifecycle.LiveData
import com.codebosses.comical.repository.api.ApiServices
import com.codebosses.comical.repository.api.network.NetworkResource
import com.codebosses.comical.repository.api.network.Resource
import com.codebosses.comical.repository.model.user.User
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepository @Inject constructor(private val apiServices: ApiServices){

    fun updateNotification(userId: Int, notificationStatus: Int) : LiveData<Resource<ResponseBody>> {
        return object : NetworkResource<ResponseBody>() {
            override fun createCall(): LiveData<Resource<ResponseBody>> {
                return apiServices.updateNotification(userId,notificationStatus)
            }
        }.asLiveData()
    }

    fun updateProfile(userId: Int, userName: String, phoneNumber: String, status: String) : LiveData<Resource<User>> {
        return object : NetworkResource<User>() {
            override fun createCall(): LiveData<Resource<User>> {
                return apiServices.updateProfile(userId,userName,phoneNumber,status)
            }
        }.asLiveData()
    }

    fun updatePassword(userEmail: String, oldPassword: String, newPassword: String) : LiveData<Resource<ResponseBody>> {
        return object : NetworkResource<ResponseBody>() {
            override fun createCall(): LiveData<Resource<ResponseBody>> {
                return apiServices.updatePassword(userEmail,oldPassword,newPassword)
            }
        }.asLiveData()
    }

}