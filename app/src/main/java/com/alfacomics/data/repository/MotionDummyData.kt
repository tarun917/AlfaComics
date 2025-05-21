package com.alfacomics.data.repository

import com.alfacomics.R

data class MotionComic(
    val id: Int,
    val title: String,
    val thumbnailUrl: Int, // Drawable resource ID (not used directly in UI for now)
    val videoUrl: String,
    val genre: String,
    val rating: Float,
    val views: Int
)

object MotionDummyData {
    private val motionComics = listOf(
        // Romance Genre (6 motion comics)
        MotionComic(
            id = 1,
            title = "Motion Comic: Eternal Love",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_1",
            genre = "Romance",
            rating = 4.8f,
            views = 2500
        ),
        MotionComic(
            id = 2,
            title = "Motion Comic: Hearts in Bloom",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_2",
            genre = "Romance",
            rating = 4.5f,
            views = 1800
        ),
        MotionComic(
            id = 3,
            title = "Motion Comic: Love Across Time",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_3",
            genre = "Romance",
            rating = 4.7f,
            views = 2200
        ),
        MotionComic(
            id = 4,
            title = "Motion Comic: Whisper of Hearts",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_4",
            genre = "Romance",
            rating = 4.6f,
            views = 2000
        ),
        MotionComic(
            id = 5,
            title = "Motion Comic: Forever Yours",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_5",
            genre = "Romance",
            rating = 4.9f,
            views = 2700
        ),
        MotionComic(
            id = 6,
            title = "Motion Comic: Sunset Promises",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_6",
            genre = "Romance",
            rating = 4.4f,
            views = 1900
        ),

        // Horror Genre (6 motion comics)
        MotionComic(
            id = 7,
            title = "Motion Comic: Shadows of Fear",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_7",
            genre = "Horror",
            rating = 4.2f,
            views = 1500
        ),
        MotionComic(
            id = 8,
            title = "Motion Comic: Night Terrors",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_8",
            genre = "Horror",
            rating = 4.6f,
            views = 2200
        ),
        MotionComic(
            id = 9,
            title = "Motion Comic: Haunted Whispers",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_9",
            genre = "Horror",
            rating = 4.3f,
            views = 1700
        ),
        MotionComic(
            id = 10,
            title = "Motion Comic: The Cursed Realm",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_10",
            genre = "Horror",
            rating = 4.5f,
            views = 2000
        ),
        MotionComic(
            id = 11,
            title = "Motion Comic: Echoes of Dread",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_11",
            genre = "Horror",
            rating = 4.1f,
            views = 1400
        ),
        MotionComic(
            id = 12,
            title = "Motion Comic: Phantom Shadows",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_12",
            genre = "Horror",
            rating = 4.4f,
            views = 1800
        ),

        // Fantasy Genre (6 motion comics)
        MotionComic(
            id = 13,
            title = "Motion Comic: Mystic Realms",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_13",
            genre = "Fantasy",
            rating = 4.9f,
            views = 3000
        ),
        MotionComic(
            id = 14,
            title = "Motion Comic: Dragon’s Legacy",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_14",
            genre = "Fantasy",
            rating = 4.4f,
            views = 1900
        ),
        MotionComic(
            id = 15,
            title = "Motion Comic: Enchanted Forest",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_15",
            genre = "Fantasy",
            rating = 4.7f,
            views = 2600
        ),
        MotionComic(
            id = 16,
            title = "Motion Comic: Fairy Kingdoms",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_16",
            genre = "Fantasy",
            rating = 4.6f,
            views = 2300
        ),
        MotionComic(
            id = 17,
            title = "Motion Comic: Wizard’s Quest",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_17",
            genre = "Fantasy",
            rating = 4.8f,
            views = 2800
        ),
        MotionComic(
            id = 18,
            title = "Motion Comic: Magic Chronicles",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_18",
            genre = "Fantasy",
            rating = 4.5f,
            views = 2100
        ),

        // Sci-Fi Genre (6 motion comics)
        MotionComic(
            id = 19,
            title = "Motion Comic: Galactic Heroes",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_19",
            genre = "Sci-Fi",
            rating = 4.7f,
            views = 2800
        ),
        MotionComic(
            id = 20,
            title = "Motion Comic: Cyber Rebellion",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_20",
            genre = "Sci-Fi",
            rating = 4.3f,
            views = 1600
        ),
        MotionComic(
            id = 21,
            title = "Motion Comic: Star Voyage",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_21",
            genre = "Sci-Fi",
            rating = 4.5f,
            views = 2000
        ),
        MotionComic(
            id = 22,
            title = "Motion Comic: Alien Frontier",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_22",
            genre = "Sci-Fi",
            rating = 4.6f,
            views = 2400
        ),
        MotionComic(
            id = 23,
            title = "Motion Comic: Time Warp",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_23",
            genre = "Sci-Fi",
            rating = 4.4f,
            views = 1800
        ),
        MotionComic(
            id = 24,
            title = "Motion Comic: Future Echoes",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_24",
            genre = "Sci-Fi",
            rating = 4.8f,
            views = 2600
        ),

        // Adventure Genre (6 motion comics)
        MotionComic(
            id = 25,
            title = "Motion Comic: The Cosmic Adventure",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_25",
            genre = "Adventure",
            rating = 4.5f,
            views = 2000
        ),
        MotionComic(
            id = 26,
            title = "Motion Comic: Quest for Glory",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_26",
            genre = "Adventure",
            rating = 4.8f,
            views = 2400
        ),
        MotionComic(
            id = 27,
            title = "Motion Comic: Jungle Odyssey",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_27",
            genre = "Adventure",
            rating = 4.6f,
            views = 2200
        ),
        MotionComic(
            id = 28,
            title = "Motion Comic: Pirate’s Treasure",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_28",
            genre = "Adventure",
            rating = 4.4f,
            views = 1900
        ),
        MotionComic(
            id = 29,
            title = "Motion Comic: Mountain Expedition",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_29",
            genre = "Adventure",
            rating = 4.7f,
            views = 2500
        ),
        MotionComic(
            id = 30,
            title = "Motion Comic: Desert Quest",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "placeholder_motion_video_30",
            genre = "Adventure",
            rating = 4.5f,
            views = 2100
        )
    )

    fun getMotionComics(): List<MotionComic> {
        return motionComics
    }

    fun getMotionComicById(id: Int): MotionComic? {
        return motionComics.find { it.id == id }
    }

    fun getGenres(): List<String> {
        return motionComics.map { it.genre }.distinct()
    }

    fun getMotionComicsByGenre(genre: String): List<MotionComic> {
        return motionComics.filter { it.genre == genre }
    }
}