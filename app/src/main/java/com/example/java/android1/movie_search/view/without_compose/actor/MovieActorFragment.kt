package com.example.java.android1.movie_search.view.without_compose.actor

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.compose.SubcomposeAsyncImage
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.app.MovieActorsAppState
import com.example.java.android1.movie_search.model.ActorDTO
import com.example.java.android1.movie_search.model.CastDTO
import com.example.java.android1.movie_search.repository.MovieActorRepositoryImpl
import com.example.java.android1.movie_search.repository.RemoteDataSource
import com.example.java.android1.movie_search.view.compose.widgets.ErrorMessage
import com.example.java.android1.movie_search.view.compose.widgets.Loader
import com.example.java.android1.movie_search.view.compose.widgets.MapView
import com.example.java.android1.movie_search.viewmodel.MovieActorViewModel
import com.example.java.android1.movie_search.viewmodel.MovieActorViewModelFactory
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

class MovieActorFragment : Fragment() {
    private var castDTO: CastDTO? = null
    private val movieActorViewModel: MovieActorViewModel by lazy {
        ViewModelProvider(
            this,
            MovieActorViewModelFactory(MovieActorRepositoryImpl(RemoteDataSource()))
        )[MovieActorViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            castDTO = it.getParcelable(ARG_ACTOR_MOVIE_DATA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            val movieActorState = movieActorViewModel.movieActorLiveData.observeAsState()
            movieActorState.value?.let { RenderData(it) }
            castDTO?.let { castDto ->
                LaunchedEffect(true) {
                    castDto.id?.let { actorId ->
                        movieActorViewModel.getMovieActorData(
                            actorId,
                            "ru-RU"
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val ARG_ACTOR_MOVIE_DATA = "Actor Movie Data"

        @JvmStatic
        fun newInstance(bundle: Bundle) =
            MovieActorFragment().apply {
                arguments = bundle
            }
    }
}

@Composable
private fun MovieActorScreen(actorDTO: ActorDTO) {
    val context = LocalContext.current
    Configuration.getInstance().load(
        context, PreferenceManager.getDefaultSharedPreferences(
            context
        )
    )
    val geocoder = Geocoder(context)
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "${actorDTO.name}", modifier = Modifier.padding(10.dp), fontSize = 20.sp)
        SubcomposeAsyncImage(
            model = "https://image.tmdb.org/t/p/w500${actorDTO.profile_path}",
            loading = {
                Loader()
            },
            contentDescription = "movie_poster",
            modifier = Modifier
                .clip(CircleShape)
                .size(60.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "Place of Birth: ${actorDTO.place_of_birth}",
            modifier = Modifier.padding(10.dp),
            fontSize = 20.sp
        )
        actorDTO.place_of_birth?.let { GetActorCoordinates(it, geocoder) }
    }
}

@Composable
private fun GetActorCoordinates(location: String, geocoder: Geocoder) {
    val actorAddress = remember { mutableStateOf(emptyList<Address>()) }
    LaunchedEffect(Unit) {
        withContext(IO) {
            val address = geocoder.getFromLocationName(location, 10)
            if (address?.isNotEmpty() == true) {
                actorAddress.value = address
            }
        }
    }
    ShowCityOfActorBorn(actorAddress.value.toMutableList())
}

@Composable
private fun RenderData(movieActorsAppState: MovieActorsAppState) {
    when (movieActorsAppState) {
        is MovieActorsAppState.Error -> movieActorsAppState.error.message?.let { ErrorMessage(message = it) {} }
        MovieActorsAppState.Loading -> Loader()
        is MovieActorsAppState.Success -> {
            val actorData = movieActorsAppState.data
            MovieActorScreen(actorData)
        }
    }
}

@Composable
private fun ShowCityOfActorBorn(address: MutableList<Address>) {
    if (address.isEmpty()) return
    val context = LocalContext.current
    MapView(onLoad = { mapView ->
        val marker = Marker(mapView)

        mapView.setTileSource(TileSourceFactory.MAPNIK)
        val mapController = mapView.controller
        mapController.setZoom(13.0)
        val startPoint = GeoPoint(address[0].latitude, address[0].longitude)
        mapController.animateTo(startPoint)

        marker.position = startPoint
        marker.icon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_location_on_24)
        marker.title = "${address[0].featureName}, ${address[0].countryName}"
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
        mapView.overlays.add(marker)
        mapView.invalidate()
    })
}
