package com.alfacomics.data.repository

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.alfacomics.pratilipitv.data.repository.MotionComic
import com.alfacomics.pratilipitv.data.repository.MotionEpisode

object MotionDummyData {
    private val motionComics = mutableStateListOf(
        MotionComic(
            motion_comic_id = 1,
            title = "Alfa Centaury",
            description = "superb",
            image_url = "http://192.168.1.14:8000/media/comics/images/aaaprofile.jpeg",
            genre = "Fantasy",
            rating = 0.0f,
            ratings_count = 0,
            read_count = 0,
            price = 100.0,
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    motion_comic_id = 1,
                    title = "Alfa Centaury Episode 1",
                    video_url = "http://192.168.1.14:8000/media/comics/videos/logo_green-3_R1xQhmo.mp4",
                    is_unlocked = true
                ),
                MotionEpisode(
                    id = 8,
                    motion_comic_id = 1,
                    title = "Alfa Centaury Episode 2",
                    video_url = "http://192.168.1.14:8000/media/comics/videos/logo_green-2_i9PGKa7.mp4",
                    is_unlocked = true
                )
            ),
            is_unlocked = false
        ),
        MotionComic(
            motion_comic_id = 2,
            title = "Kalyani",
            description = "awesome",
            image_url = "http://192.168.1.14:8000/media/comics/images/ic_coin.png",
            genre = "Romance",
            rating = 0.0f,
            ratings_count = 0,
            read_count = 0,
            price = 80.0,
            episodes = listOf(
                MotionEpisode(
                    id = 2,
                    motion_comic_id = 2,
                    title = "Kalyani 1",
                    video_url = "http://192.168.1.14:8000/media/comics/videos/Untitled_DSqtf6q.mp4",
                    is_unlocked = true
                ),
                MotionEpisode(
                    id = 3,
                    motion_comic_id = 2,
                    title = "Kalyani 2",
                    video_url = "http://192.168.1.14:8000/media/comics/videos/Untitled_1.mp4",
                    is_unlocked = true
                ),
                MotionEpisode(
                    id = 4,
                    motion_comic_id = 2,
                    title = "Kalyani 3",
                    video_url = "http://192.168.1.14:8000/media/comics/videos/logo_green-2_UID2KO0.mp4",
                    is_unlocked = true
                ),
                MotionEpisode(
                    id = 5,
                    motion_comic_id = 2,
                    title = "Kalyani 4",
                    video_url = "http://192.168.1.14:8000/media/comics/videos/logo_green.mp4",
                    is_unlocked = true
                ),
                MotionEpisode(
                    id = 6,
                    motion_comic_id = 2,
                    title = "Kalyani 5",
                    video_url = "http://192.168.1.14:8000/media/comics/videos/logo_green-4_kR1ADmv.mp4",
                    is_unlocked = true
                ),
                MotionEpisode(
                    id = 7,
                    motion_comic_id = 2,
                    title = "Kalyani 6",
                    video_url = "http://192.168.1.14:8000/media/comics/videos/logo_green-3_HSGNYTa.mp4",
                    is_unlocked = true
                )
            ),
            is_unlocked = false
        )
    )

    fun getMotionComics(): List<MotionComic> {
        return motionComics.filter { it.episodes.isNotEmpty() }
    }

    fun getMotionComicById(id: Int): MotionComic? {
        val comic = motionComics.find { it.motion_comic_id == id }
        return if (comic != null && comic.episodes.isNotEmpty()) comic else null
    }

    fun getGenres(): List<String> {
        return motionComics.map { it.genre }.distinct()
    }

    fun getMotionComicsByGenre(genre: String): List<MotionComic> {
        return motionComics.filter { it.genre == genre && it.episodes.isNotEmpty() }
    }

    fun getMotionEpisodesWithSubscription(motionComicId: Int): List<MotionEpisode> {
        val motionComic = getMotionComicById(motionComicId) ?: return emptyList()
        return if (DummyData.isUserSubscribed()) {
            motionComic.episodes.map { episode -> episode.copy(is_unlocked = true) }
        } else {
            motionComic.episodes.mapIndexed { index, episode ->
                if (index < 5) episode.copy(is_unlocked = true) else episode
            }
        }
    }

    fun unlockMotionEpisodeWithCoins(motionComicId: Int, episodeId: Int, coinsRequired: Int = 50): Boolean {
        Log.d("MotionDummyData", "Attempting to unlock episode $episodeId for comic $motionComicId")
        val motionComic = getMotionComicById(motionComicId) ?: return false.also {
            Log.d("MotionDummyData", "MotionComic not found")
        }
        val episode = motionComic.episodes.find { it.id == episodeId } ?: return false.also {
            Log.d("MotionDummyData", "Episode not found")
        }

        val userProfile = DummyData.getUserProfile()
        Log.d("MotionDummyData", "Current alfaCoins: ${userProfile.alfaCoins}, Required: $coinsRequired")
        if (userProfile.alfaCoins >= coinsRequired) {
            DummyData.updateAlfaCoins(userProfile.alfaCoins - coinsRequired)
            val updatedEpisode = episode.copy(is_unlocked = true)
            updateMotionEpisode(motionComicId, episodeId, updatedEpisode)
            Log.d("MotionDummyData", "Episode unlocked successfully. New alfaCoins: ${userProfile.alfaCoins - coinsRequired}")
            return true
        } else {
            Log.d("MotionDummyData", "Not enough coins to unlock episode")
            return false
        }
    }

    fun updateMotionEpisode(motionComicId: Int, episodeId: Int, updatedEpisode: MotionEpisode) {
        val motionComicIndex = motionComics.indexOfFirst { it.motion_comic_id == motionComicId }
        if (motionComicIndex != -1) {
            val motionComic = motionComics[motionComicIndex]
            val updatedEpisodes = motionComic.episodes.map { episode ->
                if (episode.id == episodeId) updatedEpisode else episode
            }
            motionComics[motionComicIndex] = motionComic.copy(episodes = updatedEpisodes)
            Log.d("MotionDummyData", "Episode $episodeId updated for comic $motionComicId")
        } else {
            Log.d("MotionDummyData", "Failed to update episode: MotionComic not found")
        }
    }
}