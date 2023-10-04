package com.example.uiassignment.composeui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.uiassignment.FakeData
import com.example.uiassignment.FakeDatabase
import com.example.uiassignment.Model
import com.example.uiassignment.R
import com.example.uiassignment.viewmodel.SwipeViewModel
import com.example.uiassignment.getTokenOffset
import com.example.uiassignment.viewmodel.NumberInputViewModel
import com.example.uiassignment.viewmodel.TransactionViewModel

@Composable
fun Transaction(
    textFont: FontFamily,
    transactionViewModel: TransactionViewModel
) {
    val model by transactionViewModel.model.collectAsState()
    val numberInputViewModel = NumberInputViewModel()
    val sendingMoney by numberInputViewModel.money.collectAsState()
    val configuration = LocalConfiguration.current
    val swipeViewModel = SwipeViewModel(configuration.screenHeightDp / 2)
    val primaryColor = colorResource(id = R.color.teal_200)
    val secondaryColor = colorResource(id = R.color.teal_700)
    val listOfInput = listOf("1","2","3","4","5","6","7","8","9",".","0","←")

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(color = Color.Black)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.size(16.dp))

                    Text(
                        text = "Send",
                        style = TextStyle(
                            color = primaryColor
                        ),
                        fontSize = 20.sp,
                        fontFamily = textFont
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Card(
                        modifier = Modifier,
                        colors = CardDefaults.cardColors(
                            containerColor = Color.DarkGray
                        )
                    ) {
                        Text(
                            text = " $ USD ",
                            style = TextStyle(
                                color = secondaryColor
                            ),
                            fontSize = 15.sp,
                            fontFamily = textFont,
                            modifier = Modifier.padding(3.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        },
        bottomBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Black)
                        .padding(6.dp),
                    shape = CircleShape
                ) {
                    Text(
                        text = "Review Transfer",
                        fontSize = 22.sp,
                        fontFamily = textFont
                    )
                }
            }
        },
        modifier = Modifier.background(Color.Black)
    ) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier.background(color = Color.Black)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(
                            start = 8.dp,
                            top = 8.dp,
                            end = 8.dp
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.DarkGray
                    )
                ) {
                    Row {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                        ) {
                            Column {
                                Text(
                                    text = sendingMoney,
                                    style = TextStyle(
                                        color = primaryColor
                                    ),
                                    fontSize = 30.sp,
                                    fontFamily = textFont,
                                    modifier = Modifier
                                        .padding(
                                            start = 15.dp,
                                            top = 10.dp
                                        )
                                        .height(36.dp)
                                )

                                Text(
                                    text = getRawMoneyNumber(sendingMoney),
                                    style = TextStyle(
                                        color = secondaryColor
                                    ),
                                    fontSize = 18.sp,
                                    fontFamily = textFont,
                                    modifier = Modifier
                                        .padding(start = 15.dp)
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Card(
                                    modifier = Modifier
                                        .clickable {
                                            swipeViewModel.turnOn()
                                        },
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Black
                                    )
                                ) {
                                    var thisFontSize by rememberSaveable {
                                        mutableIntStateOf(15)
                                    }
                                    Row(
                                        modifier = Modifier,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(id = model.imageId),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(25.dp)
                                                .clip(CircleShape),
                                            contentScale = ContentScale.Crop
                                        )
                                        Text(
                                            text = " ${model.name}",
                                            style = TextStyle(
                                                color = secondaryColor
                                            ),
                                            fontSize = thisFontSize.sp,
                                            fontFamily = textFont,
                                            modifier = Modifier.padding(3.dp),
                                            onTextLayout = { textLayoutResult ->
                                                if (textLayoutResult.didOverflowWidth) {
                                                    thisFontSize -= 1
                                                }
                                            }
                                        )
                                        Image(
                                            painter = painterResource(id = R.drawable.point_back),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .rotate(180f)
                                                .size(16.dp)
                                        )

                                        Spacer(modifier = Modifier.width(2.dp))
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row {
                                    Text(
                                        text ="Balance: 0.015",
                                        style = TextStyle(
                                            color = secondaryColor
                                        ),
                                        fontSize = 14.sp,
                                        fontFamily = textFont,
                                        modifier = Modifier.padding(3.dp)
                                    )
                                    Text(
                                        text = "Max",
                                        style = TextStyle(
                                            color = Color.Yellow
                                        ),
                                        fontSize = 14.sp,
                                        fontFamily = textFont,
                                        modifier = Modifier.padding(3.dp)
                                    )
                                }
                            }


                            Spacer(
                                modifier = Modifier.width(10.dp)
                            )
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(
                            start = 8.dp,
                            top = 4.dp,
                            end = 8.dp
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.DarkGray
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                var thisFontSize by rememberSaveable {
                                    mutableIntStateOf(15)
                                }
                                AsyncImage(
                                    model = R.drawable.baraka_obama,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop

                                )
                                Text(
                                    text = " Baraka Obama",
                                    style = TextStyle(
                                        color = primaryColor
                                    ),
                                    fontSize = thisFontSize.sp,
                                    fontFamily = textFont,
                                    modifier = Modifier.padding(3.dp),
                                    onTextLayout = { textLayoutResult ->
                                        if (textLayoutResult.didOverflowWidth) {
                                            thisFontSize -= 1
                                        }
                                    }
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.point_back_bright),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .rotate(180f)
                                        .size(16.dp)
                                )

                                Spacer(modifier = Modifier.width(2.dp))
                            }
                            Text(
                                text = "47 previous transfers",
                                style = TextStyle(
                                    color = secondaryColor
                                ),
                                fontSize = 12.sp,
                                fontFamily = textFont,
                                modifier = Modifier.padding(3.dp),
                            )
                        }
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                Card(
                    modifier = Modifier.size(40.dp),
                    border = BorderStroke(
                        width = 4.dp,
                        color = Color.Black
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.DarkGray
                    )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.arrow),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.Bottom
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    content = {
                        items(
                            listOfInput,
                            key = { it }
                        ) {
                            NumberPanel(
                                value = it,
                                textColor = primaryColor,
                                textFont = textFont,
                                numberInputViewModel
                            )
                        }
                    }
                )
            }
        }
    }
    ChangeModel(transactionViewModel,swipeViewModel)
}

