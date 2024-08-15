package com.example.worldcinema

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LaunchScreen (){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background( color = colorResource(id = R.color.fon)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "WorldCinema",
            color = colorResource(id = R.color.pink),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top=160.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_video_camera),
            contentDescription = "camera",
            modifier = Modifier
                .padding(top=21.dp)
                .size(214.dp))
    }
}
