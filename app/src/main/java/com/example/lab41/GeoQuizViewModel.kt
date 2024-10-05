package com.example.lab41

import androidx.lifecycle.ViewModel

class GeoQuizViewModel : ViewModel() {

    private val questionBank = listOf(
        R.string.question_australia,
        R.string.question_oceans,
        R.string.question_mideast,
        R.string.question_africa,
        R.string.question_americas,
        R.string.question_asia
    )

    private val answers = listOf(true, true, false, false, false, true)

    var currentIndex = 0
    var correctAnswers = 0
    var cheatCount = 0 // Лимит на 3 подсказки

    fun getCurrentQuestion(): Int {
        return questionBank[currentIndex]
    }

    fun isCurrentAnswerTrue(): Boolean {
        return answers[currentIndex]
    }

    fun isAnswerCorrect(userAnswer: Boolean): Boolean {
        return answers[currentIndex] == userAnswer
    }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
}
