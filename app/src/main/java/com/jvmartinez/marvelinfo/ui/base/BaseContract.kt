package com.jvmartinez.marvelinfo.ui.base

interface BaseContract {
    /**
     * Notification dialog
     * @param title : alert title
     * @param message : alert message
     */
    fun showMessage(title: String, message: String)

    /**
     * Loading process for action
     * @param  void : show progressBar
     */
    fun showLoading()

    /**
     * Hide the loading action
     * @param  void : hide progressBar
     */
    fun hideLoading()

    /**
     * Open browser
     * @param url: url of site to show
     */
     fun showBrowser(url: String)
}