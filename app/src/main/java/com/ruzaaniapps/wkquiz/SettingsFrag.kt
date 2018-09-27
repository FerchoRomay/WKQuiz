package com.ruzaaniapps.wkquiz

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.frag_settings.*

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
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.frag_settings, container, false)

    override fun onDetach() {
        super.onDetach()
        listener = null
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
