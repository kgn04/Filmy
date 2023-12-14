package com.example.filmy

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.ui.PlayerView
import com.example.filmy.ui.theme.FilmyTheme
import androidx.constraintlayout.compose.ConstraintLayout



class DetailsActivity : ComponentActivity() {


    @androidx.annotation.OptIn(UnstableApi::class) override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DetailsGrid(intent.getSerializableExtra("movie") as Movie)
                    //VideoPlayer(intent.getSerializableExtra("movie") as Movie)
                }
            }
        }
    }

    @SuppressLint("DiscouragedApi")
    @Composable
    private fun Description(movie: Movie) {
        Spacer(modifier = Modifier.height(50.dp))
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
        Spacer(modifier = Modifier.height(50.dp))
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

    @OptIn(ExperimentalAnimationApi::class)
    @UnstableApi @androidx.annotation.OptIn(UnstableApi::class) @Composable
    private fun DetailsGrid(movie: Movie, modifier: Modifier = Modifier) {
        // Main Grid
        Column(modifier = Modifier
            .padding(all = 8.dp)) {
            VideoPlayer(
                movie = movie,
                modifier = Modifier.height(250.dp)
            )
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

    @UnstableApi @ExperimentalAnimationApi
    @Composable
    fun VideoPlayer(
        modifier: Modifier = Modifier,
        movie: Movie) {
        val context = LocalContext.current
        val mediaItems = arrayListOf<MediaItem>()
        val videoUri = Uri.parse("android.resource://${packageName}/raw/${movie.trailer_uri}")

        // create MediaItem
            mediaItems.add(
                MediaItem.Builder()
                    .setUri(videoUri)
                    .setMediaId(movie.id.toString())
                    .setTag(movie)
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setDisplayTitle(movie.title)
                            .build()
                    )
                    .build()
            )

        val exoPlayer = remember {
            SimpleExoPlayer.Builder(context).build().apply {
                this.setMediaItems(mediaItems)
                this.prepare()
                this.playWhenReady = true
            }
        }

        ConstraintLayout(modifier = modifier) {
            val (title, videoPlayer) = createRefs()

            // video title
            Text(
                text = "Current Title",
                color = Color.White,
                modifier =
                Modifier.padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            // player view
            DisposableEffect(
                AndroidView(
                    modifier =
                    Modifier.testTag("VideoPlayer")
                        .constrainAs(videoPlayer) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                    factory = {

                        // exo player view for our video player
                        PlayerView(context).apply {
                            player = exoPlayer
                            layoutParams =
                                FrameLayout.LayoutParams(
                                    ViewGroup.LayoutParams
                                        .MATCH_PARENT,
                                    ViewGroup.LayoutParams
                                        .MATCH_PARENT
                                )
                        }
                    }
                )
            ) {
                onDispose {
                    // relase player when no longer needed
                    exoPlayer.release()
                }
            }
        }
    }
}
