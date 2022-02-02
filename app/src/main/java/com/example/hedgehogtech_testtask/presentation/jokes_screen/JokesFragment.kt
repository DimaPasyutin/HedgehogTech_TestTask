package com.example.hedgehogtech_testtask.presentation.jokes_screen

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hedgehogtech_testtask.databinding.FragmentJokesBinding
import com.example.hedgehogtech_testtask.presentation.adapters.JokesListAdapter
import com.example.hedgehogtech_testtask.presentation.factories.factory

class JokesFragment : Fragment() {

    private val jokesViewModel by viewModels<JokesViewModel> { factory() }

    private var binding: FragmentJokesBinding? = null

    private var adapter: JokesListAdapter? = null

    private val requireBinding: FragmentJokesBinding
        get() = binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewBinding =  FragmentJokesBinding.inflate(inflater, container, false)
        this.binding = viewBinding
        adapter = JokesListAdapter()
        return viewBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jokesViewModel.searchCertainCountJoke(requireBinding.searchBar)
        jokesViewModel.showAlertForCheckInternetConnection(requireContext())
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        requireBinding.recyclerViewJokes.layoutManager = layoutManager
        requireBinding.recyclerViewJokes.adapter = adapter
        jokesViewModel.uiStateChanges.observe(viewLifecycleOwner, { jokeUiState ->
            renderState(jokeUiState)
        })
    }

    private fun renderState(jokesUiState: JokeUiState) {
        when {
            jokesUiState.isLoading -> {
                requireBinding.progressBarIsLoading.visibility = View.VISIBLE
            }

            jokesUiState.jokes.isNotEmpty() && jokesUiState.error == null -> {
                adapter!!.submitList(jokesUiState.jokes)
                with(requireBinding) {
                    progressBarIsLoading.visibility = View.GONE
                }
            }

            jokesUiState.error != null -> {
                requireBinding.progressBarIsLoading.visibility = View.GONE
                Toast.makeText(context, "Ошибка при загрузве данных", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}