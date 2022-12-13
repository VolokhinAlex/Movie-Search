package com.example.java.android1.movie_search.view.details

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.java.android1.movie_search.BuildConfig
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MovieDataLoad(private val listener: MovieDataLoadListener, val lang: String, val movieID: Int) {

    private val TAG = "TAG_${MovieDataLoad::class.java}"

    @RequiresApi(Build.VERSION_CODES.N)
    fun parseData() {
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/${movieID}?api_key=${BuildConfig.MOVIE_API_KEY}&language=${lang}&append_to_response=credits")
            val handler = Handler(Looper.getMainLooper())

            Thread {
                var urlConnection: HttpsURLConnection? = null
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 10000
                    val result = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val movieDataDTO: MovieDataTMDB =
                        Gson().fromJson(getLines(result), MovieDataTMDB::class.java)
                    handler.post {
                        listener.onLoaded(movieDataDTO)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "", e)
                    listener.onFailed(e)
                } finally {
                    urlConnection?.disconnect()
                }
            }.start()

        } catch (e: MalformedURLException) {
            Log.e(TAG, "", e)
            listener.onFailed(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

}

interface MovieDataLoadListener {
    fun onLoaded(movieData: MovieDataTMDB)
    fun onFailed(e: Throwable)
}