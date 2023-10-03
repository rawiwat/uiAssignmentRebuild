package com.example.uiassignment.composeui

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.uiassignment.ArchiveScreenType
import com.example.uiassignment.Categorized
import com.example.uiassignment.CryptoActivity
import com.example.uiassignment.FakeDatabase
import com.example.uiassignment.Month
import com.example.uiassignment.R
import com.example.uiassignment.generateRecord
import com.example.uiassignment.getImageIds
import com.example.uiassignment.getRandomName
import com.example.uiassignment.toModel
import com.example.uiassignment.trimDouble2

@Composable
fun Archive(
    images: List<Int>,
    modelId: Int,
    textFont: FontFamily,
    navController: NavController,
    context: Context
) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val size = screenWidthDp / 2
    val model = remember(modelId) {
        FakeDatabase().getModelFromID(modelId).toModel()
    }
    val primaryColor = colorResource(id = R.color.teal_200)
    val secondaryColor = colorResource(id = R.color.teal_700)
    val standardPadding = 8.dp
    val splitedMoney = model.current.toString().split(".")

    var mode by rememberSaveable {
        mutableStateOf(ArchiveScreenType.ACTIVITY)
    }
    val record by rememberSaveable {
        mutableStateOf(generateRecord().map {
            Categorized(
                month = it.key.toString(),
                activities = it.value
            )
        })
    }

    val screen70 = screenWidthDp * 7 / 10
    val screen30 = screenWidthDp * 3 / 10
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(color = Color.Black)
                    .fillMaxWidth()
            ) {
                Card(
                    modifier = Modifier
                        .padding(standardPadding),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black
                    )
                ) {

                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = model.imageId,
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = model.name,
                            style = TextStyle(
                                color = secondaryColor
                            ),
                            fontSize = 15.sp,
                            fontFamily = textFont,
                            modifier = Modifier.padding(3.dp)
                        )

                        AsyncImage(
                            model = R.drawable.point_back,
                            contentDescription = null,
                            modifier = Modifier
                                .rotate(270f)
                                .size(16.dp)
                        )

                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }

                Row {

                    Spacer(modifier = Modifier.width(standardPadding))

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

                    Spacer(modifier = Modifier.width(standardPadding))

                    AsyncImage(
                        model = if(model.positiveGrowth) R.drawable.up else R.drawable.down,
                        contentDescription = null,
                        modifier = Modifier.size(6.dp)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = "$0.64 (${model.growthPercent}%)",
                        style = TextStyle(
                            color = secondaryColor
                        ),
                        fontFamily = textFont,
                        fontSize = 12.sp
                    )
                }

                Row (
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    LinkInGallery(
                        imageId = R.drawable.share_plane,
                        text = "Send",
                        modifier = Modifier
                            .padding(standardPadding)
                            .width((size - 30).dp)
                            .height(50.dp)
                    )

                    LinkInGallery(
                        imageId = R.drawable.qr_code_icon,
                        text = "Scan",
                        modifier = Modifier
                            .padding(standardPadding)
                            .width((size - 30).dp)
                            .height(50.dp)
                            .clickable {
                                navController.navigate("QR Code")
                            }
                    )
                }

                Row {

                    Spacer(modifier = Modifier.width(standardPadding))

                    Text(
                        text = "Tokens",
                        style = TextStyle(
                            color = if (mode == ArchiveScreenType.TOKEN)
                                primaryColor
                            else secondaryColor
                        ),
                        fontFamily = textFont,
                        fontSize = 15.sp,
                        modifier = Modifier.clickable {
                            mode = ArchiveScreenType.TOKEN
                        }
                    )

                    Spacer(modifier = Modifier.width(standardPadding))

                    Text(
                        text = "NFTs",
                        style = TextStyle(
                            color = if (mode == ArchiveScreenType.NFTS)
                                primaryColor
                            else secondaryColor
                        ),
                        fontFamily = textFont,
                        fontSize = 15.sp,
                        modifier = Modifier.clickable {
                            mode = ArchiveScreenType.NFTS
                        }
                    )

                    Spacer(modifier = Modifier.width(standardPadding))

                    Text(
                        text = "Activity",
                        style = TextStyle(
                            color = if (mode == ArchiveScreenType.ACTIVITY)
                                primaryColor
                            else secondaryColor
                        ),
                        fontFamily = textFont,
                        fontSize = 15.sp,
                        modifier = Modifier.clickable {
                            mode = ArchiveScreenType.ACTIVITY
                        }
                    )
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier
                        .width(screen70.dp)
                        .height(30.dp)
                        .padding(
                            start = 8.dp,
                            end = 8.dp
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
                        Spacer(modifier = Modifier.width(8.dp))

                        AsyncImage(
                            model = R.drawable.search_icon,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Search web3"
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .width(screen30.dp)
                        .height(30.dp)
                        .padding(
                            start = 8.dp,
                            end = 8.dp
                        )
                        .clickable {
                            navController.navigate("Swap/$modelId")
                        },
                    colors = CardDefaults.cardColors(
                        contentColor = Color.White,
                        containerColor = primaryColor,
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Swap",
                            fontSize = 20.sp,
                            fontFamily = textFont
                        )
                    }
                }
            }
        }
    ) { bodyPadding->
        Column(
            modifier = Modifier
                .background(color = Color.Black)
                .fillMaxSize()
                .padding(bodyPadding)
        ) {
            Spacer(modifier = Modifier.height(standardPadding))

            when(mode) {
                ArchiveScreenType.TOKEN -> {
                    LazyColumn {
                        items(20) {
                            RandomToken(context = context)
                        }
                    }
                }

                ArchiveScreenType.NFTS -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2)
                    ) {
                        items(
                            images,
                            key = { it }
                        ) {
                            PhotoInGallery(
                                imageId = it,
                                size = size,
                                navController = navController,
                                modelId = modelId
                            )
                        }
                    }
                }

                ArchiveScreenType.ACTIVITY ->
                    Records(
                        category = record,
                        imagesIds = images
                    )
            }
        }
    }

}

