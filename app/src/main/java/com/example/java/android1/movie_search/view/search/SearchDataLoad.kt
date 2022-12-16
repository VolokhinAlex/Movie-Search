package com.example.java.android1.movie_search.view.search

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.java.android1.movie_search.BuildConfig
import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val INTENT_FILTER = "DETAILS INTENT FILTER"
const val LOAD_RESULT_EXTRA = "LOAD RESULT"
const val RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
const val TITLE_EXTRA = "title"
const val DATA_EXTRA = "data"

class SearchDataLoad(
    name: String = "SearchLoadData"
) : IntentService(name) {

    private val TAG = SearchDataLoad::class.java.toString()
    private val broadcastIntent = Intent(INTENT_FILTER)

    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            val title = intent.getStringExtra(TITLE_EXTRA)
            if (title?.isEmpty() == true) {
                onEmptyIntent()
            } else {
                title?.let { movieDataLoad(it) }
            }
        }
    }

    private fun movieDataLoad(movieTitle: String) {
        try {
            val url =
                URL("https://api.themoviedb.org/3/search/movie?api_key=${BuildConfig.MOVIE_API_KEY}&language=ru-RU&query=${movieTitle}")
            var urlConnection: HttpsURLConnection? = null

            try {
                urlConnection = url.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.readTimeout = 10000

                val result = BufferedReader(InputStreamReader(urlConnection.inputStream))

                val data: CategoryMoviesTMDB = Gson().fromJson(getLines(result), CategoryMoviesTMDB::class.java)
                onResponse(data.results)
            } catch (error: Exception) {
                Log.e(TAG, "Failed to get data: ", error)
            } finally {
                urlConnection?.disconnect()
            }

        } catch (error: MalformedURLException) {
            Log.e(TAG, "Failed to parse url: ", error)
        }
    }

    private fun getLines(reader: BufferedReader) : String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            reader.lines().collect(Collectors.joining("\n"))
        } else {
            ""
        }
    }

    private fun onResponse(data: List<MovieDataTMDB>) {
        if (data.isEmpty()) {
            onEmptyResponse()
        } else {
            onSuccessResponse(data as ArrayList<MovieDataTMDB>)
        }
    }

    private fun onSuccessResponse(data: ArrayList<MovieDataTMDB>) {
        putLoadResult(RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putParcelableArrayListExtra(DATA_EXTRA, data)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        putLoadResult(RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadResult(RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(LOAD_RESULT_EXTRA, result)
    }

}