package com.example.filmy

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.filmy.ui.theme.FilmyTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Serializable
import java.sql.Ref


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) { MoviesList(loadMoviesFromJson()) }
            }
        }
    }

    private fun loadMoviesFromJson(): List<Movie> {
        val jsonFile = resources.openRawResource(R.raw.movies) // Assuming you've placed your JSON file in the 'res/raw' folder
        val jsonReader = BufferedReader(InputStreamReader(jsonFile))
        return Gson().fromJson(jsonReader, Array<Movie>::class.java).toList()
    }

    @SuppressLint("DiscouragedApi")
    @Composable
    private fun MovieCard(movie: Movie, modifier: Modifier = Modifier) {
        val mContext = LocalContext.current
        Row(modifier = Modifier
            .padding(all = 8.dp)
            .clickable {
                val intent = Intent(mContext, DetailsActivity::class.java)
                intent.putExtra("movie", movie)
                mContext.startActivity(intent)
            }) {
            Image(
                painter = painterResource(resources.getIdentifier(movie.image_name, "drawable", packageName)),
                contentDescription = "Photo from ${movie.title}",
                modifier = Modifier
                    .size(100.dp)
                    //.clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text = movie.title,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
                // Add a vertical space between the author and message texts
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = movie.author,
                    fontSize = 20.sp)
            }
        }
    }

    @Composable
    private fun MoviesList(movies: List<Movie>) {
        LazyColumn {
            items(movies) { movie ->
                MovieCard(movie)
            }
        }
    }
}

data class Reference(
    val description: String,
    val url: String,
    val image_name: String
) : Serializable

data class Movie(
    val id: Int,
    val title: String,
    val author: String,
    val image_name: String,
    val description: String,
    val trailer_uri: String,
    val scenes: List<Reference>,
    val actors: List<Reference>
) : Serializable
