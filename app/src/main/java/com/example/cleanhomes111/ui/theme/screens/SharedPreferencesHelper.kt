package com.example.cleanhomes111.ui.theme.screens

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesHelper {
    private const val PREFS_NAME = "cleanhomes_prefs"
    private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun setOnboardingCompleted(context: Context, completed: Boolean) {
        val editor = getPreferences(context).edit()
        editor.putBoolean(KEY_ONBOARDING_COMPLETED, completed)
        editor.apply()
    }

    fun isOnboardingCompleted(context: Context): Boolean {
        return getPreferences(context).getBoolean(KEY_ONBOARDING_COMPLETED, false)
    }
}