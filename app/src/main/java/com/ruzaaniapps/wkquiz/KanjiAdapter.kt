package com.ruzaaniapps.wkquiz

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import com.ruzaaniapps.wkquiz.datamanager.EntityKanji
import kotlinx.android.synthetic.main.kanji_item.view.*

class KanjiAdapter(private val items: ArrayList<EntityKanji>, private val script: String):
        RecyclerView.Adapter<KanjiAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.kanji_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(items[position]){
            holder.txtKanji.text = kanji
            if(importantReading == "onyomi")
                if(script == "Hiragana")
                    holder.txtReading.text = onyomi.split(",")[0]
                else
                    holder.txtReading.text = onyomi.split(",")[0].map { katakana[it]}.joinToString("")
            else
                holder.txtReading.text = kunyomi.split(",")[0]
            holder.txtMeaning.text = meaning.split(",")[0].capitalize()
        }
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtKanji: TextView = itemView.item_kanji
        val txtReading: TextView = itemView.item_kanji_reading
        val txtMeaning: TextView = itemView.item_kanji_meaning
    }

    companion object {
        val katakana = mapOf('ぁ' to 'ァ', 'あ' to 'ア', 'ぃ' to 'ィ', 'い' to 'イ', 'ぅ' to 'ゥ',
                'う' to 'ウ', 'ぇ' to 'ェ', 'え' to 'エ', 'ぉ' to 'ォ', 'お' to 'オ', 'か' to 'カ',
                'が' to 'ガ', 'き' to 'キ', 'ぎ' to 'ギ', 'く' to 'ク', 'ぐ' to 'グ', 'け' to 'ケ',
                'げ' to 'ゲ', 'こ' to 'コ', 'ご' to 'ゴ', 'さ' to 'サ', 'ざ' to 'ザ', 'し' to 'シ',
                'じ' to 'ジ', 'す' to 'ス', 'ず' to 'ズ', 'せ' to 'セ', 'ぜ' to 'ゼ', 'そ' to 'ソ',
                'ぞ' to 'ゾ', 'た' to 'タ', 'だ' to 'ダ', 'ち' to 'チ', 'ぢ' to 'ヂ', 'っ' to 'ッ',
                'つ' to 'ツ', 'づ' to 'ヅ', 'て' to 'テ', 'で' to 'デ', 'と' to 'ト', 'ど' to 'ド',
                'な' to 'ナ', 'に' to 'ニ', 'ぬ' to 'ヌ', 'ね' to 'ネ', 'の' to 'ノ', 'は' to 'ハ',
                'ば' to 'バ', 'ぱ' to 'パ', 'ひ' to 'ヒ', 'び' to 'ビ', 'ぴ' to 'ピ', 'ふ' to 'フ',
                'ぶ' to 'ブ', 'ぷ' to 'プ', 'へ' to 'ヘ', 'べ' to 'べ', 'ぺ' to 'ペ', 'ほ' to 'ホ',
                'ぼ' to 'ボ', 'ぽ' to 'ポ', 'ま' to 'マ', 'み' to 'ミ', 'む' to 'ム', 'め' to 'メ',
                'も' to 'モ', 'ゃ' to 'ャ', 'や' to 'ヤ', 'ゅ' to 'ュ', 'ゆ' to 'ユ', 'ょ' to 'ョ',
                'よ' to 'ヨ', 'ら' to 'ラ', 'り' to 'リ', 'る' to 'ル', 'れ' to 'レ', 'ろ' to 'ロ',
                'ゎ' to 'ヮ', 'わ' to 'ワ', 'を' to 'ヲ', 'ん' to 'ン', 'ゔ' to 'ヴ', 'ゕ' to 'ヵ',
                'ゖ' to 'ヶ')
    }
}