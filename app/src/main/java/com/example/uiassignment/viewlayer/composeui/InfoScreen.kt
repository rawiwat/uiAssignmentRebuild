package com.example.uiassignment.viewlayer.composeui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.uiassignment.miscellaneous.Constant.Companion.primaryColor
import com.example.uiassignment.miscellaneous.Constant.Companion.secondaryColor
import com.example.uiassignment.miscellaneous.Constant.Companion.textFont
import com.example.uiassignment.data_layer.FakeData
import com.example.uiassignment.data_layer.FakeDatabase
import com.example.uiassignment.miscellaneous.GraphOutputType
import com.example.uiassignment.miscellaneous.GraphSelector
import com.example.uiassignment.miscellaneous.Model
import com.example.uiassignment.R
import com.example.uiassignment.miscellaneous.generateAXisX
import com.example.uiassignment.miscellaneous.generateAxisY
import com.example.uiassignment.miscellaneous.getAxisXStepSize
import com.example.uiassignment.miscellaneous.getPointData
import com.example.uiassignment.viewlayer.composeui.viewmodel.InfoScreenViewModel
import kotlin.math.roundToInt


@Composable
fun InfoScreen(
    navController: NavController,
    infoScreenViewModel: InfoScreenViewModel
) {
    val model = infoScreenViewModel.model
    val statsList = infoScreenViewModel.statsList
    val linkList = infoScreenViewModel.linkList
    var readMore by remember { mutableStateOf(false) }
    val offsetX by remember { mutableFloatStateOf(0f) }
    val screenScrollState = rememberScrollState()
    var isFavorite by remember { mutableStateOf(false) }
    val topIconSize = 20.dp
    val bodyFontSize = 14.sp
    val splitedMoney = model.current.toString().split(".")
    val bodyPadding = 5.dp

    Scaffold(
        topBar = {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(color = Color.Black)
            ) {
                val (threeDots,arrow,fav,nameAndIcon,money) = createRefs()

                Image(
                    painter = painterResource(id = R.drawable.point_back),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { navController.navigate("Home") }
                        .size(topIconSize)
                        .constrainAs(arrow) {
                            start.linkTo(parent.start, margin = 15.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )

                Image(
                    painter = painterResource(
                        id = if (isFavorite) {
                            R.drawable.fav_is_selected
                        } else { R.drawable.fav_not_selected }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { isFavorite = !isFavorite }
                        .size(topIconSize)
                        .constrainAs(fav) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end, margin = 15.dp)
                        }
                )

                if(screenScrollState.value >= 280) {
                    Text(
                        text = "$${model.current}",
                        style = TextStyle(
                            color = primaryColor
                        ),
                        fontFamily = textFont,
                        fontSize = 16.sp,
                        modifier = Modifier.constrainAs(money) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                    )

                    Row(
                        modifier = Modifier.constrainAs(nameAndIcon) {
                            top.linkTo(money.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                    ) {
                        Image(
                            painter = painterResource(id = model.imageId),
                            contentDescription = null,
                            modifier = Modifier
                                .size(22.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(3.dp))

                        Text(
                            text = model.name,
                            style = TextStyle(
                                color = secondaryColor
                            ),
                            fontFamily = textFont,
                            fontSize = 13.sp,
                            modifier = Modifier
                        )
                    }

                }

                Image(
                    painter = painterResource(id = R.drawable.three_dots),
                    contentDescription = null,
                    modifier = Modifier
                        .size(topIconSize)
                        .constrainAs(threeDots) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(fav.start, margin = 15.dp)
                        }
                )
            }
        },
        bottomBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        navController.navigate("Archive/${model.id}")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Black)
                        .padding(6.dp),
                    shape = CircleShape
                ) {
                    Text(
                        text = "Swap",
                        fontSize = 22.sp,
                        fontFamily = textFont
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(screenScrollState)
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .background(color = Color.Black)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateTopPadding(),
                    end = bodyPadding,
                    start = bodyPadding
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = model.imageId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp)
                        .clip(CircleShape)
                )

                Text(
                    text = model.name,
                    style = TextStyle(
                        color = primaryColor
                    ),
                    fontFamily = textFont,
                    fontSize = 13.sp
                )

                Row {
                    Text(
                        text = "$${splitedMoney[0]}",
                        style = TextStyle(
                            color = primaryColor
                        ),
                        fontFamily = textFont,
                        fontSize = 30.sp
                    )
                    Text(
                        text = ".${splitedMoney[1]}",
                        style = TextStyle(
                            color = secondaryColor
                        ),
                        fontFamily = textFont,
                        fontSize = 30.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(
                            id = if(model.positiveGrowth)
                                R.drawable.up
                            else R.drawable.down
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(6.dp)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = "${model.growthPercent}%",
                        style = TextStyle(
                            color = if (model.positiveGrowth) {
                                Color.Green
                            } else {
                                Color.Red
                            }
                        ),
                        fontFamily = textFont,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(9.dp))

            Graph(
                model = model, infoScreenViewModel
            )

            Spacer(modifier = Modifier.height(9.dp))

            ConstraintLayout(
                modifier = Modifier
            ) {
                val (
                    line1, line2, yourBalance,
                    yourMoney, send, detailText
                ) = createRefs()
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(line1) {
                            top.linkTo(parent.top)
                        }
                ) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    drawLine(
                        start = Offset(x = canvasWidth, y = 0f),
                        end = Offset(x = 0f, y = canvasHeight),
                        color = secondaryColor
                    )
                }

                Text(
                    text = "Your balance",
                    style = TextStyle(
                        color = secondaryColor
                    ),
                    fontFamily = textFont,
                    fontSize = bodyFontSize,
                    modifier = Modifier.constrainAs(yourBalance) {
                        top.linkTo(line1.bottom,margin = 8.dp)
                    }
                )

                Text(
                    text = "$4.20",
                    style = TextStyle(
                        color = Color.White
                    ),
                    fontFamily = textFont,
                    fontSize = bodyFontSize,
                    modifier = Modifier.constrainAs(yourMoney) {
                        top.linkTo(yourBalance.bottom)
                    }
                )

                Text(
                    text = "1.8.2 Lime",
                    style = TextStyle(
                        color = secondaryColor
                    ),
                    fontFamily = textFont,
                    fontSize = bodyFontSize,
                    modifier = Modifier.constrainAs(detailText) {
                        top.linkTo(yourMoney.bottom)
                    }
                )

                Image(
                    painter = painterResource(R.drawable.share_plane),
                    contentDescription = null,
                    modifier = Modifier
                        .size(22.dp)
                        .clickable { navController.navigate("transaction/${model.id}") }
                        .constrainAs(send) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end, margin = 8.dp)
                        }
                )

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(line2) {
                            bottom.linkTo(parent.bottom)
                            top.linkTo(detailText.bottom, margin = 8.dp)
                        }
                ) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    drawLine(
                        start = Offset(x = canvasWidth, y = 0f),
                        end = Offset(x = 0f, y = canvasHeight),
                        color = secondaryColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = FakeData().getDetailFromId(model.id),
                maxLines = if (readMore) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(color = Color.White),
                modifier = Modifier.animateContentSize(animationSpec = tween(1000)),
                fontSize = bodyFontSize,
                fontFamily = textFont
            )

            Text(
                text = if (readMore) "Show Less" else "Read More",
                modifier = Modifier
                    .clickable { readMore = !readMore },
                style = TextStyle(
                    color = colorResource(id = R.color.teal_200)
                ),
                fontSize = bodyFontSize,
                fontFamily = textFont
            )

            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = "Stats",
                style = TextStyle(
                    color = secondaryColor
                ),
                fontSize = bodyFontSize,
                fontFamily = textFont
            )

            for (snack in statsList) {
                Stats(
                    name = snack.name,
                    money = snack.money,
                    textFont = textFont
                )
            }

            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = "Links",
                style = TextStyle(
                    color = secondaryColor
                ),
                fontSize = bodyFontSize,
                fontFamily = textFont
            )

            LazyRow(
                modifier = Modifier.height(40.dp)
            ) {
                items(
                    linkList,
                    key = { it.name }
                ) { link->
                    Links(imageId = link.imageId, text = link.name)
                }
            }
        }
    }
}