@Composable
fun PhotoInGallery(
    imageId:Int,
    size:Int,
    navController: NavController,
    modelId: Int
) {
    Card(
        modifier = Modifier
            .size(size.dp)
            .padding(5.dp)
    ) {
        val painter = rememberImagePainter(
            data = imageId,
            builder = {
                crossfade(500)
            }
        )

        val painterState = painter.state

        if (painterState == AsyncImagePainter.State.Loading(painter)) {
            CircularProgressIndicator(
                color = colorResource(id = R.color.teal_200)
            )
        } else {
            Image(
                painter = painter,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        navController.navigate("PhotoDetail/$modelId/$imageId")
                    },
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun LinkInGallery(
    imageId: Int,
    text: String,
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageId,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
                    .padding(2.dp)
            )

            Text(
                text = text,
                style = TextStyle(
                    color = Color.White
                ),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.impact)),
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
        }
    }
}

@Composable
fun MonthHeader(text: String) {
    Surface(
        color = Color.Black,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = Color.White
            ),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.impact)),
            modifier = Modifier.padding(start = 12.dp)
        )
    }

}

@Composable
fun ActivityUI(
    activity: CryptoActivity,
    imageId: Int
) {
    val primaryTextColor = colorResource(id = R.color.teal_200)
    val secondaryTextColor = colorResource(id = R.color.teal_700)
    val textFont = FontFamily(Font(R.font.impact))
    val topFontSize = 16.sp
    val bottomFontSize = 14.sp
    val activityTime = if (activity.time.date.month == Month.Today) {
        activity.time.hour.text
    } else {
        "${activity.time.date.month} ${activity.time.date.day}"
    }

    ConstraintLayout(
        modifier = Modifier
            .height(75.dp)
            .background(color = Color.Black)
            .fillMaxWidth()
    ) {
        val (image, type, time, detail) = createRefs()

        AsyncImage(
            model = imageId,
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
            text = activity.type,
            fontSize = topFontSize,
            style = TextStyle(
                color = primaryTextColor
            ),
            fontFamily = textFont,
            modifier = Modifier.constrainAs(type) {
                start.linkTo(image.end)
                top.linkTo(parent.top, margin = 5.dp)
                bottom.linkTo(detail.top)
            }
        )

        Text(
            text = activityTime,
            style = TextStyle(
                color = primaryTextColor
            ),
            fontFamily = textFont,
            fontSize = topFontSize,
            modifier = Modifier.constrainAs(time) {
                top.linkTo(parent.top)
                end.linkTo(parent.end, margin = 12.dp)
                bottom.linkTo(parent.bottom)
            }
        )

        Text(
            text = activity.detail,
            fontSize = bottomFontSize,
            style = TextStyle(
                color = secondaryTextColor
            ),
            fontFamily = textFont,
            modifier = Modifier.constrainAs(detail) {
                start.linkTo(image.end)
                bottom.linkTo(parent.bottom, margin = 5.dp)
                top.linkTo(type.bottom)
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Records(
    category: List<Categorized>,
    imagesIds: List<Int>
) {
    LazyColumn {
        category.forEach {
            stickyHeader {
                MonthHeader(text = it.month)
            }

            val sortedActivities: List<CryptoActivity> = if(it.month == "Today") {
                it.activities.sortedByDescending { it.time.hour.value }
            } else {
                it.activities.sortedByDescending { it.time.date.day }
            }

            items(
                sortedActivities,
                key = { it.id }
            ) { activity ->
                val imageId by rememberSaveable {
                    mutableIntStateOf(imagesIds.random())
                }
                ActivityUI(
                    activity = activity,
                    imageId = imageId
                )
            }
        }
    }
}

@Composable
fun RandomToken(
    context: Context
) {
    val primaryTextColor = colorResource(id = R.color.teal_200)
    val secondaryTextColor = colorResource(id = R.color.teal_700)
    val textFont = FontFamily(Font(R.font.impact))
    val topFontSize = 16.sp
    val bottomFontSize = 14.sp
    val imageId by remember { mutableIntStateOf(getImageIds(context = context).random()) }
    val tokenName by remember { mutableStateOf(getRandomName()) }
    val tokenDescription by remember { mutableStateOf(getRandomName()) }
    val tokenMoney by remember {
        mutableDoubleStateOf(trimDouble2(kotlin.random.Random.nextDouble(1.0,70.0)))
    }
    val tokenPercent by remember {
        mutableDoubleStateOf(trimDouble2(kotlin.random.Random.nextDouble(0.01,7.0)))
    }
    ConstraintLayout(
        modifier = Modifier
            .height(75.dp)
            .background(color = Color.Black)
            .fillMaxWidth()
    ) {
        val (image, name,money, growth, description, percent) = createRefs()

        Image(
            painter = painterResource(id = imageId),
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
            text = tokenName,
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
            text = "$$tokenMoney",
            style = TextStyle(
                color = primaryTextColor
            ),
            fontFamily = textFont,
            fontSize = topFontSize,
            modifier = Modifier.constrainAs(money) {
                top.linkTo(parent.top, margin = 5.dp)
                end.linkTo(parent.end, margin = 12.dp)
                bottom.linkTo(percent.top)
            }
        )

        Text(
            text = tokenDescription,
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

        Image(
            painter = painterResource(
                id = R.drawable.up),
            contentDescription = null,
            modifier = Modifier
                .size(8.dp)
                .constrainAs(growth) {
                    end.linkTo(percent.start, margin = 5.dp)
                    top.linkTo(percent.top)
                    bottom.linkTo(percent.bottom)
                },
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = "$tokenPercent%",
            style = TextStyle(
                color = secondaryTextColor
            ),
            fontFamily = textFont,
            fontSize = bottomFontSize,
            modifier = Modifier.constrainAs(percent) {
                end.linkTo(parent.end, margin = 12.dp)
                bottom.linkTo(parent.bottom, margin = 5.dp)
                top.linkTo(money.bottom)
            }
        )
    }
}

@Preview
@Composable
fun PreviewArchive() {
    val context = LocalContext.current
    Archive(
        images = getImageIds(context),
        modelId = 1,
        textFont = FontFamily(Font(R.font.impact)),
        navController = NavController(context),
        context = context
    )
}