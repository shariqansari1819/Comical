package com.codebosses.comical.utils

import android.content.Context
import android.content.SharedPreferences
import com.codebosses.comical.common.Constants

object PrefUtils {

    private const val NAME = "comical"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // list of app specific preferences
    private val IS_FIRST_RUN_PREF = Pair(Constants.PreferenceConstants.IS_FIRST_RUN, true)
    private val IS_LOGGED_IN_PREF = Pair(Constants.PreferenceConstants.IS_LOGGED_IN, false)
    private val USER_ID = Pair(Constants.PreferenceConstants.USER_ID, 0)
    private val USER_NAME = Pair(Constants.PreferenceConstants.USER_NAME, "")
    private val USER_EMAIL = Pair(Constants.PreferenceConstants.USER_EMAIL, "")
    private val DEVICE_TOKEN = Pair(Constants.PreferenceConstants.DEVICE_TOKEN, "")
    private val PROFILE_IMAGE_URL = Pair(Constants.PreferenceConstants.PROFILE_IMAGE_URL, "")
    private val IS_FACEBOOK_LOG_IN = Pair(Constants.PreferenceConstants.IS_FACEBOOK_LOG_IN, false)
    private val IS_GOOGLE_LOG_IN = Pair(Constants.PreferenceConstants.IS_GOOGLE_LOG_IN, false)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var firstRun: Boolean
        get() = preferences.getBoolean(IS_FIRST_RUN_PREF.first, IS_FIRST_RUN_PREF.second)
        set(value) = preferences.edit {
            it.putBoolean(IS_FIRST_RUN_PREF.first, value)
        }

    var isUserLoggedIn: Boolean
        get() = preferences.getBoolean(IS_LOGGED_IN_PREF.first, IS_LOGGED_IN_PREF.second)
        set(value) = preferences.edit {
            it.putBoolean(IS_LOGGED_IN_PREF.first, value)
        }

    var userId: Int
        get() = preferences.getInt(USER_ID.first, USER_ID.second)
        set(value) = preferences.edit {
            it.putInt(USER_ID.first, value)
        }

    var userName: String
        get() = preferences.getString(USER_NAME.first, USER_NAME.second)!!
        set(value) = preferences.edit {
            it.putString(USER_NAME.first, value)
        }

    var userEmail: String
        get() = preferences.getString(USER_EMAIL.first, USER_EMAIL.second)!!
        set(value) = preferences.edit {
            it.putString(USER_EMAIL.first, value)
        }

    var deviceToken: String
        get() = preferences.getString(DEVICE_TOKEN.first, DEVICE_TOKEN.second)!!
        set(value) = preferences.edit {
            it.putString(DEVICE_TOKEN.first, value)
        }

    var profileImageUrl: String
        get() = preferences.getString(PROFILE_IMAGE_URL.first, PROFILE_IMAGE_URL.second)!!
        set(value) = preferences.edit {
            it.putString(PROFILE_IMAGE_URL.first, value)
        }

    var isFacebookLogIn: Boolean
        get() = preferences.getBoolean(IS_FACEBOOK_LOG_IN.first, IS_FACEBOOK_LOG_IN.second)
        set(value) = preferences.edit {
            it.putBoolean(IS_FACEBOOK_LOG_IN.first, value)
        }

    var isGoogleLogIn: Boolean
        get() = preferences.getBoolean(IS_GOOGLE_LOG_IN.first, IS_GOOGLE_LOG_IN.second)
        set(value) = preferences.edit {
            it.putBoolean(IS_GOOGLE_LOG_IN.first, value)
        }


}