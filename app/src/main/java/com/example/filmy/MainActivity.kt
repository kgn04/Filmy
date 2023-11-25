package com.example.filmy

import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
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
import java.sql.Ref


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val actor = Reference("Grzesiu", "http//sadsadsd.peel", R.drawable.ic_launcher_background)
                    val scene = Reference("scena taka", "http//sadsadsd.peel", R.drawable.ic_launcher_background)
                    val movie = Movie("Android", "juzek", R.drawable.ic_launcher_background, "Lorem ipsum lalala askjndkasfnlajsf",
                        listOf(scene, scene, scene, scene, scene), listOf(actor, actor, actor, actor, actor, actor, actor, actor)
                    )
                    MovieCard(movie)
                }
            }
        }
    }
}

data class Reference(val description: String, val link: String, val image: Int)
data class Movie(val title: String, val author: String, val image: Int, val description: String,
                 val scenes: List<Reference>, val actors: List<Reference>)


@Composable
fun MovieCard(movie: Movie, modifier: Modifier = Modifier) {
    val mContext = LocalContext.current
    Row(modifier = Modifier
        .padding(all = 8.dp)
        .clickable {
            mContext.startActivity(Intent(mContext, DetailsActivity::class.java))
        }) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "Photo from ${movie.title}",
            modifier = Modifier
                // Set image size to 40 dp
                .size(100.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
        )

        // Add a horizontal space between the image and the column
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


@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun MovieCardPreview() {
    FilmyTheme {
        val actor = Reference("Grzesiu", "http//sadsadsd.peel", R.drawable.ic_launcher_background)
        val scene = Reference("scena taka", "http//sadsadsd.peel", R.drawable.ic_launcher_background)
        val movie = Movie("Android", "juzek", R.drawable.ic_launcher_background, "Lorem ipsum lalala askjndkasfnlajsf",
            listOf(scene, scene, scene, scene, scene), listOf(actor, actor, actor, actor, actor, actor, actor, actor)
        )
        MovieCard(movie)
    }
}

@Composable
fun MoviesList(movies: List<Movie>) {
    LazyColumn {
        items(movies) { movie ->
            MovieCard(movie)
        }
    }
}

@Preview
@Composable
fun PreviewMovieList() {
    FilmyTheme {
        val actor = Reference("Grzesiu", "http//sadsadsd.peel",  R.drawable.ic_launcher_background)
        val scene = Reference("scena taka", "http//sadsadsd.peel", R.drawable.ic_launcher_background)
        val movie = Movie("Android", "juzek", R.drawable.ic_launcher_background, "Lorem ipsum lalala askjndkasfnlajsf",
            listOf(scene, scene, scene, scene, scene), listOf(actor, actor, actor, actor, actor, actor, actor, actor)
        )
        MoviesList(listOf(movie, movie))
    }
}
