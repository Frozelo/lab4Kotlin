package com.example.lab41

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab41.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiLevel = Build.VERSION.SDK_INT
        binding.apiLevelText.text = "API Level $apiLevel"

        binding.showAnswerButton.setOnClickListener {
            val answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
            binding.answerTextView.setText(if (answerIsTrue) R.string.true_button else R.string.false_button)
        }
    }

    companion object {
        const val EXTRA_ANSWER_IS_TRUE = "com.example.geoquiz.answer_is_true"
    }
}
