package com.example.lab41

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab41.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {

    // инициализируем биндниг для работы CheatActivity
    private lateinit var binding: ActivityCheatBinding

    // метод который вызывается при открытии activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // выводим activity
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // задаём поведенеи кнопки по выводу подсказкки
        binding.showAnswerButton.setOnClickListener {
            // распаковывем в переменную
            val answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
            // показываем в тексте - answerdIsTrue = True, пишем в тексте True, иначе False
            binding.answerTextView.setText(if (answerIsTrue) R.string.true_button else R.string.false_button)
        }
    }


    // Сопудствующий объект - объект, который необходим от activity
    companion object {
        const val EXTRA_ANSWER_IS_TRUE = "com.example.geoquiz.answer_is_true"
    }
}