@Composable
fun Graph(
    model: Model,
    infoViewModel: InfoScreenViewModel
) {
    val currentOutPut by infoViewModel.graphType.collectAsState()

    val graphTypes = listOf(
        GraphSelector(type = GraphOutputType.HOUR,text ="1H"),
        GraphSelector(type = GraphOutputType.DAY,text ="1D"),
        GraphSelector(type = GraphOutputType.WEEK,text ="1W"),
        GraphSelector(type = GraphOutputType.MONTH,text ="1M"),
        GraphSelector(type = GraphOutputType.YEAR,text ="1Y")
    )

    Column {
        LineChart(
            modifier = Modifier
                .background(color = Color.Black)
                .fillMaxWidth()
                .height(250.dp)
            ,
            lineChartData = generateChartData(
                pointsData = getPointData(currentOutPut,model.pointData),
                lineColor = model.chartColor,
                xAxisData = generateAXisX(
                    getPointData(currentOutPut,model.pointData),
                    getAxisXStepSize(currentOutPut)
                ),
                yAxisData = generateAxisY(20)
            ),
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(
                graphTypes,
                key = { it.text }
            ) {
                GraphOutPutSelector(
                    currentType = currentOutPut,
                    thisType = it.type,
                    buttonText = it.text,
                    infoViewModel
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InfoPreview() {
    InfoScreen(
        navController = NavController(LocalContext.current),
        infoScreenViewModel = InfoScreenViewModel(FakeDatabase(),1)
    )
}

fun generateChartData(
    pointsData: List<Point>,
    lineColor: Color,
    xAxisData: AxisData,
    yAxisData: AxisData,
):LineChartData {
    return LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = lineColor,
                        lineType = LineType.SmoothCurve(),
                        width = 3f
                    ),
                    IntersectionPoint(
                        color = lineColor,
                        radius = 1.dp
                    ),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                lineColor,
                                lineColor,
                                Color.Transparent,
                            )
                        ),
                    ),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = Color.Black
    )
}

