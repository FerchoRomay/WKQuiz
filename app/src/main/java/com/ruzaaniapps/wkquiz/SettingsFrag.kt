package com.ruzaaniapps.wkquiz

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.frag_settings.*
import org.jetbrains.anko.support.v4.selector
import org.jetbrains.anko.support.v4.toast

class SettingsFrag : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
        arguments?.getString(ARG_SCRIPT).let { prefsOnyomiScript = it.toString() }
        arguments?.getInt(ARG_COLUMNS).let { prefsColumns = it ?: prefsColumns }
        arguments?.getBoolean(ARG_LEVELS).let { prefsSeeAllLevels = it ?: prefsSeeAllLevels}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsOnyomiValue.text = prefsOnyomiScript
        settingsColumnsValue.text = prefsColumns.toString()
        settingsAllKanjiValue.text = if (prefsSeeAllLevels) {
            getString(R.string.app_prefs_all_levels_true)
        } else {
            getString(R.string.app_prefs_all_levels_false)
        }
        setClickListeners()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.frag_settings, container, false)

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun setClickListeners() {
        settingsOnyomiFrame.setOnClickListener {
        val scripts = listOf(getString(R.string.app_prefs_hiragana),
                getString(R.string.app_prefs_katakana))
        selector(getString(R.string.settings_onyomi_text), scripts) { _, i ->
            settingsOnyomiValue.text = scripts[i]
            MainAct.prefsOnyomiScript = scripts[i]
        }
    }
        settingsColumnsFrame.setOnClickListener {
            val columns = listOf("2", "3", "4")
            selector(getString(R.string.settings_columns_text), columns) { _, i ->
                settingsColumnsValue.text = columns[i]
                MainAct.prefsKanjiColumns = columns[i].toInt()
            }
        }
        settingsAllKanjiFrame.setOnClickListener {
            val answers = listOf(getString(R.string.app_prefs_all_levels_true),
                    getString(R.string.app_prefs_all_levels_false))
            selector(getString(R.string.settings_all_kanji_text), answers) { _, i ->
                settingsAllKanjiValue.text = answers[i]
                MainAct.prefsSeeAllLevels = settingsAllKanjiValue.text == getString(R.string.app_prefs_all_levels_true)
            }
        }
        settingsShareAppFrame.setOnClickListener {
            toast("Share app functionality is still not coded :V")
        }
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private var listener: OnFragmentInteractionListener? = null
        private var prefsOnyomiScript = "hiragana"
        private var prefsColumns = 3
        private var prefsSeeAllLevels = false
        private const val ARG_SCRIPT = "prefsOnyomiScript"
        private const val ARG_COLUMNS = "prefsColumns"
        private const val ARG_LEVELS = "prefsSeeAllLevels"

        @JvmStatic
        fun newInstance(onyomiScript: String, columns: Int, levels: Boolean) = SettingsFrag().apply {
            arguments = Bundle().apply {
                putString(ARG_SCRIPT, onyomiScript)
                putInt(ARG_COLUMNS, columns)
                putBoolean(ARG_LEVELS, levels)
            }
        }
    }
}
