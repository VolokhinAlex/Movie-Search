package com.example.java.android1.movie_search.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.java.android1.movie_search.BuildConfig
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class ParseMoviesFromTMDB(
    private val listener: MovieDataLoadListener,
    private val language: String,
    private val category: String
) {

    /**
     *  The method to get a data from TMDB API.
     *  The method uses HttpsUrlConnection to connect the server and receive a data
     */

    fun parseData() {
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/${category}?api_key=${BuildConfig.MOVIE_API_KEY}&language=${language}&page=1")
            val handler = Handler(Looper.getMainLooper())

            Thread {
                var urlConnection: HttpsURLConnection? = null
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.readTimeout = 10000
                    urlConnection.requestMethod = "GET"
                    val result = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val movieDataDTO: CategoryMoviesTMDB =
                        Gson().fromJson(getLines(result), CategoryMoviesTMDB::class.java)
                    handler.post {
                        listener.onLoaded(movieDataDTO.results)
                    }
                } catch (e: Exception) {
                    listener.onFailed(e)
                } finally {
                    urlConnection?.disconnect()
                }
            }.start()

        } catch (e: MalformedURLException) {
            listener.onFailed(e)
        }
    }

    private fun getLines(reader: BufferedReader): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            reader.lines().collect(Collectors.joining("\n"))
        } else {
            ""
        }
    }

    /**
     * Listener to transfer the data from ParseMoviesFromTMDB.class
     */

    interface MovieDataLoadListener {
        fun onLoaded(movieData: List<MovieDataTMDB>)
        fun onFailed(e: Throwable)
    }

}