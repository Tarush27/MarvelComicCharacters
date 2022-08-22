package com.tarush27.networking

import com.google.gson.annotations.SerializedName
import com.tarush27.marvelComicModel.MarvelComicCharacters

data class MarvelCharacterResponse(
    @SerializedName("data") var marvelCollection: MarvelCharacters? = MarvelCharacters()
)

data class MarvelCharacters(

    @SerializedName("results") var results: ArrayList<Results> = arrayListOf()

)

data class Results(

    @SerializedName("name") var name: String?,
    @SerializedName("thumbnail") var thumbnail: Thumbnail? = Thumbnail(),
    @SerializedName("comics") var comics: Comics = Comics()

) {
    fun toMarvelCharacter(): MarvelComicCharacters {
        return MarvelComicCharacters(
            celebrityName = name,
            characterImage = thumbnail?.path,
            celebrityImageExtension = thumbnail?.extension
            )
    }
}

data class Comics(

    @SerializedName("items") var items: ArrayList<Items> = arrayListOf()

)

data class Items(

    @SerializedName("name") var name: String

)

data class Thumbnail(

    @SerializedName("path") var path: String? = null,
    @SerializedName("extension") var extension: String? = null

)