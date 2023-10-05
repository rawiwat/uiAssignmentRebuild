package com.example.uiassignment.data_layer

import com.example.uiassignment.miscellaneous.Categorized
import com.example.uiassignment.miscellaneous.LinkModel
import com.example.uiassignment.miscellaneous.Model
import com.example.uiassignment.miscellaneous.NFT
import com.example.uiassignment.miscellaneous.StatsModel
import com.example.uiassignment.miscellaneous.TokenModel

interface Database {
    fun getModels():List<Model>
    fun getTokens(): List<TokenModel>
    fun getModelFromID(id:Int): Model
    fun getTokenFromID(id:Int): TokenModel
    fun getDetailFromId(id: Int): String
    fun getStatsList(): List<StatsModel>
    fun getLinkList(): List<LinkModel>
    fun getFavoriteTokens(): List<TokenModel>
    fun getPopularTokens(): List<TokenModel>
    fun getActivityRecords(): List<Categorized>
    fun getArchivedTokens(): List<TokenModel>
    fun getNFTs(): List<NFT>
    fun getNFTFromId(id: Int): NFT
}
