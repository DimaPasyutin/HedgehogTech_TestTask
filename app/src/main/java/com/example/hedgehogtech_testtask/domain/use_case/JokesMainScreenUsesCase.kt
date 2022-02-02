package com.example.hedgehogtech_testtask.domain.use_case

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.hedgehogtech_testtask.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class JokesMainScreenUsesCase() {

    private var oldSearchRequest: String = "0"

    @SuppressLint("CheckResult")
    fun searchCertainCountJoke(editText: EditText, listener: Listener) {
        Observable.create<String> { emitter ->
            editText.addTextChangedListener( object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if (!emitter.isDisposed && oldSearchRequest != s.toString() && s.toString() != "" && s.toString() != "0") {
                        emitter.onNext(s.toString())
                    }
                    oldSearchRequest = s.toString()
                }

            })
        }
            .debounce(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listener.onCountJokeGet(it)
            }
    }

    fun interface Listener {
        fun onCountJokeGet(count: String)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isConnectedToInet(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkInternetConnection(context: Context) {
        if (!isConnectedToInet(context)) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.resources.getString(R.string.switch_on_internet))
                .setPositiveButton(context.resources.getString(R.string.positive_button_ok)) { dialog, id ->
                    dialog.cancel()
                }
            builder.create().show()
        }
    }

}