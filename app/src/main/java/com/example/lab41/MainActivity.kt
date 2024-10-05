package com.example.lab41

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.lab41.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        R.string.question_australia,
        R.string.question_oceans,
        R.string.question_mideast,
        R.string.question_africa,
        R.string.question_americas,
        R.string.question_asia
    )


    private val answers = listOf(true, true, false, false, false, true)

    private var currentIndex = 0
    private var correctAnswers = 0
    private var answered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt("CURRENT_INDEX", 0)
            correctAnswers = savedInstanceState.getInt("CORRECT_ANSWERS", 0)
            answered = savedInstanceState.getBoolean("ANSWERED", false)
        }


        updateQuestion()


        binding.trueButton.setOnClickListener {
            if (!answered) {
                checkAnswer(true)
                answered = true
                hideButtons()
            }
        }


        binding.falseButton.setOnClickListener {
            if (!answered) {
                checkAnswer(false)
                answered = true
                hideButtons()
            }
        }


        binding.nextButton.setOnClickListener {
            if (currentIndex == questionBank.size - 1) {

                showResults()
            } else {

                currentIndex = (currentIndex + 1) % questionBank.size
                answered = false
                updateQuestion()
            }
        }


        binding.cheatButton.setOnClickListener {
            val answerIsTrue = answers[currentIndex]
            val intent = Intent(this, CheatActivity::class.java).apply {
                putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
            startActivity(intent)
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("CURRENT_INDEX", currentIndex)
        outState.putInt("CORRECT_ANSWERS", correctAnswers)
        outState.putBoolean("ANSWERED", answered)
    }


    private fun updateQuestion() {
        val questionResId = questionBank[currentIndex]
        binding.questionText.setText(questionResId)


        binding.trueButton.visibility = Button.VISIBLE
        binding.falseButton.visibility = Button.VISIBLE


        if (currentIndex == questionBank.size - 1) {
            binding.nextButton.text = "Finish"
        } else {
            binding.nextButton.text = "Next"
        }
    }


    private fun hideButtons() {
        binding.trueButton.visibility = Button.INVISIBLE
        binding.falseButton.visibility = Button.INVISIBLE
    }


    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = answers[currentIndex]
        if (userAnswer == correctAnswer) {
            correctAnswers++
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
        }
    }


    private fun showResults() {
        AlertDialog.Builder(this)
            .setTitle("Результат")
            .setMessage("Вы ответили правильно на $correctAnswers из ${questionBank.size} вопросов!")
            .setPositiveButton("OK") { _, _ -> }
            .show()


        resetQuiz()
    }

    // Сбрасываем викторину для нового прохождения
    private fun resetQuiz() {
        correctAnswers = 0
        currentIndex = 0
        answered = false
        updateQuestion()
    }
}
