package com.ruzaaniapps.wkquiz

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.frag_vocabulary.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class VocabularyFrag : Fragment() {
    private var mListener: OnFragmentInteractionListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener)
            mListener = context
        else
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.frag_vocabulary, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vocabRecyclerView.layoutManager = LinearLayoutManager(this.context)
        doAsync {
            val userLevel = listOf(App.datasource!!.wkDao().getUserInfo().level)
            val vocArray = ArrayList(App.datasource!!.wkDao().getVocabularyByLevel(userLevel))
            uiThread { vocabRecyclerView.adapter = VocabularyAdapter(vocArray) }
        }
        //adapter.setClickListener(this)
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        fun newInstance() = VocabularyFrag().apply { }
    }
}