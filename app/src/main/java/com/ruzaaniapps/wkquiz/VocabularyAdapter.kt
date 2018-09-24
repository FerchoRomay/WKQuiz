package com.ruzaaniapps.wkquiz

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import com.ruzaaniapps.wkquiz.datamanager.EntityVocabulary
import kotlinx.android.synthetic.main.vocabulary_item.view.*

class VocabularyAdapter(private val items: ArrayList<EntityVocabulary>) : RecyclerView.Adapter<VocabularyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.vocabulary_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(items[position]){
            holder.txtVocabulary.text = word
            holder.txtReading.text = kana.split(",")[0]
            holder.txtMeaning.text = meaning.split(",")[0].capitalize()
        }
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtVocabulary: TextView = itemView.item_vocabulary
        val txtReading: TextView = itemView.item_voc_reading
        val txtMeaning: TextView = itemView.item_voc_meaning
    }
}