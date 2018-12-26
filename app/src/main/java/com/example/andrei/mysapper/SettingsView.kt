package com.example.andrei.mysapper

import android.util.Log
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.activity_game_settings.view.*

data class Settings(var field_size:Size, var bombs_count:Int)
/*
val Begginer = Settings(Size(9,9),10)
val Intermediate = Settings(Size(16,16), 40)
val Expert = Settings(Size(31,16), 99)
*/
val Begginer = Settings(Size(3,3),3)
val Intermediate = Settings(Size(7,7), 8)
val Expert = Settings(Size(9,9), 10)
interface SettingsView {
    var saved_settings :Settings

    interface settingsViewListener {
        fun onApplyTap()
        fun onUndoTap()
    }

    fun setListener(newListener: settingsViewListener)
}

class SettingsViewImpl(layoutInflater: LayoutInflater) : SettingsView{

    override var saved_settings = Settings(Size(0,0), 0)
    private var m_root_view = layoutInflater.inflate(R.layout.activity_game_settings, null)
    private var m_settings_tap_listener:  SettingsView.settingsViewListener? = null

    init {
        m_root_view.button_back.setOnClickListener {onClickBack()}
        m_root_view.button_apply_restart.setOnClickListener {onClickApply()}
    }

    override fun setListener(newListener: SettingsView.settingsViewListener) {
        m_settings_tap_listener = newListener
    }

    fun onClickBack() {
        Log.d("SapperSettings", "CLick back registered")
        m_settings_tap_listener?.onUndoTap()
    }

    fun onClickApply () {
        m_settings_tap_listener?.onApplyTap()
    }
}