package com.example.uiassignment

import com.example.uiassignment.data_layer.FakeData

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

class FakeDatabase :Database {
    override fun getModels(): List<Model> {
        return FakeData().getModels()
    }

    override fun getTokens(): List<TokenModel> {
        return FakeData().getTokens()
    }

    override fun getModelFromID(id: Int): Model {
        return FakeData().getModelFromID(id)
    }

    override fun getTokenFromID(id: Int): TokenModel {
        return FakeData().getTokenFromID(id)
    }

    override fun getDetailFromId(id: Int): String {
        return FakeData().getDetailFromId(id)
    }

    override fun getStatsList(): List<StatsModel> {
        return FakeData().getStatsList()
    }

    override fun getLinkList(): List<LinkModel> {
        return FakeData().getLinkList()
    }

    override fun getFavoriteTokens(): List<TokenModel> {
        return FakeData().favoriteTokens()
    }

    override fun getPopularTokens(): List<TokenModel> {
        return FakeData().popularTokens()
    }

    override fun getActivityRecords(): List<Categorized> {
        return generateRecord().map {
            Categorized(
                month = it.key.toString(),
                activities = it.value
            )
        }
    }

    override fun getArchivedTokens(): List<TokenModel> {
        return FakeData().getArchivedTokens()
    }

    override fun getNFTs(): List<NFT> {
        return FakeData().getNFTs()
    }

    override fun getNFTFromId(id: Int): NFT {
        return FakeData().getNFTFromID(id)
    }
}