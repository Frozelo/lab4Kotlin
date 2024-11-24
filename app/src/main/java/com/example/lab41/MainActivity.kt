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

    // инициализируем биндниг для работы MainActivity
    private lateinit var binding: ActivityMainBinding

    // Инициализируем необходимые вопрсы из res/strings
    private val questionBank = listOf(
        R.string.question_australia,
        R.string.question_oceans,
        R.string.question_mideast,
        R.string.question_africa,
        R.string.question_americas,
        R.string.question_asia
    )

    // инициализируем список из правильный ответов
    private val answers = listOf(true, true, false, false, false, true)

    // инициализируем счетчики (текущий вопрос, правильные ответы и флаг ответили/не ответили)
    private var currentIndex = 0
    private var correctAnswers = 0
    private var answered = false


    // метод который вызывается при открытии activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // вызываем наше activity
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // сохраняем состояние при поворете устройсва
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt("CURRENT_INDEX", 0)
            correctAnswers = savedInstanceState.getInt("CORRECT_ANSWERS", 0)
            answered = savedInstanceState.getBoolean("ANSWERED", false)
        }


        //точка входа в наш quiz - отображаем вопрос с индексом 1
        updateQuestion()


        // задаём поведение кнопки true
        binding.trueButton.setOnClickListener {
            if (!answered) {
                checkAnswer(true)
                answered = true
                // скрываем кнпоку
                hideButtons()
            }
        }

    // задаём поведение кнопки false
        binding.falseButton.setOnClickListener {
            if (!answered) {
                checkAnswer(false)
                answered = true
                // скрываем кнпоку
                hideButtons()
            }
        }

        // задаём поведение кнопки next
        binding.nextButton.setOnClickListener {
            // функциональная проверка на завершение воросов
            //  проверка, что мы прошли все вопросы
            if (currentIndex == questionBank.size - 1) {

                // показывает результат
                showResults()
            } else {

                // прибавляем к текущему индексу
                currentIndex = (currentIndex + 1)
                answered = false

                // обновляем строку
                updateQuestion()
            }
        }


        // задаём поведение кнопки cheat
        binding.cheatButton.setOnClickListener {
            // достаём правильный ответ на текущий вопрос и сохраняем в переменную
            val answerIsTrue = answers[currentIndex]

            // intent - механизм для создания нового активити окна - создаём и передаём необходимые параметры
            // (answerdIsTrue)
            val intent = Intent(this, CheatActivity::class.java).apply {
                putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }

            // запускаем activity с переданными объетктом класса Intent
            startActivity(intent)
        }
    }


    // Функция для сохранения состояния
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("CURRENT_INDEX", currentIndex)
        outState.putInt("CORRECT_ANSWERS", correctAnswers)
        outState.putBoolean("ANSWERED", answered)
    }


    // берет из списка вопросов очередной вопрос и отображает его компоненты, храня на него ответ
    private fun updateQuestion() {
        // берет индекс и ставит текст вопроса
        val questionResId = questionBank[currentIndex]
        binding.questionText.setText(questionResId)


        // ставит кнопки true/false видимыми
        binding.trueButton.visibility = Button.VISIBLE
        binding.falseButton.visibility = Button.VISIBLE


        // проверка того, что мы прошли все вопросы - меняем текст в кнопке
        if (currentIndex == questionBank.size - 1) {
            binding.nextButton.text = "Finish"
        } else {
            binding.nextButton.text = "Next"
        }
    }


    // функция, которая ставит кнопки в положение "invisible"
    private fun hideButtons() {
        binding.trueButton.visibility = Button.INVISIBLE
        binding.falseButton.visibility = Button.INVISIBLE
    }


    // функция, которая проверяет ответ
    private fun checkAnswer(userAnswer: Boolean) {
        // достаёт ответ из списка ответов по индексу
        val correctAnswer = answers[currentIndex]
        // сравнивает ответ пользователя с ответом в списке ответов
        if (userAnswer == correctAnswer) {

            // если всё ок, прибавляем к счётчику
            correctAnswers++
            // используем Toast, отображаем, что мы ответили правильно
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show()
        } else {
            // отображаем, что мы ответили неправильно
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
        }
    }


    // используем компонент AlertDialog для отображения ответа в виде системного окошка
    private fun showResults() {
        AlertDialog.Builder(this)
            // форматируем строки под ответ
            .setTitle("Результат")
            .setMessage("Вы ответили правильно на $correctAnswers из ${questionBank.size} вопросов!")
            .setPositiveButton("OK") { _, _ -> }
            .show()


        // сбрасываем состояние для тестирования
        resetQuiz()
    }


    // всё зануляем
    private fun resetQuiz() {
        correctAnswers = 0
        currentIndex = 0
        answered = false

        // начинаем тест заново
        updateQuestion()
    }
}
