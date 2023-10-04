package com.example.uiassignment.composeui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.platform.LocalContext
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
import com.example.uiassignment.R
import com.example.uiassignment.viewmodel.SwipeViewModel
import com.example.uiassignment.TokenModel
import com.example.uiassignment.toModel
import com.example.uiassignment.trimDouble
import com.example.uiassignment.viewmodel.NumberInputViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SwapScreen(
    textFont: FontFamily,
    context: Context,
    modelId: Int,
) {
    var model by remember {
        mutableStateOf(FakeData().getModelFromID(modelId).toModel())
    }
    val numberInputViewModel = NumberInputViewModel()
    val primaryColor = colorResource(id = R.color.teal_200)
    val secondaryColor = colorResource(id = R.color.teal_700)
    var sendingMoney by rememberSaveable {
        mutableStateOf("0")
    }
    var swappingMoney by rememberSaveable {
        mutableStateOf("0")
    }
    val listOfInput = listOf("1","2","3","4","5","6","7","8","9",".","0","←")
    val singleDigitNum = listOf("0","1","2","3","4","5","6","7","8","9")
    val CHANGE_ACTIVATION_TO by remember {
        mutableStateOf("change_swap_activation_to")
    }

    val CHANGE_ACTIVATION by remember {
        mutableStateOf("change_swap_activation")
    }

    var tokenChoose by rememberSaveable {
        mutableStateOf(false)
    }

    var token by remember {
        mutableStateOf(FakeData().getTokenFromID(6))
    }
    val configuration = LocalConfiguration.current
    val topCardHeight by remember {
        mutableIntStateOf(configuration.screenHeightDp / 7)
    }

    val attachedHeight by remember {
        mutableIntStateOf(topCardHeight / 4)
    }
    val bottomCardClosedHeight by remember {
        mutableIntStateOf(topCardHeight + attachedHeight)
    }

    val bottomCardOpenedHeight by remember {
        mutableIntStateOf(bottomCardClosedHeight + attachedHeight)
    }

    var loadingResult by remember {
        mutableStateOf(false)
    }

    DisposableEffect(sendingMoney) {
        val moneyBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                loadingResult = true
                if (p1 != null) {
                    val received = p1.getStringExtra("moneyEdit").toString()
                    if (sendingMoney == "0") {
                        if (singleDigitNum.contains(received)) {
                            sendingMoney = received
                        } else if (received == ".") {
                            sendingMoney += "."
                        }
                    } else {
                        if (singleDigitNum.contains(received)) {
                            sendingMoney += received
                        } else if (received == ".") {
                            if (sendingMoney.contains(".")) {
                                sendingMoney = sendingMoney.filterNot { it == '.' }
                                sendingMoney += "."
                            } else {
                                sendingMoney += "."
                            }
                        } else if (received == "←") {
                            if (sendingMoney.length >= 2) {
                                sendingMoney = sendingMoney.substring(0,sendingMoney.length - 1)
                            } else {
                                sendingMoney = "0"
                            }
                        }
                    }
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(500)
                        loadingResult = false
                        swappingMoney = trimDouble(sendingMoney.toDouble() * 3.14,"10").toString()
                    }
                }
            }
        }

        context.registerReceiver(
            moneyBroadcastReceiver,
            IntentFilter("moneyEdit"),
            Context.RECEIVER_EXPORTED
        )

        onDispose {
            context.unregisterReceiver(moneyBroadcastReceiver)
        }
    }

    DisposableEffect(model) {
        val modelBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val idReceived = p1!!.getIntExtra("change_model_to",model.id)
                model = FakeData().getModelFromID(idReceived).toModel()
            }
        }

        context.registerReceiver(
            modelBroadcastReceiver,
            IntentFilter("change_model"),
            Context.RECEIVER_EXPORTED
        )

        onDispose{
            context.unregisterReceiver(modelBroadcastReceiver)
        }
    }

    DisposableEffect(tokenChoose) {
        val tokenReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val tokenReceived = p1?.getIntExtra("Chosen_Token_id",6)
                tokenReceived?.let {
                    token = FakeData().getTokenFromID(it)
                    tokenChoose = true
                }!!
            }
        }

        context.registerReceiver(
            tokenReceiver,
            IntentFilter("Choose_Token"),
            Context.RECEIVER_EXPORTED
        )
        onDispose {
            context.unregisterReceiver(tokenReceiver)
        }
    }

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
                        text = "Swap",
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
                        modifier = Modifier.wrapContentSize(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.DarkGray
                        )
                    ) {
                        Row(
                            modifier = Modifier.wrapContentSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.width(8.dp))

                            Image(
                                painter = painterResource(R.drawable.eye_icon),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )

                            Text(
                                text = "View-only",
                                style = TextStyle(
                                    color = primaryColor
                                ),
                                fontSize = 15.sp,
                                fontFamily = textFont,
                                modifier = Modifier.padding(3.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))
                        }

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
                        text = "Review swap",
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
                        .height(topCardHeight.dp)
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
                                    text = if (getRawMoneyNumber(sendingMoney).isNotEmpty())
                                        trimDouble(getRawMoneyNumber(sendingMoney).toDouble(),"10").toString()
                                        else "" ,
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
                                    modifier = Modifier,
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

                                Text(
                                    text ="Balance: 0.001",
                                    style = TextStyle(
                                        color = secondaryColor
                                    ),
                                    fontSize = 14.sp,
                                    fontFamily = textFont,
                                    modifier = Modifier.padding(3.dp)
                                )
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
                        .height(
                            if (sendingMoney.toDouble() > 0) {
                                bottomCardOpenedHeight.dp
                            } else {
                                bottomCardClosedHeight.dp
                            }
                        )
                        .padding(
                            start = 8.dp,
                            top = 4.dp,
                            end = 8.dp
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.DarkGray
                    )
                ) {
                    Column {
                        if (tokenChoose) {
                            Row {
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                ) {
                                    Column {
                                        Text(
                                            text = swappingMoney,
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
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(topCardHeight.dp)
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.End
                                    ) {
                                        Card(
                                            modifier = Modifier,
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
                                                    painter = painterResource(id = token.imageId),
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .size(25.dp)
                                                        .clip(CircleShape),
                                                    contentScale = ContentScale.Crop
                                                )
                                                Text(
                                                    text = " ${token.description}",
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
                        } else {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(topCardHeight.dp)
                            ) {
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Blue,
                                        contentColor = Color.White
                                    ),
                                    modifier = Modifier
                                        .clickable {
                                            val intent = Intent(CHANGE_ACTIVATION)
                                            intent.putExtra(CHANGE_ACTIVATION_TO,true)
                                            context.sendBroadcast(intent)
                                        }
                                ) {
                                    Text(
                                        text = "Choose a Token",
                                        modifier = Modifier.padding(6.dp),
                                        fontSize = 15.sp,
                                        fontFamily = textFont
                                    )
                                }
                            }
                        }
                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                        ) {
                            val canvasWidth = size.width
                            val canvasHeight = size.height
                            drawLine(
                                start = Offset(x = canvasWidth, y = 0f),
                                end = Offset(x = 0f, y = canvasHeight),
                                color = Color.Black
                            )
                        }
                        Row(
                            modifier = Modifier
                                .height(attachedHeight.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(8.dp))

                            Image(
                                painter = painterResource(R.drawable.warning_icon),
                                contentDescription = null,
                                modifier = Modifier.size(15.dp)
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = "Restore your wallet to swap",
                                style = TextStyle(
                                    color = Color.Yellow
                                ),
                                fontSize = 12.sp,
                                fontFamily = textFont,
                            )
                        }

                        if (sendingMoney.toDouble() > 0) {
                            Canvas(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                            ) {
                                val canvasWidth = size.width
                                val canvasHeight = size.height
                                drawLine(
                                    start = Offset(x = canvasWidth, y = 0f),
                                    end = Offset(x = 0f, y = canvasHeight),
                                    color = Color.Black
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .height(attachedHeight.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (loadingResult) {

                                    Spacer(modifier = Modifier.width(8.dp))
                                    CircularProgressIndicator(
                                        color = Color.Black,
                                        trackColor = Color.White,
                                        modifier = Modifier.size(15.dp)
                                    )

                                    Spacer(modifier = Modifier.width(4.dp))

                                    Text(
                                        text = "fetching prices",
                                        style = TextStyle(
                                            color = Color.White
                                        ),
                                        fontSize = 12.sp,
                                        fontFamily = textFont,
                                    )
                                } else {

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Image(
                                        painter = painterResource(id = R.drawable.see_more_icon),
                                        contentDescription = null,
                                        modifier = Modifier.size(15.dp)
                                    )

                                    Spacer(modifier = Modifier.width(4.dp))

                                    Text(
                                        text = "1 ${model.name} = 3.14 ${token.description}",
                                        style = TextStyle(
                                            color = Color.White
                                        ),
                                        fontSize = 12.sp,
                                        fontFamily = textFont,
                                    )
                                }
                            }
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

    ChangeTokenInSwap(context = context)
}

@Composable
fun ChangeTokenInSwap(
    context: Context
) {
    val configuration = LocalConfiguration.current
    val viewModel = SwipeViewModel(configuration.screenHeightDp / 2)
    val offset by viewModel.currentOffset.collectAsState()
    val secondaryColor = colorResource(id = R.color.teal_700)
    val textFont = FontFamily(Font(R.font.impact))
    val listOfToken = FakeData().getTokens()

    val favorite = listOfToken.subList(0,2)
    val popular = listOfToken.subList(3,4)
    val padding8 by remember {
        mutableStateOf(8.dp)
    }
    val padding12 by remember {
        mutableStateOf(12.dp)
    }
    var active by remember {
        mutableStateOf(false)
    }

    val CHANGE_ACTIVATION_TO by remember {
        mutableStateOf("change_swap_activation_to")
    }

    val CHANGE_ACTIVATION by remember {
        mutableStateOf("change_swap_activation")
    }

    DisposableEffect(active) {
        val activationReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val activationReceived = p1!!.getBooleanExtra(CHANGE_ACTIVATION_TO, false)
                active = activationReceived
                viewModel.setOffsetToDefault()
            }
        }
        context.registerReceiver(
            activationReceiver,
            IntentFilter(CHANGE_ACTIVATION),
            Context.RECEIVER_EXPORTED
        )
        onDispose {
            context.unregisterReceiver(activationReceiver)
        }
    }

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
                //horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)
            ) {
                Spacer(modifier = Modifier.height(padding8))

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Canvas(
                        modifier = Modifier
                            .width(50.dp)
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    change.consume()
                                    viewModel.changeOffset(dragAmount.y.toInt())
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

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Suggested",
                        style = TextStyle(
                            color = secondaryColor
                        ),
                        fontSize = 15.sp,
                        fontFamily = textFont,
                        modifier = Modifier
                            .padding(start = padding12)
                    )

                    LazyHorizontalStaggeredGrid(
                        rows = StaggeredGridCells.Fixed(2),
                        modifier = Modifier
                            .height(120.dp)
                            .wrapContentSize()
                    ) {
                        items(listOfToken, key = { it.id }) {
                            SuggestedToken(imageId = it.imageId, name = it.description, context = context, id = it.id)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(padding8))

                Text(
                    text = "Favorite",
                    style = TextStyle(
                        color = secondaryColor
                    ),
                    fontSize = 15.sp,
                    fontFamily = textFont,
                    modifier = Modifier
                        .padding(start = padding12)
                )

                LazyColumn {
                    items(
                        favorite,
                        key = { it.id }
                    ) {
                        ChooseToken(model = it, context = context)
                    }
                }

                Spacer(modifier = Modifier.height(padding8))

                Text(
                    text = "Popular tokens",
                    style = TextStyle(
                        color = secondaryColor
                    ),
                    fontSize = 15.sp,
                    fontFamily = textFont,
                    modifier = Modifier
                        .padding(start = padding12)
                )

                LazyColumn {
                    items(
                        popular,
                        key = { it.id }
                    ) {
                        ChooseToken(model = it, context = context)
                    }
                }
            }
        }
    }
}

