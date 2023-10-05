package com.example.uiassignment.data_layer

import com.example.uiassignment.miscellaneous.Categorized
import com.example.uiassignment.miscellaneous.LinkModel
import com.example.uiassignment.miscellaneous.Model
import com.example.uiassignment.miscellaneous.NFT
import com.example.uiassignment.miscellaneous.StatsModel
import com.example.uiassignment.miscellaneous.TokenModel
import com.example.uiassignment.miscellaneous.generateRecord


class FakeDatabase : Database {
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