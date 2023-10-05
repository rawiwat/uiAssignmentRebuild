package com.example.uiassignment.viewlayer.composeui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.uiassignment.data_layer.Database

class NFTDetailViewModel(
    database: Database,
    nftId:Int,
    modelId:Int
): ViewModel() {
    val model = database.getModelFromID(modelId)
    val nft = database.getNFTFromId(nftId)
}