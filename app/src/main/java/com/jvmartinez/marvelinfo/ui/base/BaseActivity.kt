package com.jvmartinez.marvelinfo.ui.base

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.jvmartinez.marvelinfo.R
import kotlinx.android.synthetic.main.custom_loading.*


abstract class BaseActivity : AppCompatActivity(), BaseContract {

    @LayoutRes
    abstract fun layoutId(): Int

    protected abstract fun onSetup()

    protected abstract fun onObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        onSetup()
        onObserver()
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

    override fun showLoading() {
        if (loading != null) {
            loading.visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        if (loading != null) {
            loading.visibility = View.GONE
        }
    }

    override fun showBrowser(url: String) {
        try {
            val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(myIntent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }
}