@Composable
fun GraphOutPutSelector(
    currentType: GraphOutputType,
    thisType: GraphOutputType,
    buttonText: String,
    infoViewModel: InfoScreenViewModel
) {
    Spacer(modifier = Modifier.width(5.dp))

    Button(
        onClick = {
            infoViewModel.changeGraphType(thisType)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (currentType == thisType) {
                colorResource(R.color.teal_700)
            } else { Color.Black },
            contentColor = if (currentType == thisType) {
                colorResource(id = R.color.teal_200)
            } else { colorResource(id = R.color.teal_700) }
        ),
//        border = BorderStroke(3.dp,Color.Cyan)
    ) {
        Text(
            text = buttonText,
            fontFamily = FontFamily(Font(R.font.impact))
        )
    }

    Spacer(modifier = Modifier.width(5.dp))
}

@Composable
fun Stats(
    name: String,
    money: String,
    textFont: FontFamily
) {
    ConstraintLayout(
        modifier = Modifier
            .background(color = Color.Black)
            .height(20.dp)
            .fillMaxWidth()
    ) {
        val (statsIcon,statsName,statsMoney) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.stats_icon),
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .constrainAs(statsIcon) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                },
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = name,
            style = TextStyle(
                color = Color.White
            ),
            fontSize = 15.sp,
            fontFamily = textFont,
            modifier = Modifier.constrainAs(statsName) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(statsIcon.end, margin = 5.dp)
            }
        )

        Text(
            text = "$$money",
            style = TextStyle(
                color = Color.White
            ),
            fontSize = 15.sp,
            fontFamily = textFont,
            modifier = Modifier.constrainAs(statsMoney) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Composable
fun Links(
    imageId: Int,
    text: String
) {
    Spacer(modifier = Modifier.width(4.dp))

    //var containerWidth by rememberSaveable { mutableStateOf(80) }
    Card(
        modifier = Modifier.width(75.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        )
    ) {
        Row(
            modifier = Modifier.padding(2.dp)
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .padding(2.dp)
            )

            Text(
                text = text,
                style = TextStyle(
                    color = Color.White
                ),
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.impact)),
                maxLines = 1,
                overflow = TextOverflow.Clip,
                /*onTextLayout = { textLayoutResult ->
                    if (!textLayoutResult.didOverflowWidth) {
                        containerWidth -= 1
                    }
                }*/
            )
        }
    }
    Spacer(modifier = Modifier.width(4.dp))
}

@Preview
@Composable
fun ShowQRPreview() {
    ShowMyQR()
}