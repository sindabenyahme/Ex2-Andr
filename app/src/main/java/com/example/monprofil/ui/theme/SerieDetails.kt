package com.example.monprofil.ui.theme


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

import androidx.compose.material3.Text

import androidx.navigation.NavController


import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


@Composable
fun ScreenSerieDetails(
    navController: NavController,
    viewModel: MainViewModel,
    id: String,
) {
    val seriesSelected = viewModel.series_select.collectAsState()
    val seriesActeurs by viewModel.seriesCast.collectAsState()

    LaunchedEffect(id) {
        val idInt = id.toIntOrNull()
        if (idInt != null) {
            viewModel.selectedSeries(idInt)
            viewModel.getActeurSeries(idInt)
        } else {
            navController.popBackStack()
        }
    }

    val isCompactScreen = LocalConfiguration.current.screenWidthDp < 600
    val contentPadding = if (isCompactScreen) 16.dp else 32.dp
    val fontSizeTitle = if (isCompactScreen) 24.sp else 32.sp
    val gridColumns = if (isCompactScreen) 2 else 4

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(contentPadding)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Titre de la série
            item {
                Text(
                    text = seriesSelected.value.name ?: "Unknown Title",
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSizeTitle,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Image et grille des détails
            item {
                if (isCompactScreen) {
                    // Affichage en colonne pour écrans compacts
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w500/" + seriesSelected.value.backdrop_path,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f)
                                .clip(RoundedCornerShape(8.dp))
                                .shadow(4.dp)
                                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))

                        )

                        LazyHorizontalGrid(
                            rows = GridCells.Fixed(gridColumns),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            val detailsList = listOf(
                                "LANGUE: ${seriesSelected.value.original_language}",
                                "POPULARITÉ: ${seriesSelected.value.popularity}",
                                "VOTE: ${seriesSelected.value.vote_count}",
                                "SAISONS: ${seriesSelected.value.number_of_seasons}"
                            )
                            items(detailsList) { detail ->
                                SerieDetailRow(detail = detail)
                            }
                        }
                    }
                } else {
                    // Affichage côte à côte pour écrans larges
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w300/" + seriesSelected.value.backdrop_path,
                            contentDescription = null,
                            modifier = Modifier
                                .width(200.dp)
                                .aspectRatio(4f / 3f)
                                .clip(RoundedCornerShape(10.dp))
                                .border(5.dp, PurpleGrey40, RoundedCornerShape(10.dp))
                        )

                        Column(
                            modifier = Modifier
                                .weight(2f)
                                .padding(2.dp),
                            verticalArrangement = Arrangement.spacedBy(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            val detailsList = listOf(
                                "LANGUE: ${seriesSelected.value.original_language}",
                                "POPULARITÉ: ${seriesSelected.value.popularity}",
                                "VOTE: ${seriesSelected.value.vote_count}",
                                "SAISONS: ${seriesSelected.value.number_of_seasons}"
                            )
                            detailsList.forEach { detail ->
                                Text(
                                    text = detail,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = PurpleGrey80,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(PurpleGrey40)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            // Synopsis
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Synopsis",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = seriesSelected.value.overview ?: "No synopsis available.",
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Acteurs principaux
            item {
                Text(
                    text = "Acteurs principaux",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(seriesActeurs) { actor ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.width(100.dp)
                        ) {
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w200${actor.profile_path}",
                                contentDescription = actor.name,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .shadow(4.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                            )
                            Text(
                                text = actor.name,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 4.dp),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SerieDetailRow(detail: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(10.dp)
    ) {
        Text(
            text = detail,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}