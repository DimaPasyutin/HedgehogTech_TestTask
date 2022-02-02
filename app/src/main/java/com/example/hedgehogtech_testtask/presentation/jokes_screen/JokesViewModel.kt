package com.example.hedgehogtech_testtask.presentation.jokes_screen

import android.content.Context
import android.os.Build
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hedgehogtech_testtask.App
import com.example.hedgehogtech_testtask.data.network.models.Joke
import com.example.hedgehogtech_testtask.domain.use_case.JokesMainScreenUsesCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class JokesViewModel(private val app: App): ViewModel() {

    private val uiState = MutableLiveData(JokeUiState(emptyList(), null, false))
    val uiStateChanges: LiveData<JokeUiState> = uiState

    private val currentUiState: JokeUiState
        get() = requireNotNull(uiState.value)

    private val asyncJokes = CompositeDisposable()

    private val jokesMainScreenUseCase = JokesMainScreenUsesCase()

    private fun fetchJoke(count: String) {updateState {
        copy(
            error = null,
            isLoading = true
        )
    }
        app.jokesRepository.loadJokes(count)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onSusses, this::onError)
            .addTo(asyncJokes)
    }

    private fun onSusses(jokes: List<Joke>) {
        updateState {
            copy(
                jokes = jokes,
                error = null,
                isLoading = false
            )
        }
    }

    private fun onError(throwable: Throwable) {
        updateState {
            copy(
                error = throwable,
                isLoading = false
            )
        }
    }

    private fun updateState(mutate: JokeUiState.() -> JokeUiState) {
        val newState = mutate(currentUiState)
        if (newState != currentUiState) {
            uiState.value = newState
        }
    }

    fun searchCertainCountJoke(editText: EditText) {
        jokesMainScreenUseCase.searchCertainCountJoke(editText) {
            fetchJoke(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun showAlertForCheckInternetConnection(context: Context) {
        jokesMainScreenUseCase.checkInternetConnection(context)
    }

    override fun onCleared() {
        asyncJokes.clear()
    }

    private fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
        compositeDisposable.add(this)
    }
}

data class JokeUiState(
    val jokes: List<Joke>,
    val error: Throwable?,
    val isLoading: Boolean
)