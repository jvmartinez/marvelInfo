package com.jvmartinez.marvelinfo.utils

import android.app.Application
import com.jvmartinez.marvelinfo.R
import java.math.BigInteger
import java.security.MessageDigest


object  MarvelInfoUtils {
    fun createHash(app : Application) : String{
        val md = MessageDigest.getInstance("MD5")
        val credential: String = 1.toString().plus(app.getString(R.string.private_key).plus(app.getString(R.string.public_key)))
        return BigInteger(1, md.digest(credential.toByteArray())).toString(16).padStart(32, '0')
    }

}