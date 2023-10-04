package com.example.uiassignment

interface Database {
    fun getModels():List<Model>
    fun getTokens(): List<TokenModel>
    fun getModelFromID(id:Int): Model
    fun getTokenFromID(id:Int): TokenModel
    fun getDetailFromId(id: Int): String
    fun getStatsList(): List<StatsModel>
    fun getLinkList(): List<LinkModel>
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


}