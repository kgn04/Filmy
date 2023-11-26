package com.example.filmy

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DetailsGrid(intent.getSerializableExtra("movie") as Movie)
                }
            }
        }
    }

    @SuppressLint("DiscouragedApi")
    @Composable
    private fun Description(movie: Movie) {
        Spacer(modifier = Modifier.height(100.dp))
        Row(modifier = Modifier
            .padding(all = 8.dp))  {
            Image(painter = painterResource(resources.getIdentifier(movie.image_name, "drawable", packageName)),
                contentDescription = "Photo from ${movie.title}",
                modifier = Modifier
                    .size(150.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = movie.description,
                textAlign = TextAlign.Justify,
                fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(100.dp))
    }


    @SuppressLint("DiscouragedApi")
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun ContentContainer(name: String, content: List<Reference>, width_fraction: Float) {
        Column(modifier = Modifier
            .fillMaxWidth(width_fraction)) {
            Text(text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
           )

            Spacer(modifier = Modifier.height(8.dp))

            val mContext = LocalContext.current
            LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(3)) {
                items(content) { reference ->
                    Image(painter = painterResource(resources.getIdentifier(reference.image_name, "drawable", packageName)),
                        contentDescription = "Photo  from",
                        modifier = Modifier
                            // Set image size to 40 dp
                            .size(80.dp)
                            .padding(2.dp)
                            .clickable {
                                mContext.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(reference.url)
                                    )
                                )
                            }
                    )
                }
            }
        }
    }

    @Composable
    private fun DetailsGrid(movie: Movie, modifier: Modifier = Modifier) {
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
}
