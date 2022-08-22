package com.tarush27.marvelAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tarush27.marvelComicModel.MarvelComicCharacters
import com.tarush27.marvelcharacters.R
import com.tarush27.networking.MarvelCharacterResponse

class MarvelComicAdapter : RecyclerView.Adapter<MarvelComicAdapter.MarvelViewHolder>() {

    val marvelCharacters: ArrayList<MarvelComicCharacters> = arrayListOf()

    inner class MarvelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val marvelCl: ConstraintLayout = itemView.findViewById(R.id.marvel_cl)
        val marvelAvatar: ImageView = itemView.findViewById(R.id.marvelAvatar)
        val llCharactersDetail: LinearLayout = itemView.findViewById(R.id.ll_characters_detail)
        val characterName: TextView = itemView.findViewById(R.id.characterName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelViewHolder {
        val marvelViewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.marvel_characters, parent, false)
        return MarvelViewHolder(marvelViewHolder)
    }

    override fun onBindViewHolder(holder: MarvelViewHolder, position: Int) {
        bindCharacterData(position, holder)

    }

    private fun bindCharacterData(
        position: Int,
        holder: MarvelViewHolder
    ) {
        val characterInfo: MarvelComicCharacters = marvelCharacters[position]
        val context = holder.marvelAvatar.context
        getMarvelAvatar(characterInfo, context, holder)
        holder.characterName.text = characterInfo.celebrityName
        holder.marvelCl.setOnClickListener {
            if (holder.llCharactersDetail.visibility == View.GONE) {
                holder.llCharactersDetail.visibility = View.VISIBLE
            } else {
                holder.llCharactersDetail.visibility = View.GONE
            }
        }
    }

    private fun getMarvelAvatar(
        characterInfo: MarvelComicCharacters,
        context: Context,
        holder: MarvelViewHolder
    ) {
        val characterImagePath =
            "${
                characterInfo.characterImage?.replace(
                    "http",
                    "https"
                )
            }/landscape_xlarge.${characterInfo.celebrityImageExtension}"
        Glide.with(context).load(characterImagePath).into(holder.marvelAvatar)
    }


    override fun getItemCount(): Int = marvelCharacters.size


    fun updateCharacters(marvelHeroes: ArrayList<MarvelComicCharacters>) {
        marvelCharacters.addAll(marvelHeroes)
        notifyDataSetChanged()
    }
}