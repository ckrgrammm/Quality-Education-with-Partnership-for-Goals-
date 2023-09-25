package com.example.kleine.adapters.recyclerview


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kleine.R
import com.example.kleine.model.PassedQuiz

class PassedQuizzesAdapter(private var quizzes: MutableList<PassedQuiz> = mutableListOf()) :
    RecyclerView.Adapter<PassedQuizzesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val materialNameTextView: TextView = itemView.findViewById(R.id.tv_material_name)
        val dateTextView: TextView = itemView.findViewById(R.id.tv_date)
        val setNameTextView: TextView = itemView.findViewById(R.id.tv_set_name)
        val scoreTextView: TextView = itemView.findViewById(R.id.tv_score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.passed_quiz_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quiz = quizzes[position]
        holder.materialNameTextView.text = quiz.materialName
        holder.dateTextView.text = quiz.date
        holder.setNameTextView.text = quiz.setName
        holder.scoreTextView.text = quiz.score
    }

    override fun getItemCount() = quizzes.size

    fun updateQuizzes(newQuizzes: List<PassedQuiz>) {
        quizzes.clear()
        quizzes.addAll(newQuizzes)
        notifyDataSetChanged()
    }
}


