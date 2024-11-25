package com.example.monprofil.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.monprofil.ActeursDestination
import com.example.monprofil.FilmDetailDestination
import com.example.monprofil.FilmsDestination
import com.example.monprofil.ProfilDestination
import com.example.monprofil.SeriesDestination

@Composable
fun ActeurScreen(navController: NavController, viewModel: MainViewModel) {
    var selectedTab by remember { mutableStateOf("acteur") } // Onglet sélectionné
    var searchText by remember { mutableStateOf(TextFieldValue("")) } // Texte de recherche
    val keyboardController = LocalSoftwareKeyboardController.current

    val acteurs by viewModel.acteurs.collectAsState() // Collecte des données

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color(0xFFADD8E6)) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Movie, contentDescription = "Film") },
                    label = { Text("Film") },
                    selected = selectedTab == "film",
                    onClick = {
                        selectedTab = "film"
                        navController.navigate(FilmsDestination())
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Tv, contentDescription = "Series") },
                    label = { Text("Series") },
                    selected = selectedTab == "series",
                    onClick = {
                        selectedTab = "series"
                        navController.navigate(SeriesDestination())
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Star, contentDescription = "Acteur") },
                    label = { Text("Acteur") },
                    selected = selectedTab == "acteur",
                    onClick = {
                        selectedTab = "acteur"
                        navController.navigate(ActeursDestination())
                    }
                )

            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("Rechercher acteurs...") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            viewModel.searchActeurs(searchText.text) // Déclenche la recherche
                            keyboardController?.hide() // Masque le clavier
                        }
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(acteurs) { acteur -> // Affiche la liste d'acteurs récupérée
                    ActeurCard(acteur = acteur)
                }
            }
        }
    }
}

