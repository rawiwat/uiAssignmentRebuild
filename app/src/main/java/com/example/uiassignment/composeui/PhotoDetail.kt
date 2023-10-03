package com.example.uiassignment.composeui

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.uiassignment.FakeDatabase
import com.example.uiassignment.R
import com.example.uiassignment.Trait
import com.example.uiassignment.getRandomName
import com.example.uiassignment.toModel
import com.example.uiassignment.traitsValueGenerator
import kotlin.random.Random

@Composable
fun PhotoDetail(
    modelId:Int,
    photoId:Int,
    navController:NavController
) {
    val model by remember {
        mutableStateOf(FakeDatabase().getModelFromID(modelId).toModel())
    }
    val primaryColor = colorResource(id = R.color.teal_200)
    val secondaryColor = colorResource(id = R.color.teal_700)
    val textFont = FontFamily(Font(R.font.impact))
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val name by rememberSaveable {
        mutableStateOf(getRandomName())
    }

    val enemyName by rememberSaveable {
        mutableStateOf(getRandomName())
    }

    val tag by rememberSaveable {
        mutableStateOf("#${Random.nextInt(0,9999)}")
    }

    val sideScreenPadding = 12.dp

    val betweenComponentPadding = 8.dp

    val traits = Trait.values().toList()

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(color = Color.Black)
            ) {
                val (threeDots,arrow) = createRefs()

                Image(
                    painter = painterResource(id = R.drawable.point_back),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { navController.navigate("Swap/${model.id}") }
                        .size(20.dp)
                        .constrainAs(arrow) {
                            start.linkTo(parent.start, margin = 15.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )

                Image(
                    painter = painterResource(id = R.drawable.three_dots),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .constrainAs(threeDots) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end, margin = 15.dp)
                        }
                )
            }
        }
    ) { it ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    state = scrollState
                )
        ) {
            Column(
                modifier = Modifier
                    .background(color = Color.Black)
                    .padding(it)
                    .fillMaxSize()
                    .height(1000.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = photoId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(screenWidth.dp)
                            .padding(
                                start = sideScreenPadding,
                                end = sideScreenPadding
                            )
                    )
                }

                Text(
                    text = "$name $tag",
                    fontSize = 18.sp,
                    fontFamily = textFont,
                    style = TextStyle(
                        color = primaryColor
                    ),
                    modifier = Modifier
                        .padding(start = sideScreenPadding)
                )

                Spacer(modifier = Modifier.height(betweenComponentPadding))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(
                            start = sideScreenPadding,
                            end = sideScreenPadding
                        )
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize(),
                        color = Color.DarkGray
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Spacer(modifier = Modifier.width(8.dp))

                            Image(
                                painter = painterResource(id = R.drawable.token_unity),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(35.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Fit
                            )

                            Column {
                                Text(
                                    text = name,
                                    fontSize = 18.sp,
                                    fontFamily = textFont,
                                    style = TextStyle(
                                        color = primaryColor
                                    ),
                                    modifier = Modifier
                                        .padding(start = sideScreenPadding)
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        text = "Floor:",
                                        fontSize = 18.sp,
                                        fontFamily = textFont,
                                        style = TextStyle(
                                            color = secondaryColor
                                        ),
                                        modifier = Modifier
                                            .padding(start = sideScreenPadding)
                                    )

                                    Image(
                                        painter = painterResource(id = R.drawable.crystal_icon),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(25.dp),
                                        contentScale = ContentScale.FillBounds
                                    )

                                    Text(
                                        text = "0.420",
                                        fontSize = 18.sp,
                                        fontFamily = textFont,
                                        style = TextStyle(
                                            color = secondaryColor
                                        ),
                                        modifier = Modifier
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.point_back_bright),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(25.dp)
                                    .rotate(180f)
                            )

                            Spacer(modifier = Modifier.width(betweenComponentPadding))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(betweenComponentPadding))

                Text(
                    text = "Arch-Nemesis of $enemyName their battle shook the metaverse to it's core",
                    fontSize = 18.sp,
                    fontFamily = textFont,
                    style = TextStyle(
                        color = primaryColor
                    ),
                    modifier = Modifier
                        .padding(
                            start = sideScreenPadding,
                            end = sideScreenPadding
                        )
                )

                Spacer(modifier = Modifier.height(betweenComponentPadding))

                Surface(
                    color = Color.Black,
                    modifier = Modifier.height(30.dp)
                ) {
                    Text(
                        text = "Owned by",
                        fontSize = 18.sp,
                        fontFamily = textFont,
                        style = TextStyle(
                            color = primaryColor
                        ),
                        modifier = Modifier
                            .padding(start = sideScreenPadding)
                    )

                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
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
                            fontSize = 18.sp,
                            fontFamily = textFont,
                            modifier = Modifier.padding(end = sideScreenPadding),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(betweenComponentPadding))

                Text(
                    text = "Traits",
                    style = TextStyle(
                        color = primaryColor
                    ),
                    fontSize = 18.sp,
                    fontFamily = textFont,
                    modifier = Modifier.padding(start = sideScreenPadding),
                )

                Spacer(modifier = Modifier.height(betweenComponentPadding))

                LazyRow {
                    items(
                        traits,
                        key = { it.type }
                    ) {
                        TraitBox(trait = it)
                    }
                }
            }
        }
    }
}

@Composable
fun TraitBox(trait: Trait) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = trait.type,
                style = TextStyle(
                    color = colorResource(id = R.color.teal_700)
                ),
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.impact)),
            )

            Text(
                text = traitsValueGenerator(trait),
                style = TextStyle(
                    color = colorResource(id = R.color.teal_200)
                ),
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.impact)),
            )
        }
    }
}