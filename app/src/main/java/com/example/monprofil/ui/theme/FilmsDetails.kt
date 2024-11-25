
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.monprofil.ui.theme.AccentYellow
import com.example.monprofil.ui.theme.DarkGray
import com.example.monprofil.ui.theme.DeepBlue
import com.example.monprofil.ui.theme.LightBlue
import com.example.monprofil.ui.theme.LightGray
import com.example.monprofil.ui.theme.MainViewModel


@Composable
fun ScreenFilmsDetails(
    navController: NavController,
    viewModel: MainViewModel,
    id: String
) {
    val filmSelected = viewModel.movies_select.collectAsState()
    val movieActeurs by viewModel.movieCast.collectAsState()

    LaunchedEffect(id) {
        val idInt = id.toIntOrNull()
        if (idInt != null) {
            viewModel.selectedMovies(idInt)
            viewModel.getActeurMovie(idInt)
        } else {
            navController.popBackStack()
        }
    }

    val isCompactScreen = LocalConfiguration.current.screenWidthDp < 600
    val contentPadding = if (isCompactScreen) 16.dp else 24.dp
    val fontSizeTitle = if (isCompactScreen) 20.sp else 28.sp
    val gridColumns = if (isCompactScreen) 2 else 4

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(contentPadding)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Titre
            item {
                Text(
                    text = filmSelected.value.original_title.orEmpty(),
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSizeTitle,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Image et grille des détails
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Image
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/" + filmSelected.value.backdrop_path,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .clip(RoundedCornerShape(16.dp))
                            .shadow(4.dp)
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                    )

                    // Grille des détails
                    LazyHorizontalGrid(
                        rows = GridCells.Fixed(gridColumns),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        horizontalArrangement = Arrangement.spacedBy(62.dp)
                    ) {
                        val detailsList = listOf(
                            "Langue: ${filmSelected.value.original_language}",
                            "Popularité: ${filmSelected.value.popularity}",
                            "Votes: ${filmSelected.value.vote_count}",
                            "Budget: ${filmSelected.value.budget}"
                        )
                        items(detailsList) { detail ->
                            FilmDetailRow(detail)
                        }
                    }
                }
            }

            // Synopsis
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Synopsis",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = filmSelected.value.overview.orEmpty(),
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
                    items(movieActeurs) { actor ->
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
                                text = actor.name.orEmpty(),
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
fun FilmDetailRow(detail: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(12.dp)
    ) {
        Text(
            text = detail,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
