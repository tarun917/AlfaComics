package com.alfacomics.presentation.ui.screens.home

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

// Custom Saver for MotionEpisode? to handle nullable MotionEpisode
val MotionEpisodeSaver: Saver<MotionEpisode?, List<Any?>> = Saver(
    save = { episode ->
        if (episode == null) {
            listOf(null, null, null)
        } else {
            listOf(episode.id, episode.title, episode.videoUrl)
        }
    },
    restore = { restored ->
        if (restored[0] == null || restored[1] == null || restored[2] == null) {
            null
        } else {
            MotionEpisode(
                id = restored[0] as Int,
                title = restored[1] as String,
                videoUrl = restored[2] as String
            )
        }
    }
)

@Composable
fun EpisodePlayerScreen(
    navController: NavHostController,
    motionComicId: Int,
    initialEpisodeId: Int,
    isLandscape: Boolean,
    onOrientationChange: (Boolean) -> Unit
) {
    val motionComic = MotionDummyData.getMotionComicById(motionComicId)
    val episodes = motionComic?.episodes ?: emptyList()
    val context = LocalContext.current

    // State for the currently playing episode index
    var currentEpisodeIndex by rememberSaveable { mutableStateOf(episodes.indexOfFirst { it.id == initialEpisodeId }.coerceAtLeast(0)) }

    // State for the currently playing episode using custom Saver
    val currentEpisodeState: MutableState<MotionEpisode?> = rememberSaveable(stateSaver = MotionEpisodeSaver) {
        mutableStateOf(episodes.getOrNull(currentEpisodeIndex))
    }
    var currentEpisode by currentEpisodeState

    // State for controls
    var showNextButton by remember { mutableStateOf(false) }
    var showControls by remember { mutableStateOf(true) }
    var isPlaying by remember { mutableStateOf(true) }
    var videoPosition by remember { mutableLongStateOf(0L) }
    var videoDuration by remember { mutableLongStateOf(0L) }
    var isBuffering by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var bufferingTimeout by remember { mutableStateOf(false) }

    // States for double-tap to seek feedback
    var showRewindFeedback by remember { mutableStateOf(false) }
    var showForwardFeedback by remember { mutableStateOf(false) }

    // Coroutine scope for auto-hiding controls and buffering timeout
    val coroutineScope = remember { CoroutineScope(SupervisorJob() + Dispatchers.Main) }

    // Function to auto-hide controls after a delay, but only if the video is playing
    fun autoHideControls() {
        coroutineScope.launch {
            delay(3000) // Hide controls after 3 seconds
            if (isPlaying) {
                showControls = false
            }
        }
    }

    // Function to auto-hide double-tap feedback after a delay
    fun autoHideFeedback() {
        coroutineScope.launch {
            delay(500) // Hide feedback after 0.5 seconds
            showRewindFeedback = false
            showForwardFeedback = false
        }
    }

    // Function to format time in mm:ss format
    @SuppressLint("DefaultLocale")
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

    // Update video position periodically using a coroutine on the main thread
    LaunchedEffect(exoPlayer) {
        while (isActive) {
            // Access ExoPlayer on the main thread
            val position = exoPlayer.currentPosition
            videoPosition = position
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
                    coroutineScope.cancel() // Cancel the coroutine scope
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.release()
            coroutineScope.cancel()
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

    // Early return if motionComic or currentEpisode is null
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
        // PlayerView at the top with gesture detection directly applied
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .let {
                    if (isLandscape) it.fillMaxHeight() else it.height(200.dp) // Fill screen in landscape
                }
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
                    .clip(RoundedCornerShape(8.dp))
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                showControls = !showControls
                                if (showControls) {
                                    autoHideControls()
                                }
                            },
                            onDoubleTap = { offset ->
                                val width = size.width.toFloat()
                                val tapX = offset.x
                                if (tapX < width / 2) {
                                    // Double-tap on the left side (Rewind)
                                    val newPosition = (exoPlayer.currentPosition - 10000).coerceAtLeast(0)
                                    exoPlayer.seekTo(newPosition)
                                    videoPosition = newPosition
                                    showRewindFeedback = true
                                    autoHideFeedback()
                                } else {
                                    // Double-tap on the right side (Fast Forward)
                                    val duration = exoPlayer.duration
                                    if (duration > 0) {
                                        val newPosition = (exoPlayer.currentPosition + 10000).coerceAtMost(duration)
                                        exoPlayer.seekTo(newPosition)
                                        videoPosition = newPosition
                                        showForwardFeedback = true
                                        autoHideFeedback()
                                    }
                                }
                                showControls = true
                                autoHideControls()
                            }
                        )
                    },
                update = { playerView ->
                    // Since we've already returned if currentEpisode is null, it's safe to access properties here
                    val mediaItem = MediaItem.fromUri(currentEpisode?.videoUrl.toString())
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
                        .size(48.dp)
                        .align(Alignment.Center)
                        .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Error Message or Buffering Timeout
            errorMessage?.let { message ->
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center)
                        .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .padding(16.dp),
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

            // Double-Tap Feedback (Rewind)
            if (showRewindFeedback) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 32.dp)
                        .background(Color.Black.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Rewind 10s",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Double-Tap Feedback (Forward)
            if (showForwardFeedback) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 32.dp)
                        .background(Color.Black.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Forward 10s",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Center Controls (Rewind, Play/Pause, Forward)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .background(Color.Black.copy(alpha = 0.0f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .alpha(if (showControls || !isPlaying) 1f else 0f), // Show controls if paused
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
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }

                // Play/Pause Button
                IconButton(
                    onClick = {
                        if (exoPlayer.isPlaying) {
                            exoPlayer.pause()
                            isPlaying = false
                            showControls = true // Ensure controls stay visible when paused
                        } else {
                            exoPlayer.play()
                            isPlaying = true
                            showControls = true
                            autoHideControls()
                        }
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
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            // Rotate Button (Top Right, to toggle orientation)
            IconButton(
                onClick = {
                    onOrientationChange(!isLandscape) // Toggle orientation
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .alpha(if (showControls || !isPlaying) 1f else 0f) // Show if paused
            ) {
                Icon(
                    imageVector = if (isLandscape) Icons.Default.ScreenRotation else Icons.Default.ScreenRotation,
                    contentDescription = if (isLandscape) "Switch to Portrait" else "Switch to Landscape",
                    tint = Color.White
                )
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
                    .background(Color.Black.copy(alpha = 0.0f))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .alpha(if (showControls || !isPlaying) 1f else 0f) // Show if paused
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

            // Progress Bar Overlay (Always visible)
            LinearProgressIndicator(
                progress = { if (videoDuration > 0) (videoPosition.toFloat() / videoDuration.toFloat()) else 0f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .align(Alignment.BottomCenter),
                color = Color.Red,
                trackColor = Color.Gray.copy(alpha = 0.1f)
            )

            // Back Button (Top Left)
            IconButton(
                onClick = {
                    navController.popBackStack()
                    // Reset to portrait when navigating back
                    onOrientationChange(false)
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .alpha(if (showControls || !isPlaying) 1f else 0f) // Show if paused
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        // Episode List (below the video player, hidden in landscape mode)
        if (!isLandscape) {
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
            containerColor = if (isPlaying) Color(0xFFBB86FC).copy(alpha = 0.3f) else Color(0xFF1E1E1E)
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