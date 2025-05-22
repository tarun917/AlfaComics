package com.alfacomics.data.repository

import com.alfacomics.R

data class MotionComic(
    val id: Int,
    val title: String,
    val thumbnailUrl: Int,
    val videoUrl: String,
    val genre: String,
    val rating: Float,
    val views: Int,
    val description: String = "This is a sample description for the motion comic. It includes thrilling animations, engaging stories, and dynamic visuals that bring the comic to life in an exciting new way!",
    val episodes: List<MotionEpisode> = List(5) { index -> MotionEpisode(id = index + 1, title = "Episode ${index + 1}", videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4") }
)

data class MotionEpisode(
    val id: Int,
    val title: String,
    val videoUrl: String
)

object MotionDummyData {
    private val motionComics = listOf(
        // Romance Genre (6 motion comics)
        MotionComic(
            id = 1,
            title = "Motion Comic: Eternal Love",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            genre = "Romance",
            rating = 4.8f,
            views = 2500,
            description = "A timeless tale of two lovers separated by fate, only to be reunited through magical circumstances in a world filled with romance and mystery. Their journey is one of passion and heartbreak, as they overcome obstacles to be together forever.",
            episodes = List(5) { index -> MotionEpisode(id = index + 1, title = "Episode ${index + 1}", videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4") }
        ),
        MotionComic(
            id = 2,
            title = "Motion Comic: Hearts in Bloom",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            genre = "Romance",
            rating = 4.5f,
            views = 1800,
            description = "A young florist discovers love in the most unexpected place when a mysterious stranger visits her shop. Their budding romance faces challenges, but the power of love helps them bloom together in a heartwarming story."
        ),
        MotionComic(
            id = 3,
            title = "Motion Comic: Love Across Time",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
            genre = "Romance",
            rating = 4.7f,
            views = 2200
        ),
        MotionComic(
            id = 4,
            title = "Motion Comic: Whisper of Hearts",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
            genre = "Romance",
            rating = 4.6f,
            views = 2000
        ),
        MotionComic(
            id = 5,
            title = "Motion Comic: Forever Yours",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
            genre = "Romance",
            rating = 4.9f,
            views = 2700
        ),
        MotionComic(
            id = 6,
            title = "Motion Comic: Sunset Promises",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
            genre = "Romance",
            rating = 4.4f,
            views = 1900
        ),

        // Horror Genre (6 motion comics)
        MotionComic(
            id = 7,
            title = "Motion Comic: Shadows of Fear",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
            genre = "Horror",
            rating = 4.2f,
            views = 1500,
            description = "In a haunted mansion, a group of friends uncovers dark secrets that awaken shadowy creatures. As fear takes over, they must confront their deepest nightmares to survive the night in this chilling horror motion comic."
        ),
        MotionComic(
            id = 8,
            title = "Motion Comic: Night Terrors",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
            genre = "Horror",
            rating = 4.6f,
            views = 2200
        ),
        MotionComic(
            id = 9,
            title = "Motion Comic: Haunted Whispers",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
            genre = "Horror",
            rating = 4.3f,
            views = 1700
        ),
        MotionComic(
            id = 10,
            title = "Motion Comic: The Cursed Realm",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4",
            genre = "Horror",
            rating = 4.5f,
            views = 2000
        ),
        MotionComic(
            id = 11,
            title = "Motion Comic: Echoes of Dread",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
            genre = "Horror",
            rating = 4.1f,
            views = 1400
        ),
        MotionComic(
            id = 12,
            title = "Motion Comic: Phantom Shadows",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4",
            genre = "Horror",
            rating = 4.4f,
            views = 1800
        ),

        // Fantasy Genre (6 motion comics)
        MotionComic(
            id = 13,
            title = "Motion Comic: Mystic Realms",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            genre = "Fantasy",
            rating = 4.9f,
            views = 3000,
            description = "A brave adventurer steps into a mystical realm where magic reigns supreme. Joined by a band of enchanted creatures, they embark on a quest to defeat an ancient evil and restore peace to the land in this fantastical journey."
        ),
        MotionComic(
            id = 14,
            title = "Motion Comic: Dragon’s Legacy",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            genre = "Fantasy",
            rating = 4.4f,
            views = 1900
        ),
        MotionComic(
            id = 15,
            title = "Motion Comic: Enchanted Forest",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
            genre = "Fantasy",
            rating = 4.7f,
            views = 2600
        ),
        MotionComic(
            id = 16,
            title = "Motion Comic: Fairy Kingdoms",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
            genre = "Fantasy",
            rating = 4.6f,
            views = 2300
        ),
        MotionComic(
            id = 17,
            title = "Motion Comic: Wizard’s Quest",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
            genre = "Fantasy",
            rating = 4.8f,
            views = 2800
        ),
        MotionComic(
            id = 18,
            title = "Motion Comic: Magic Chronicles",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
            genre = "Fantasy",
            rating = 4.5f,
            views = 2100
        ),

        // Sci-Fi Genre (6 motion comics)
        MotionComic(
            id = 19,
            title = "Motion Comic: Galactic Heroes",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
            genre = "Sci-Fi",
            rating = 4.7f,
            views = 2800,
            description = "In a distant galaxy, a team of heroes fights against an alien invasion threatening humanity. With advanced technology and unwavering courage, they embark on a mission to save the universe in this thrilling sci-fi motion comic adventure."
        ),
        MotionComic(
            id = 20,
            title = "Motion Comic: Cyber Rebellion",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
            genre = "Sci-Fi",
            rating = 4.3f,
            views = 1600
        ),
        MotionComic(
            id = 21,
            title = "Motion Comic: Star Voyage",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
            genre = "Sci-Fi",
            rating = 4.5f,
            views = 2000
        ),
        MotionComic(
            id = 22,
            title = "Motion Comic: Alien Frontier",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4",
            genre = "Sci-Fi",
            rating = 4.6f,
            views = 2400
        ),
        MotionComic(
            id = 23,
            title = "Motion Comic: Time Warp",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
            genre = "Sci-Fi",
            rating = 4.4f,
            views = 1800
        ),
        MotionComic(
            id = 24,
            title = "Motion Comic: Future Echoes",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4",
            genre = "Sci-Fi",
            rating = 4.8f,
            views = 2600
        ),

        // Adventure Genre (6 motion comics)
        MotionComic(
            id = 25,
            title = "Motion Comic: The Cosmic Adventure",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            genre = "Adventure",
            rating = 4.5f,
            views = 2000,
            description = "An explorer ventures into the cosmos, discovering uncharted planets and facing thrilling challenges. Along the way, they uncover ancient secrets that could change the fate of the universe in this epic adventure motion comic."
        ),
        MotionComic(
            id = 26,
            title = "Motion Comic: Quest for Glory",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            genre = "Adventure",
            rating = 4.8f,
            views = 2400
        ),
        MotionComic(
            id = 27,
            title = "Motion Comic: Jungle Odyssey",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
            genre = "Adventure",
            rating = 4.6f,
            views = 2200
        ),
        MotionComic(
            id = 28,
            title = "Motion Comic: Pirate’s Treasure",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
            genre = "Adventure",
            rating = 4.4f,
            views = 1900
        ),
        MotionComic(
            id = 29,
            title = "Motion Comic: Mountain Expedition",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
            genre = "Adventure",
            rating = 4.7f,
            views = 2500
        ),
        MotionComic(
            id = 30,
            title = "Motion Comic: Desert Quest",
            thumbnailUrl = R.drawable.ic_launcher_background,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
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