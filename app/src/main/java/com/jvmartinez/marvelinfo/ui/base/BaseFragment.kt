package com.jvmartinez.marvelinfo.ui.base

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import com.jvmartinez.marvelinfo.R

abstract class BaseFragment : Fragment(), BaseContract {

    override fun onStart() {
        super.onStart()
        onSetup()
    }

    /**
     * This action is called in the Create view
     * @param
     */
    protected abstract fun onSetup()

    override fun showMessage(title: String, message: String) {
        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton(R.string.btn_ok) { view, _ ->
                view.dismiss()
            }
            .setCancelable(false)
            .create()
        dialog.show()
    }

    override fun showBrowser(url: String) {
        try {
            val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(myIntent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun showMessage(
        title: String,
        message: String,
        action: DialogInterface.OnClickListener
    ) {
        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.btn_ok, action)
            .setCancelable(false)
            .create()
        dialog.show()
    }
}