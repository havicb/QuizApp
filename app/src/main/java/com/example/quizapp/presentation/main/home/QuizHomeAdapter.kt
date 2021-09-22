package com.example.quizapp.presentation.main.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.databinding.ItemQuizBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class QuizHomeAdapter @Inject constructor(
    private val quizzes: Quizzes,
) : RecyclerView.Adapter<QuizHomeAdapter.QuizVH>() {

    internal lateinit var quizSelected: (title: String) -> Unit

    fun setOnClickListener(listener: (String) -> Unit) {
        quizSelected = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateQuizzes(data: List<QuizView>) {
        quizzes.updateQuizzes(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        QuizVH(ItemQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: QuizVH, position: Int) {
        holder.bind(quizzes.getSingleQuizById(position))
    }

    override fun getItemCount(): Int {
        return quizzes.getSize()
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
