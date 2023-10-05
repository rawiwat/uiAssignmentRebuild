package com.example.uiassignment.miscellaneous


import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import co.yml.charts.common.model.Point

@Immutable
data class Model(
    val id:Int,
    val name: String,
    val current: Double,
    val growthPercent: Double,
    val positiveGrowth:Boolean,
    val description: String,
    val imageId: Int,
    val pointData: List<Point>,
    val chartColor: Color
)
fun Model?.toModel(): Model = Model(
    this!!.id,
    this.name,
    this.current,
    this.growthPercent,
    this.positiveGrowth,
    this.description,
    this.imageId,
    this.pointData,
    this.chartColor
)

/*data class NavigationUIState(
    //--Back Stack for navigation--
    val navigationBackStack: SnapshotStateList<AppScreenTypes> = mutableStateListOf(AppScreenTypes.Screen1()),

    //--Horizontal Draggable Screen offsets--
    val screenXOffset: Float = 0.0f,
    val topScreenXOffset: Float = 0.0f,
    val prevScreenXOffset: Float = 0.0f,

    //-- Index of previous screen, this is used to fine tune animations.--
    val prevScreenIndex: Int = -1,
)

sealed class AppScreenTypes(val route: String, args: String? = null){
    class Screen1(args: String? = null): AppScreenTypes(route = "screen1", args = args)
    class Screen2(args: String? = null): AppScreenTypes(route = "screen1", args = args)
}*/

@Immutable
data class GraphSelector(
    val type: GraphOutputType,
    val text: String
)

@Immutable
data class StatsModel(
    val name: String,
    val money: String
)

@Immutable
data class LinkModel(
    val name: String,
    val imageId: Int
)

@Immutable
data class Hour(
    val text:String,
    val value:Int
)

@Immutable
data class Date(
    val month: Month,
    val day: Int
)

@Immutable
data class Time(
    val date: Date,
    val hour: Hour
)

@Immutable
data class CryptoActivity(
    val type: String,
    val detail: String,
    val time: Time,
    val id: Int,
    val imageId: Int
)

@Immutable
data class TokenModel(
    val id:Int,
    val name: String,
    val current: Double,
    val growthPercent: Double,
    val positiveGrowth:Boolean,
    val description: String,
    val imageId: Int
)

@Immutable
data class Trait(
    val type: TraitType,
    val detail: String
)

@Immutable
data class NFT(
    val id:Int,
    val name:String,
    val imageId: Int,
    val tagNumber: Int,
    val description: String,
    val traits: List<Trait>
)