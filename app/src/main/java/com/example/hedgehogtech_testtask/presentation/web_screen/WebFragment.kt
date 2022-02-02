package com.example.hedgehogtech_testtask.presentation.web_screen

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.hedgehogtech_testtask.databinding.FragmentWebBinding
import com.example.hedgehogtech_testtask.domain.use_case.JokesMainScreenUsesCase

interface IOnBackPressed {
    fun onBackPressed(): Boolean
}

class WebFragment: Fragment(), IOnBackPressed {

    private var _binding: FragmentWebBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebBinding.inflate(inflater, container, false)
        binding.webViewJokesApi.webViewClient = MyWebViewClient()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            binding.webViewJokesApi.apply {
                restoreState(savedInstanceState)
                settings.javaScriptEnabled = true
                settings.builtInZoomControls = true
            }

        } else {
            webViewSetup()
        }
        val jokesMainScreenUseCase = JokesMainScreenUsesCase()
        jokesMainScreenUseCase.checkInternetConnection(requireContext())
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup() {
        binding.webViewJokesApi.apply {
            loadUrl("https://www.icndb.com/api/")
            settings.javaScriptEnabled = true
            settings.builtInZoomControls = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.webViewJokesApi.saveState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBackPressed(): Boolean {
        return if (binding.webViewJokesApi.canGoBack()) {
            binding.webViewJokesApi.goBack()
            false
        } else {
            true
        }
    }
}

class MyWebViewClient : WebViewClient() {
    @TargetApi(Build.VERSION_CODES.N)
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        view.loadUrl(request.url.toString())
        return true
    }

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        view.loadUrl(url)
        return true
    }
}