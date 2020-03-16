package com.codebosses.comical.repository.repo.user

import androidx.lifecycle.LiveData
import com.codebosses.comical.repository.api.ApiServices
import com.codebosses.comical.repository.api.network.NetworkResource
import com.codebosses.comical.repository.api.network.Resource
import com.codebosses.comical.repository.model.user.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
        private val apiServices: ApiServices
) {

    fun login(email: String, password: String): LiveData<Resource<User>> {
        return object : NetworkResource<User>() {
            override fun createCall(): LiveData<Resource<User>> {
                return apiServices.login(email, password)
            }
        }.asLiveData()
    }

    fun fbLogin(userName: String, deviceId: Int, deviceToken: String, fbId: String,gId: String, profileImageUrl: String,
                profileImageThumbUrl: String): LiveData<Resource<User>> {
        return object : NetworkResource<User>() {
            override fun createCall(): LiveData<Resource<User>> {
                return apiServices.fbLogin(userName,deviceId,deviceToken,fbId,gId,profileImageUrl,profileImageThumbUrl)
            }
        }.asLiveData()
    }

    fun register(name: String, email: String, password: String, phoneNumber: String,deviceId: Int, deviceToken: String): LiveData<Resource<User>> {
        return object : NetworkResource<User>() {
            override fun createCall(): LiveData<Resource<User>> {
                return apiServices.register(email, phoneNumber, name, password, deviceId, deviceToken)
            }
        }.asLiveData()
    }

}