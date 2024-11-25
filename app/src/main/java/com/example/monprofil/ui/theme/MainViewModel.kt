package com.example.monprofil.ui.theme

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monprofil.TmdbAPI




import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {

    val movies = MutableStateFlow<List<Movie>>(listOf()) // Liste d'objets Movie
    val series = MutableStateFlow<List<Series>>(listOf()) // Liste d'objets Series
    val acteurs = MutableStateFlow<List<Acteurs>>(listOf()) // Liste d'objets Acteurs


    val movies_select = MutableStateFlow<DetailedMovie>(DetailedMovie())
    val series_select = MutableStateFlow<DetailedSerie>(DetailedSerie())


    val movieCast = MutableStateFlow<List<Cast>>(emptyList()) // Liste vide de Cast
    val seriesCast = MutableStateFlow<List<CastSerie>>(emptyList()) // Liste vide de CastSerie



    val apikey = "474915450c136f48794281389330d269"


    // Intercepteur de logs
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Client OkHttp
    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Service Retrofit pour les films
    val Service = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()
        .create(TmdbAPI::class.java)



    // Initialisation des appels
    init {
        getMovies()
        getTrendingSeries()
        getActeurs()

    }

    // Récupérer les films tendances
    private fun getMovies() {
        viewModelScope.launch {
            try {
                Log.v("MainViewModel", "Récupération des films tendances")
                val trendingMovies = Service.getTrendingMovies(apikey)
                movies.value = trendingMovies.results
            } catch (e: Exception) {
                Log.e("MainViewModel", "Erreur lors de la récupération des films: ${e.message}")
            }
        }
    }

    // Recherche de films par mot-clé
    fun searchMovies(motcle: String) {
        viewModelScope.launch {
            try {
                val searchResult = Service.getFilmsParMotCle(apikey, motcle)
                movies.value = searchResult.results
            } catch (e: Exception) {
                Log.e("MainViewModel", "Erreur lors de la recherche de films: ${e.message}")
            }
        }
    }

    // Récupérer les séries tendances
    fun getTrendingSeries() {
        viewModelScope.launch {
            try {
                Log.v("MainViewModel", "Récupération des séries tendances")
                val trendingSeries = Service.getDiscoverTV(apikey)
                series.value = trendingSeries.results
            } catch (e: Exception) {
                Log.e("MainViewModel", "Erreur lors de la récupération des séries: ${e.message}")
            }
        }
    }

    // Recherche de séries par mot-clé
    fun searchSeries(motcle: String) {
        viewModelScope.launch {
            try {
                val searchResult = Service.getSeriesParMotCle(apikey, motcle)
                series.value = searchResult.results
            } catch (e: Exception) {
                Log.e("MainViewModel", "Erreur lors de la recherche de séries: ${e.message}")
            }
        }
    }




    // Récupérer les acteurs tendances
     fun getActeurs() {
        viewModelScope.launch {
            try {
                Log.v("MainViewModel", "Récupération des acteurs tendances")
                val trendingActeurs = Service.getActeur(apikey)
                acteurs.value = trendingActeurs.results
            } catch (e: Exception) {
                Log.e("MainViewModel", "Erreur lors de la récupération des acteurs: ${e.message}")
            }
        }
    }


    fun searchActeurs(motcle: String) {
        viewModelScope.launch {
            try {
                val searchResult = Service.getActeurParMotCle(apikey, motcle)
                acteurs.value = searchResult.results
            } catch (e: Exception) {
                Log.e("MainViewModel", "Erreur lors de la recherche d'acteurs: ${e.message}")
            }
        }
    }


    //Pour selection séries et films
    fun selectedMovies(id : Int){
        viewModelScope.launch {
            movies_select.value = Service.selectOfMovie(id,apikey)
        }
    }
    fun selectedSeries(id : Int){
        viewModelScope.launch {
            series_select.value = Service.selectOfSerie(id,apikey)
        }
    }

    // Pour afficher les acteurs d'une série ou d'un film select
    fun getActeurMovie(id: Int) {
        viewModelScope.launch {
            try {
                val result = Service.acteurfilm(id, apikey)
                movieCast.value = result.cast
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun getActeurSeries(id: Int) {
        viewModelScope.launch {
            try {
                val serieact = Service.acteurseries(id, apikey)
                seriesCast.value = serieact.cast
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



    private val _collections = MutableStateFlow<List<collection>>(emptyList())
    val collections: StateFlow<List<collection>> get() = _collections

    fun fetchCollectionsByKeyword(keyword: String = "horror") {
        viewModelScope.launch {
            try {
                val response = Service.getCollectionsByKeyword(apikey , keyword = keyword)
                _collections.value = response.results
            } catch (e: Exception) {
                // Handle errors here (e.g., log or show a message)
                _collections.value = emptyList()
            }
        }
    }






}








