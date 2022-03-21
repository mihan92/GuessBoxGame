package com.example.guessboxgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.guessboxgame.MainActivity.Companion.KEY_OPTIONS
import com.example.guessboxgame.databinding.ActivityOptionsBinding

class OptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOptionsBinding
    private lateinit var options: Options
    lateinit var boxCountItems: List<BoxCountItem>
    private lateinit var adapter: ArrayAdapter<BoxCountItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?:
                intent.getParcelableExtra(EXTRA_OPTIONS) ?:
                throw IllegalAccessException("GET EXTRA_OPTIONS")

        binding.buttonCancel.setOnClickListener { onClickCancel() }
        binding.buttonConfirm.setOnClickListener { onClickConfirm() }

        setupSpinner()
        updateUi()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    private fun setupSpinner(){
        boxCountItems = (2..9).map { BoxCountItem(it, "$it") }
        adapter = ArrayAdapter(this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, boxCountItems)
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)

        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val count = boxCountItems[p2].id
                options = options.copy(boxCount = count)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun updateUi(){
        val currentIndex = boxCountItems.indexOfFirst { it.id == options.boxCount }
        binding.spinner.setSelection(currentIndex)
    }

    private fun onClickCancel(){
        finish()
    }
    private fun onClickConfirm(){
        val intent = Intent()
        intent.putExtra(EXTRA_OPTIONS, options)
        setResult(RESULT_OK, intent)
        finish()
    }

    companion object {
        const val EXTRA_OPTIONS = "key"
    }

    class BoxCountItem(
        val id: Int,
        private val optionTitle: String
    ) {
        override fun toString(): String {
            return optionTitle
        }
    }
}