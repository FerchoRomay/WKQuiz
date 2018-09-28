package com.ruzaaniapps.wkquiz


import android.os.Bundle
import android.support.v4.app.Fragment
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ruzaaniapps.wkquiz.datamanager.JsonRequest
import com.ruzaaniapps.wkquiz.datamanager.JsonRequest.Companion.WkResultStudyQueue
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class HomeFrag : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.frag_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isConnectedToInternet()) {
            doAsync {
                val studyQueue: WkResultStudyQueue = JsonRequest(apiKey).getStudyQueue()
                uiThread {}
            }
        }
    }

    private fun isConnectedToInternet(): Boolean {
        val cm = context?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    companion object {
        private var apiKey: String = ""
        private const val ARG_API_KEY = "prefsApiKey"

        @JvmStatic
        fun newInstance(userApiKey: String) = HomeFrag().apply {
            arguments = Bundle().apply {
                putString(ARG_API_KEY, userApiKey)
            }
        }
    }
}
