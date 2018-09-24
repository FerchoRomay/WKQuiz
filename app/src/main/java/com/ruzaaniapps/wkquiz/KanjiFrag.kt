package com.ruzaaniapps.wkquiz


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.frag_kanji.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class KanjiFrag : Fragment() {
    private var prefsOnyomiScript = ""
    private var prefsColumns = 3
    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View = inflater.inflate(R.layout.frag_kanji, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = rvKanji
        val numberOfColumns = 4
        recyclerView.layoutManager = GridLayoutManager(this.context, numberOfColumns)
        doAsync {
            val userLevel = listOf(App.datasource!!.wkDao().getUserInfo().level)
            val kanjiArray = ArrayList(App.datasource!!.wkDao().getAllKanji())
            uiThread { recyclerView.adapter = KanjiAdapter(kanjiArray, prefsOnyomiScript) }
        }
        //adapter.setClickListener(this)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener)
            mListener = context
        else
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        arguments?.getString(ARG_SCRIPT).let { prefsOnyomiScript = it.toString() }
        arguments!!.getInt(ARG_COLUMNS).let { prefsColumns = it }

    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private const val ARG_SCRIPT = "prefsOnyomiScript"
        private const val ARG_COLUMNS = "prefsColumns"

        fun newInstance(onyomiScript: String, columns: Int) = KanjiFrag().apply {
            arguments = Bundle().apply {
                putString(ARG_SCRIPT, onyomiScript)
                putInt(ARG_COLUMNS, columns)
            }
        }
    }
}
