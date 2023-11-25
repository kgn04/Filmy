package com.example.filmy

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.filmy.ui.theme.FilmyTheme

class DetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val actor = Reference("Grzesiu", "http//sadsadsd.peel",  R.drawable.ic_launcher_background)
                    val scene = Reference("scena taka", "http//sadsadsd.peel", R.drawable.ic_launcher_background)
                    val movie = Movie("Android", "juzek", R.drawable.ic_launcher_background, "Lorem ipsum lalala askjndkasfnlajsf",
                        listOf(scene, scene, scene, scene, scene), listOf(actor, actor, actor, actor, actor, actor, actor, actor)
                    )
                    DetailsGrid(movie)
                }
            }
        }
    }
}

@Composable
fun Description(movie: Movie) {
    Row(modifier = Modifier
        .padding(all = 8.dp))  {
        Image(painter = painterResource(movie.image),
            contentDescription = "Photo from ${movie.title}",
            modifier = Modifier
                // Set image size to 40 dp
                .size(100.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = movie.description)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContentContainer(name: String, content: List<Reference>, width_fraction: Float) {
    Column(modifier = Modifier
        .fillMaxWidth(width_fraction)) {
        Text(text = name)

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(3)) {
            items(content) { reference ->
                Image(painter = painterResource(reference.image),
                    contentDescription = "Photo  from",
                    modifier = Modifier
                        // Set image size to 40 dp
                            .size(80.dp)
                            .padding(8.dp)
                    )
                }
        }
    }
}

@Composable
fun DetailsGrid(movie: Movie, modifier: Modifier = Modifier) {
    // Main Grid
    Column(modifier = Modifier
        .padding(all = 8.dp)) {
        // Image & description grid
        Description(movie = movie)
        //scenes & actors
        Row(modifier = Modifier
            .padding(all = 8.dp))  {
            // Scenes grid
            ContentContainer(name = "SCENY", content = movie.scenes, width_fraction = 0.5f)
            // Actors grid
            ContentContainer(name = "AKTORZY", content = movie.actors, width_fraction = 1.0f)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsGridPreview() {
    FilmyTheme {
        val actor = Reference("Grzesiu", "http//sadsadsd.peel", R.drawable.ic_launcher_background)
        val scene = Reference("scena taka", "http//sadsadsd.peel", R.drawable.ic_launcher_background)
        val movie = Movie("Android", "juzek", R.drawable.ic_launcher_background, "Lorem ipsum lalala askjndkasfnlajsf",
            listOf(scene, scene, scene, scene, scene), listOf(actor, actor, actor, actor, actor, actor, actor, actor)
        )
        DetailsGrid(movie)
    }
}
