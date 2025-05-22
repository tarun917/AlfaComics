package com.alfacomics.presentation.ui.screens.home

import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.MotionDummyData
import com.alfacomics.data.repository.MotionEpisode
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@Composable
fun EpisodePlayerScreen(
    navController: NavHostController,
    motionComicId: Int,
    initialEpisodeId: Int
) {
    val motionComic = MotionDummyData.getMotionComicById(motionComicId)
    val episodes = motionComic?.episodes ?: emptyList()
    val context = LocalContext.current

    // State for the currently playing episode
    var currentEpisodeIndex by remember { mutableStateOf(episodes.indexOfFirst { it.id == initialEpisodeId }.coerceAtLeast(0)) }
    var currentEpisode by remember { mutableStateOf(episodes.getOrNull(currentEpisodeIndex)) }

    // State for controls
    var showNextButton by remember { mutableStateOf(false) }
    var showControls by remember { mutableStateOf(true) }
    var isPlaying by remember { mutableStateOf(true) }
    var videoPosition by remember { mutableLongStateOf(0L) }
    var videoDuration by remember { mutableLongStateOf(0L) }
    var isBuffering by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var bufferingTimeout by remember { mutableStateOf(false) }

    // Coroutine scope for auto-hiding controls and buffering timeout
    val coroutineScope = rememberCoroutineScope()

    // Function to auto-hide controls after a delay
    fun autoHideControls() {
        coroutineScope.launch {
            delay(2000) // Hide controls after 2 seconds
            showControls = false
        }
    }

    // Function to format time in mm:ss format
    fun formatTime(millis: Long): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    // ExoPlayer setup
    val exoPlayer: ExoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            playWhenReady = true
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_BUFFERING -> {
                            isBuffering = true
                            errorMessage = null
                            // Start buffering timeout
                            coroutineScope.launch {
                                delay(10000) // 10 seconds timeout
                                if (isBuffering) {
                                    bufferingTimeout = true
                                    isBuffering = false
                                    errorMessage = "Failed to load video: Buffering timeout"
                                    showControls = true
                                    autoHideControls()
                                }
                            }
                        }
                        Player.STATE_READY -> {
                            isBuffering = false
                            bufferingTimeout = false
                            videoDuration = duration
                            if (isPlaying) {
                                play()
                            }
                        }
                        Player.STATE_ENDED -> {
                            isBuffering = false
                            showNextButton = true
                            // Auto-play next episode if available
                            if (currentEpisodeIndex < episodes.size - 1) {
                                coroutineScope.launch {
                                    delay(4000)
                                    if (showNextButton) { // Only auto-play if the user hasn't interacted
                                        currentEpisodeIndex++
                                        currentEpisode = episodes[currentEpisodeIndex]
                                        showNextButton = false
                                        showControls = true
                                        autoHideControls()
                                    }
                                }
                            }
                        }
                        Player.STATE_IDLE -> {
                            isBuffering = false
                        }
                    }
                    // Show controls when playback state changes
                    showControls = true
                    autoHideControls()
                }

                override fun onIsPlayingChanged(isCurrentlyPlaying: Boolean) {
                    isPlaying = isCurrentlyPlaying
                    showControls = true
                    autoHideControls()
                }

                override fun onPositionDiscontinuity(
                    oldPosition: Player.PositionInfo,
                    newPosition: Player.PositionInfo,
                    reason: Int
                ) {
                    videoPosition = newPosition.positionMs
                }

                override fun onPlayerError(error: PlaybackException) {
                    isBuffering = false
                    bufferingTimeout = false
                    errorMessage = "Error playing video: ${error.message}"
                    showControls = true
                    autoHideControls()
                }
            })
        }
    }

    // Update video position periodically
    LaunchedEffect(Unit) {
        while (true) {
            videoPosition = exoPlayer.currentPosition
            delay(1000) // Update every second
        }
    }

    // Lifecycle handling for ExoPlayer
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> exoPlayer.pause()
                Lifecycle.Event.ON_RESUME -> {
                    if (isPlaying && exoPlayer.playbackState == Player.STATE_READY) {
                        exoPlayer.play()
                    }
                }
                Lifecycle.Event.ON_DESTROY -> {
                    exoPlayer.stop()
                    exoPlayer.release()
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.release()
        }
    }

    // Update video position and playback state when changing episodes
    LaunchedEffect(currentEpisode) {
        videoPosition = 0L
        isPlaying = true
        errorMessage = null
        bufferingTimeout = false
        isBuffering = false
    }

    if (motionComic == null || currentEpisode == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Motion Comic or Episode not found",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        // PlayerView at the top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.Black)
        ) {
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                        useController = false // We'll use custom controls
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                update = { playerView ->
                    // Set the video URL for the current episode
                    val mediaItem = MediaItem.fromUri(currentEpisode!!.videoUrl)
                    exoPlayer.setMediaItem(mediaItem)
                    exoPlayer.prepare()
                    // Seek and play only if the player is ready
                    if (exoPlayer.playbackState == Player.STATE_READY) {
                        exoPlayer.seekTo(videoPosition)
                        if (isPlaying) {
                            exoPlayer.play()
                        } else {
                            exoPlayer.pause()
                        }
                    }
                }
            )

            // Loading Indicator
            if (isBuffering) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            // Error Message or Buffering Timeout
            errorMessage?.let { message ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = message,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Custom Controls Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(if (showControls) 1f else 0f)
                    .clickable(
                        onClick = {
                            showControls = !showControls
                            if (showControls) {
                                autoHideControls()
                            }
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
            ) {
                // Center Controls (Rewind, Play/Pause, Forward)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Rewind Button
                    IconButton(
                        onClick = {
                            val newPosition = (exoPlayer.currentPosition - 10000).coerceAtLeast(0)
                            exoPlayer.seekTo(newPosition)
                            videoPosition = newPosition
                            showControls = true
                            autoHideControls()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.FastRewind,
                            contentDescription = "Rewind 10s",
                            tint = Color.White
                        )
                    }

                    // Play/Pause Button
                    IconButton(
                        onClick = {
                            if (exoPlayer.isPlaying) {
                                exoPlayer.pause()
                                isPlaying = false
                            } else {
                                exoPlayer.play()
                                isPlaying = true
                            }
                            showControls = true
                            autoHideControls()
                        }
                    ) {
                        Icon(
                            imageVector = if (exoPlayer.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (exoPlayer.isPlaying) "Pause" else "Play",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    // Fast Forward Button
                    IconButton(
                        onClick = {
                            val duration = exoPlayer.duration
                            if (duration > 0) { // Ensure duration is valid
                                val newPosition = (exoPlayer.currentPosition + 10000).coerceAtMost(duration)
                                exoPlayer.seekTo(newPosition)
                                videoPosition = newPosition
                                showControls = true
                                autoHideControls()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.FastForward,
                            contentDescription = "Forward 10s",
                            tint = Color.White
                        )
                    }
                }

                // Next Button (Bottom Right, visible for 4 seconds at the end)
                if (showNextButton && currentEpisodeIndex < episodes.size - 1) {
                    IconButton(
                        onClick = {
                            currentEpisodeIndex++
                            currentEpisode = episodes[currentEpisodeIndex]
                            showNextButton = false
                            showControls = true
                            autoHideControls()
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Next Episode",
                            tint = Color.White
                        )
                    }
                }

                // Timing and Seek Bar (Bottom of the video overlay)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${formatTime(videoPosition)} / ${formatTime(videoDuration)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White
                        )
                    }
                    Slider(
                        value = if (videoDuration > 0) (videoPosition.toFloat() / videoDuration.toFloat()) else 0f,
                        onValueChange = { value ->
                            val newPosition = (value * videoDuration).toLong()
                            exoPlayer.seekTo(newPosition)
                            videoPosition = newPosition
                            showControls = true
                            autoHideControls()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = Color(0xFFBB86FC),
                            inactiveTrackColor = Color.Gray
                        )
                    )
                }

                // Back Button (Top Left)
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        }

        // Episode List (below the video player)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = motionComic.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Center
                )
            }

            items(episodes) { episode ->
                EpisodeItem(
                    episode = episode,
                    isPlaying = episode.id == currentEpisode?.id,
                    onPlayClick = {
                        currentEpisodeIndex = episodes.indexOf(episode)
                        currentEpisode = episode
                        showNextButton = false
                        showControls = true
                        autoHideControls()
                    }
                )
            }
        }
    }
}

@Composable
fun EpisodeItem(
    episode: MotionEpisode,
    isPlaying: Boolean,
    onPlayClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onPlayClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isPlaying) Color(0xFFBB86FC).copy(alpha = 0.2f) else Color(0xFF1E1E1E)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = episode.title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Button(
                onClick = onPlayClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBB86FC),
                    contentColor = Color.White
                ),
                modifier = Modifier.height(32.dp)
            ) {
                Text(
                    text = "Play",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}