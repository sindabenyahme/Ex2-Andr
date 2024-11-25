package com.example.monprofil.ui.theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import java.nio.file.WatchEvent




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActeurCard(acteur: Acteurs) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(), // Fill the width for better structure
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Increase shadow for depth
        shape = MaterialTheme.shapes.medium, // Rounded corners
        colors = CardDefaults.cardColors(containerColor = Color(0xFF4682B4 )) // Red background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp) // Increased padding for breathing room
        ) {
            // Movie Poster
            val imageUrl = "https://image.tmdb.org/t/p/w500${acteur.profile_path}"
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = acteur.name,
                modifier = Modifier
                    .size(180.dp) // Larger image size for better impact
                    .padding(bottom = 12.dp)
                    .clip(MaterialTheme.shapes.medium) // Rounded image corners
                    .border(2.dp, Color.White, MaterialTheme.shapes.medium) // White border around the image
            )

            // Movie Title and Release Date
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = acteur.name,
                    fontSize = 22.sp, // Slightly larger title
                    fontWeight = FontWeight.Bold,
                    color = Color.White, // Text color for better contrast
                    maxLines = 1, // Ensure title doesn't overflow
                    modifier = Modifier.padding(bottom = 4.dp) // Space between title and release date
                )

            }
        }
    }
}
