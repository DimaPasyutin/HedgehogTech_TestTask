package com.example.hedgehogtech_testtask.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hedgehogtech_testtask.R
import com.example.hedgehogtech_testtask.data.network.models.Joke
import com.example.hedgehogtech_testtask.databinding.ItemJokeBinding

class JokesListAdapter: ListAdapter<Joke, JokeViewHolder>(JokeDiffer()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemJokeBinding.bind(layoutInflater.inflate(R.layout.item_joke, parent, false))
        return JokeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        val joke = currentList[position]
        holder.bindSong(joke)
    }
}

class JokeViewHolder(
    private val bindingJoke: ItemJokeBinding
) : RecyclerView.ViewHolder(bindingJoke.root) {

    fun bindSong(joke: Joke) {
        with(bindingJoke) {
            textViewJoke.text = joke.joke_text
        }
    }
}

class JokeDiffer : DiffUtil.ItemCallback<Joke>() {
    override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem == newItem
    }
}