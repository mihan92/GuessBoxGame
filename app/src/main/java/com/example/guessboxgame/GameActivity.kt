package com.example.guessboxgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.guessboxgame.databinding.ActivityGameBinding
import com.example.guessboxgame.databinding.ItemBoxBinding
import kotlin.properties.Delegates
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var options: Options
    private var boxIndex by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        options = intent.getParcelableExtra(OptionsActivity.EXTRA_OPTIONS) ?:
            throw IllegalAccessException("Options not found")
        boxIndex = savedInstanceState?.getInt(KEY_INDEX) ?: Random.nextInt(options.boxCount)

        createBoxes()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_INDEX, options)
    }

    private fun createBoxes(){
        val boxBindings = (0 until options.boxCount).map { index ->
        val boxBinding = ItemBoxBinding.inflate(layoutInflater)
            boxBinding.root.id = View.generateViewId()
            boxBinding.textViewBox.text = "Box ${index + 1}"
            boxBinding.root.setOnClickListener {view -> onBoxSelected(view)}
            boxBinding.root.tag = index
            binding.root.addView(boxBinding.root)
            boxBinding
        }
        binding.flow.referencedIds = boxBindings.map { it.root.id }.toIntArray()
    }

    private fun onBoxSelected(view: View){
        if(view.tag as Int == boxIndex) {
            Toast.makeText(this, "Угадал", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this, "Не угадал", Toast.LENGTH_LONG).show()
        }
    }

    companion object{
        private const val KEY_INDEX = "key_index"
    }
}

