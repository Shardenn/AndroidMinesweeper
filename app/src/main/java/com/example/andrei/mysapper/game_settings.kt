package com.example.andrei.mysapper

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup

class game_settings : AppCompatActivity(),
                        SettingsView.settingsViewListener
{

    var curr_settings= Settings(Size(-1,-1), -1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_settings)
/*
        val button_back = findViewById<Button>(R.id.button_back)
        button_back.setOnClickListener { onUndoTap() }

        val button_apply = findViewById<Button>(R.id.button_apply_restart)
        button_apply.setOnClickListener { onApplyTap() }

        val radio_group = findViewById<RadioGroup>(R.id.radio_group)
        radio_group.setOnClickListener { onRadioChanged() }*/
    }

    fun returnToMain(pass_new_settings: Boolean) {
        val intent = Intent(this, GameBoardActivity::class.java)
        if(pass_new_settings) {
            intent.putExtra("width", curr_settings.field_size.width)
            intent.putExtra("height", curr_settings.field_size.height)
            intent.putExtra("bombs", curr_settings.bombs_count)
        }
        startActivity(intent)
    }

    fun getSelectedSettings(): Settings {
        val radio_group = findViewById<RadioGroup>(R.id.radio_group)
        if(radio_group.checkedRadioButtonId in 0..2){

        }
        else {
            var text = findViewById<EditText>(R.id.editText_width).text
            curr_settings.field_size.width = text.toString().toInt()

            text = findViewById<EditText>(R.id.editText_height).text
            curr_settings.field_size.height = text.toString().toInt()

            text = findViewById<EditText>(R.id.editText_bombs).text
            curr_settings.bombs_count = text.toString().toInt()
        }
        return curr_settings
    }

    override fun onApplyTap() {
        returnToMain(true)
    }

    override fun onUndoTap() {
        returnToMain(false)
    }

    fun onRadioChanged() {
        val radio_group = findViewById<RadioGroup>(R.id.radio_group)
        when(radio_group.checkedRadioButtonId) {
            0 -> {
                curr_settings = Begginer
                makeTextBoxesEnabled(false)
            }
            1 -> {
                curr_settings = Intermediate
                makeTextBoxesEnabled(false)
            }
            2 -> {
                curr_settings = Expert
                makeTextBoxesEnabled(false)
            }
            3 -> {
                curr_settings = Settings(Size(-1,-1), -1)
                makeTextBoxesEnabled(true)
            }
        }
    }
    private fun makeTextBoxesEnabled(enable: Boolean) {
        var text = findViewById<EditText>(R.id.editText_width)
        text.isEnabled = enable
        text = findViewById<EditText>(R.id.editText_height)
        text.isEnabled = enable
        text = findViewById<EditText>(R.id.editText_bombs)
        text.isEnabled = enable
    }
}
