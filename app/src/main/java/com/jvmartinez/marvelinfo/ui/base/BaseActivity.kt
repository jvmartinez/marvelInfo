package com.jvmartinez.marvelinfo.ui.base

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.jvmartinez.marvelinfo.R


abstract class BaseActivity : AppCompatActivity(), BaseContract {
    abstract fun layoutId(): ViewBinding

    protected abstract fun onSetup()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId().root)
        onSetup()
    }

    override fun showMessage(title: String, message: String) {
        val dialog = AlertDialog.Builder(this)
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

    override fun showMessage(title: String, message: String, action: DialogInterface.OnClickListener) {
        val dialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.btn_ok, action)
            .setCancelable(false)
            .create()
        dialog.show()
    }
}