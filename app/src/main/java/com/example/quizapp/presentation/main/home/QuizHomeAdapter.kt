package com.example.quizapp.presentation.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.databinding.ItemQuizBinding
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates

class QuizHomeAdapter(
    private val quizSelected: (title: String) -> Unit,
) : RecyclerView.Adapter<QuizHomeAdapter.QuizVH>() {

    internal var quizList: List<QuizView> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        QuizVH(ItemQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: QuizVH, position: Int) {
        holder.bind(quizList[position])
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    inner class QuizVH(private val binding: ItemQuizBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quizView: QuizView) = with(binding) {
            quizTitle.text = quizView.title

            Picasso.get()
                .load(quizView.photo)
                .fit()
                .into(quizPhoto)

            root.setOnClickListener { quizSelected(quizView.title) }
        }
    }
}
