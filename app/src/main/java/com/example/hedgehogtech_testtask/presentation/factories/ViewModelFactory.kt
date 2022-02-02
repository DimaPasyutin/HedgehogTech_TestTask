package com.example.hedgehogtech_testtask.presentation.factories

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hedgehogtech_testtask.App
import com.example.hedgehogtech_testtask.presentation.jokes_screen.JokesViewModel

class ViewModelFactory(
private val app: App
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            JokesViewModel::class.java -> {
                JokesViewModel(app)
            }
            else -> {
                throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }

}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)