@Composable
fun ChooseToken(
    model: TokenModel,
    context: Context
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
                val activation = Intent("change_swap_activation")
                activation.putExtra("change_swap_activation_to", false)
                context.sendBroadcast(activation)
                val intent = Intent("Choose_Token")
                intent.putExtra("Chosen_Token_id", model.id)
                context.sendBroadcast(intent)
            }
    ) {
        val (image, name, money, num, synonym) = createRefs()

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
                bottom.linkTo(synonym.top)
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
            modifier = Modifier.constrainAs(synonym) {
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

@Composable
fun SuggestedToken(
    imageId: Int,
    name: String,
    context: Context,
    id: Int
) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .clickable {
                val activation = Intent("change_swap_activation")
                activation.putExtra("change_swap_activation_to", false)
                context.sendBroadcast(activation)
                val intent = Intent("Choose_Token")
                intent.putExtra("Chosen_Token_id", id)
                context.sendBroadcast(intent)
            }
            .padding(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        ),
        shape = CircleShape
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .wrapContentSize()
        ) {

            Spacer(modifier = Modifier.width(5.dp))

            AsyncImage(
                model = imageId,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = name,
                style = TextStyle(
                    color = Color.White
                ),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.impact))
            )

            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSwap() {
    SwapScreen(
        textFont = FontFamily(Font(R.font.impact)),
        context = LocalContext.current,
        modelId = 3
    )
}