fun getRawMoneyNumber(
    value: String
): String {
    val valueAsNum = value.toDouble()
    return if (valueAsNum > 0) {
        (value.toDouble() * 12).toString()
    } else {
        ""
    }
}

@Composable
fun NumberPanel(
    value: String,
    textColor: Color,
    textFont: FontFamily,
    numberInputViewModel: NumberInputViewModel
) {
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp / 3
    val height = configuration.screenHeightDp / 9

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable {
                numberInputViewModel.editMoney(value)
            }
            .width(width.dp)
            .height(height.dp)
    ) {
        Text(
            text = value,
            style = TextStyle(
                color = textColor
            ),
            fontSize = (height / 2.6).sp,
            fontFamily = textFont
        )
    }
}

@Composable
fun ChangeModel(
    transactionViewModel: TransactionViewModel,
    swipeViewModel: SwipeViewModel
) {
    val configuration = LocalConfiguration.current
    val offset by swipeViewModel.currentOffset.collectAsState()
    val active by swipeViewModel.activation.collectAsState()
    val secondaryColor = colorResource(id = R.color.teal_700)
    val textFont = FontFamily(Font(R.font.impact))
    val listOfTokenImageId = listOf(
        R.drawable.token_dgp,
        R.drawable.token_dr,
        R.drawable.token_discord,
        R.drawable.token_doritos,
        R.drawable.token_jgp,
        R.drawable.token_unity
    )
    val padding8 = 8.dp

    AnimatedVisibility(
        visible = active,
        enter = slideInVertically(
            initialOffsetY = { configuration.screenHeightDp },
            animationSpec = tween(1000)
        ),
        exit = slideOutVertically(
            targetOffsetY = { configuration.screenHeightDp },
            animationSpec = tween(1000)
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .offset(0.dp, offset.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)
            ) {
                Spacer(modifier = Modifier.height(padding8))

                Canvas(
                    modifier = Modifier
                        .width(50.dp)
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                swipeViewModel.changeOffset(dragAmount.y.toInt())
                            }
                        },
                ) {
                    val canvasWidth = size.width

                    drawLine(
                        start = Offset(x = canvasWidth, y = 0f),
                        end = Offset(x = 0f, y = 0f),
                        color = Color.Gray,
                        strokeWidth = 7f
                    )
                }

                Spacer(modifier = Modifier.height(padding8))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .padding(
                            start = padding8,
                            end = padding8
                        ),
                    colors = CardDefaults.cardColors(
                        contentColor = Color.DarkGray,
                        containerColor = Color.Gray,
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(padding8))

                        AsyncImage(
                            model = R.drawable.search_icon,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.width(padding8))

                        Text(
                            text = "Search tokens"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(padding8))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Your tokens",
                        style = TextStyle(
                            color = secondaryColor
                        ),
                        fontSize = 20.sp,
                        fontFamily = textFont
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LazyRow {
                            items(
                                listOfTokenImageId,
                                key = { it }
                            ) {
                                Token(imageId = it)
                            }
                        }

                        Spacer(modifier = Modifier.width(padding8))

                        AsyncImage(
                            model = R.drawable.point_back,
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                                .rotate(-90f)
                        )

                        Spacer(modifier = Modifier.width(padding8))
                    }
                }

                Spacer(modifier = Modifier.width(padding8))

                LazyColumn {
                    items(
                        FakeData().getModels(),
                        key = { it.id }
                    ) {
                        ConstraintOption(
                            model = it,
                            transactionViewModel,
                            swipeViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Token(
    imageId:Int
) {
    Card(
        modifier = Modifier
            .size(16.dp)
            .offset(x = getTokenOffset(imageId).dp),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.teal_200)
        )
    ) {
        AsyncImage(
            model = imageId,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun ConstraintOption(
    model: Model,
    transactionViewModel: TransactionViewModel,
    swipeViewModel: SwipeViewModel
) {
    val primaryTextColor = colorResource(id = R.color.teal_200)
    val secondaryTextColor = colorResource(id = R.color.teal_700)
    val textFont = FontFamily(Font(R.font.impact))
    val topFontSize = 16.sp
    val bottomFontSize = 14.sp
    val number = getRawMoneyNumber(model.current.toString())
    ConstraintLayout(
        modifier = Modifier
            .height(75.dp)
            .background(color = Color.Black)
            .fillMaxWidth()
            .clickable {
                swipeViewModel.turnOff()
                transactionViewModel.changeModel(model.id)
            }
    ) {
        val (image, name, money, num, description) = createRefs()

        AsyncImage(
            model = model.imageId,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .size(50.dp)
                .padding(5.dp)
                .clip(CircleShape)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = model.name,
            fontSize = topFontSize,
            style = TextStyle(
                color = primaryTextColor
            ),
            fontFamily = textFont,
            modifier = Modifier.constrainAs(name) {
                start.linkTo(image.end)
                top.linkTo(parent.top, margin = 5.dp)
                bottom.linkTo(description.top)
            }
        )

        Text(
            text = "$${model.current}",
            style = TextStyle(
                color = secondaryTextColor
            ),
            fontFamily = textFont,
            fontSize = bottomFontSize,
            modifier = Modifier.constrainAs(money) {
                end.linkTo(parent.end, margin = 10.dp)
                top.linkTo(num.bottom)
                bottom.linkTo(parent.bottom, margin = 5.dp)
            }
        )

        Text(
            text = model.description,
            fontSize = bottomFontSize,
            style = TextStyle(
                color = secondaryTextColor
            ),
            fontFamily = textFont,
            modifier = Modifier.constrainAs(description) {
                start.linkTo(image.end)
                bottom.linkTo(parent.bottom, margin = 5.dp)
                top.linkTo(name.bottom)
            }
        )

        Text(
            text = number,
            textAlign = TextAlign.End,
            style = TextStyle(
                color = primaryTextColor
            ),
            fontFamily = textFont,
            fontSize = topFontSize,
            modifier = Modifier.constrainAs(num) {
                end.linkTo(parent.end, margin = 10.dp)
                top.linkTo(parent.top, margin = 5.dp)
                bottom.linkTo(money.top)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTransaction() {
    Transaction(
        textFont = FontFamily(Font(R.font.impact)),
        TransactionViewModel(FakeDatabase(),2)
    )
}