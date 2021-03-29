package com.jvmartinez.marvelinfo.ui.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.jvmartinez.marvelinfo.R
import kotlinx.android.synthetic.main.custom_loading.*

abstract class BaseFragment : Fragment(), BaseContract  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSetup()
        onObserver()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSetup(view)
    }

    @LayoutRes
    abstract fun getLayoutId(): Int
    /**
     * This action is called in the Create view created
     * @param view
     */
    protected abstract fun onSetup(view: View)

    /**
     * This action is called in the Create view
     * @param
     */
    protected abstract fun onSetup()

    protected abstract fun onObserver()

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
}