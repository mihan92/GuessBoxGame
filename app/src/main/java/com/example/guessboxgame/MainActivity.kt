package com.example.guessboxgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.guessboxgame.OptionsActivity.Companion.EXTRA_OPTIONS
import com.example.guessboxgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var options: Options
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    options = result.data?.getParcelableExtra(EXTRA_OPTIONS) ?: Options.DEFAULT
                }
        }

        binding.buttonStart.setOnClickListener { onClickStart() }
        binding.buttonOptions.setOnClickListener { onClickOptions() }
        binding.buttonAbout.setOnClickListener { onClickAbout() }
        binding.buttonExit.setOnClickListener { onClickExit() }

        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?: Options.DEFAULT

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    private fun onClickStart(){
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(EXTRA_OPTIONS, options)
        startActivity(intent)
    }

    private fun onClickOptions(){
        val intent = Intent(this, OptionsActivity::class.java)
        launcher.launch(intent.putExtra(EXTRA_OPTIONS, options))
    }

    private fun onClickAbout(){
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun onClickExit(){
        finish()
    }

    companion object {
        const val KEY_OPTIONS = "OPTIONS"
    }
}