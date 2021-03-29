package com.jvmartinez.marvelinfo.utils

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.jvmartinez.marvelinfo.R
import java.math.BigInteger
import java.security.MessageDigest


object  MarvelInfoUtils {
    /**
     * Method create key md5
     * @param app : Application for get resource
     */
    fun createHash(app : Application) : String{
        val md = MessageDigest.getInstance("MD5")
        val credential: String = 1.toString().plus(app.getString(R.string.private_key).plus(app.getString(R.string.public_key)))
        return BigInteger(1, md.digest(credential.toByteArray())).toString(16).padStart(32, '0')
    }

    /**
     * Method of call action activity
     * @param currentActivity : Activity current
     * @param destinationActivity : Acivity
     */
    fun callActivity(currentActivity: Activity, destinationActivity: Class<*>, extra: Bundle? = null) {
        val intent = Intent(currentActivity, destinationActivity)
        if (extra != null) {
            intent.putExtras(extra)
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        currentActivity.startActivity(intent)
    }
}