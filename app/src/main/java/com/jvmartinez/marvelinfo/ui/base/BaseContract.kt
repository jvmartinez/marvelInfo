package com.jvmartinez.marvelinfo.ui.base

import android.content.DialogInterface

interface BaseContract {
    /**
     * Notification dialog
     * @param title : alert title
     * @param message : alert message
     */
    fun showMessage(title: String, message: String)

    /**
     * Notification dialog
     * @param title : alert title
     * @param message : alert message
     * @param action: action dialog
     */
    fun showMessage(title: String, message: String, action: DialogInterface.OnClickListener)

    /**
     * Open browser
     * @param url: url of site to show
     */
     fun showBrowser(url: String)
}