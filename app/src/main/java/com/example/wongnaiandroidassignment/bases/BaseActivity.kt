package com.example.wongnaiandroidassignment.bases

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.webkit.URLUtil
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.text.Html
import android.text.Spanned
import android.util.Log
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.wongnaiandroidassignment.R
import com.example.wongnaiandroidassignment.views.MessageDialog
import com.kaopiz.kprogresshud.KProgressHUD


abstract class BaseActivity : AppCompatActivity() {

    lateinit var mActivity: AppCompatActivity

    lateinit var onlyOneDialog: MessageDialog

    private val mYourBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "Logout") {
                finish()
            }
        }
    }

//    fun trackScreen(screenName: String) {
//        try {
//            val mTracker = (application as MainApp).getDefaultTracker()
//            mTracker.setScreenName(screenName)
//            mTracker.send(HitBuilders.ScreenViewBuilder().build())
//        } catch (e: Exception) {
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivity = this

        setContentView(getLayoutID())

        initBroadcastReceiver()
        onCreate()

    }

    abstract fun onCreate()
    abstract fun getLayoutID(): Int

    private fun initBroadcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction("Logout")

        LocalBroadcastManager.getInstance(mActivity).registerReceiver(
            mYourBroadcastReceiver,
            intentFilter
        )
    }

    override fun onDestroy() {
        try {
            LocalBroadcastManager.getInstance(mActivity)
                .unregisterReceiver(mYourBroadcastReceiver)
        } catch (e: Exception) {
        }

        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    var progressDialog: KProgressHUD? = null

    fun showProgressDialog() {
        try {
            dismissProgressDialog()

            progressDialog = KProgressHUD.create(mActivity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
            progressDialog!!.show()

        } catch (e: Exception) {
        }

    }

    fun dismissProgressDialog() {
        try {
            if (progressDialog != null) {
                progressDialog!!.dismiss()
                progressDialog = null
            }
        } catch (e: Exception) {
        }
    }

    fun showDialog(msg: String, callback: MessageDialog.CallBack) {
        try {
            var dialog = MessageDialog(mActivity, R.style.AppTheme)
            dialog.setCallBack(callback)
            dialog.show()

            dialog.setTextMsg(msg)
        } catch (e: Exception) {
        }
    }


    fun showDialog(msg: String, close: String, callback: MessageDialog.CallBack) {
        try {
            var dialog = MessageDialog(mActivity, R.style.AppTheme)
            dialog.setCallBack(callback)
            dialog.show()

            dialog.setTextMsg(msg)
            dialog.setTextBtnClose(close)
        } catch (e: Exception) {
        }
    }


    fun showDialog(msg: String, isClose: Boolean = false) {
        try {
            var dialog = MessageDialog(mActivity, R.style.AppTheme)
            dialog.setCallBack {
                if (isClose) {
                    finish()
                }
            }
            dialog.show()

            dialog.setTextMsg(msg)
        } catch (e: Exception) {
        }
    }

    fun getNavigationBarHeight(): Int {

        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0

    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun dpToPx(dp: Int, context: Context): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

    fun openExternalWeb(url: String) {

        if (URLUtil.isValidUrl(url)) {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        } else {
            showDialog("Invalid URL", false)
        }

    }

}