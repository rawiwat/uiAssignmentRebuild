package com.example.uiassignment.viewmodel

import androidx.lifecycle.ViewModel
import com.example.uiassignment.Database

class NFTDetailViewModel(
    database: Database,
    nftId:Int,
    modelId:Int
): ViewModel() {
    val model = database.getModelFromID(modelId)
    val nft = database.getNFTFromId(nftId)
}