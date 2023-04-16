package com.example.exoplayersample.screen.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.exoplayersample.data.VideoItem


@Composable
fun VideoCard(isCurrentPlaying: Boolean, videoItem: VideoItem, onClick: () -> Unit) {

    val color = if (isCurrentPlaying) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        Color.White
    }

    OutlinedCard(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),

        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Row(
            Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .clickable { onClick.invoke() },
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                Modifier
                    .padding(8.dp), contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = videoItem.placeHolder,
                    contentDescription = "Thumbnail",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(56.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                Modifier
                    .padding(horizontal = 14.dp)
                    .weight(7f)
            ) {
                Text(videoItem.name, fontWeight = FontWeight.SemiBold, fontSize = 17.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Cool Description",
                    maxLines = 1,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            if (isCurrentPlaying) {
                Column(Modifier.weight(3f)) {
                    Text(
                        text = "Now Playing...",
                        color = Color.Red,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
                            .padding(8.dp),
                    )
                }
            }
        }
    }
}