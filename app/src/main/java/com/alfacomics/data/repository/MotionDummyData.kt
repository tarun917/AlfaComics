package com.alfacomics.data.repository

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.alfacomics.R

data class MotionComic(
    val id: Int,
    val title: String,
    val thumbnailUrl: Int,
    val genre: String,
    val rating: Float,
    val views: Int,
    val description: String,
    val episodes: List<MotionEpisode>
)

data class MotionEpisode(
    val id: Int,
    val title: String,
    val videoUrl: String,
    val isUnlocked: Boolean = true
)

object MotionDummyData {
    // Array of drawable resource IDs for cyclic mapping
    private val motionThumbnails = listOf(
        R.drawable.motion_image1,
        R.drawable.motion_image2,
        R.drawable.motion_image3,
        R.drawable.motion_image4,
        R.drawable.motion_image5
    )

    private val motionComics = mutableStateListOf(
        MotionComic(
            id = 1,
            title = "Motion Comic: Eternal Love",
            thumbnailUrl = motionThumbnails[0],
            genre = "Romance",
            rating = 4.8f,
            views = 2500,
            description = "A timeless tale of two lovers separated by fate, only to be reunited through magical circumstances in a world filled with romance and mystery. Their journey is one of passion and heartbreak, as they overcome obstacles to be together forever.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 3,
                    title = "Episode 3",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 4,
                    title = "Episode 4",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 5,
                    title = "Episode 5",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 6,
                    title = "Episode 6",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = false
                ),
                MotionEpisode(
                    id = 7,
                    title = "Episode 7",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = false
                ),
                MotionEpisode(
                    id = 8,
                    title = "Episode 8",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = false
                ),
                MotionEpisode(
                    id = 9,
                    title = "Episode 9",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = false
                )
            )
        ),
        MotionComic(
            id = 2,
            title = "Motion Comic: Hearts in Bloom",
            thumbnailUrl = motionThumbnails[1],
            genre = "Romance",
            rating = 4.5f,
            views = 1800,
            description = "A young florist discovers love in the most unexpected place when a mysterious stranger visits her shop. Their budding romance faces challenges, but the power of love helps them bloom together in a heartwarming story.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 3,
                    title = "Episode 3",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 4,
                    title = "Episode 4",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 5,
                    title = "Episode 5",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 6,
                    title = "Episode 6",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = false
                ),
                MotionEpisode(
                    id = 7,
                    title = "Episode 7",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = false
                ),
                MotionEpisode(
                    id = 8,
                    title = "Episode 8",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = false
                )
            )
        ),
        MotionComic(
            id = 3,
            title = "Motion Comic: Love Across Time",
            thumbnailUrl = motionThumbnails[2],
            genre = "Romance",
            rating = 4.7f,
            views = 2200,
            description = "Two souls from different eras find each other through a mysterious time portal. Their love transcends time as they navigate challenges from both past and future in this romantic odyssey.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 3,
                    title = "Episode 3",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 4,
                    title = "Episode 4",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 5,
                    title = "Episode 5",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 6,
                    title = "Episode 6",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = false
                ),
                MotionEpisode(
                    id = 7,
                    title = "Episode 7",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = false
                ),
                MotionEpisode(
                    id = 8,
                    title = "Episode 8",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = false
                )
            )
        ),
        MotionComic(
            id = 4,
            title = "Motion Comic: Whisper of Hearts",
            thumbnailUrl = motionThumbnails[3],
            genre = "Romance",
            rating = 4.6f,
            views = 2000,
            description = "A shy poet and a free-spirited artist meet by chance, their hearts whispering promises of love. Together, they discover the beauty of vulnerability in this tender romance.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 3,
                    title = "Episode 3",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 4,
                    title = "Episode 4",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 5,
                    title = "Episode 5",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 5,
            title = "Motion Comic: Forever Yours",
            thumbnailUrl = motionThumbnails[4],
            genre = "Romance",
            rating = 4.9f,
            views = 2700,
            description = "A couple's love is tested by life's challenges, but their unwavering commitment to each other proves that true love lasts forever in this emotional journey.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 3,
                    title = "Episode 3",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 4,
                    title = "Episode 4",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 5,
                    title = "Episode 5",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 6,
            title = "Motion Comic: Sunset Promises",
            thumbnailUrl = motionThumbnails[0],
            genre = "Romance",
            rating = 4.4f,
            views = 1900,
            description = "Under the glow of a sunset, two childhood friends confess their love, promising to stand by each other through every dawn and dusk in this touching story.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 7,
            title = "Motion Comic: Shadows of Fear",
            thumbnailUrl = motionThumbnails[1],
            genre = "Horror",
            rating = 4.2f,
            views = 1500,
            description = "In a haunted mansion, a group of friends uncovers dark secrets that awaken shadowy creatures. As fear takes over, they must confront their deepest nightmares to survive the night in this chilling horror motion comic.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 8,
            title = "Motion Comic: Night Terrors",
            thumbnailUrl = motionThumbnails[2],
            genre = "Horror",
            rating = 4.6f,
            views = 2200,
            description = "A small town is plagued by horrifying nightmares that come to life at midnight. A group of teens must unravel the mystery before they succumb to their night terrors in this spine-chilling tale.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 9,
            title = "Motion Comic: Haunted Whispers",
            thumbnailUrl = motionThumbnails[3],
            genre = "Horror",
            rating = 4.3f,
            views = 1700,
            description = "A journalist investigating an old asylum hears whispers of the past that turn into horrifying visions. Can she escape the haunted echoes before they consume her?",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 10,
            title = "Motion Comic: The Cursed Realm",
            thumbnailUrl = motionThumbnails[4],
            genre = "Horror",
            rating = 4.5f,
            views = 2000,
            description = "A cursed forest traps adventurers in a realm of eternal darkness, where every shadow hides a deadly secret. Survival means facing the curse head-on in this terrifying journey.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 11,
            title = "Motion Comic: Echoes of Dread",
            thumbnailUrl = motionThumbnails[0],
            genre = "Horror",
            rating = 4.1f,
            views = 1400,
            description = "An ancient artifact unleashes echoes of dread that haunt a small village. A lone investigator must stop the terror before it spreads in this eerie horror tale.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 12,
            title = "Motion Comic: Phantom Shadows",
            thumbnailUrl = motionThumbnails[1],
            genre = "Horror",
            rating = 4.4f,
            views = 1800,
            description = "A photographer captures more than just images when phantom shadows begin to haunt her every shot. She must uncover the truth before the shadows claim her soul.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 13,
            title = "Motion Comic: Mystic Realms",
            thumbnailUrl = motionThumbnails[2],
            genre = "Fantasy",
            rating = 4.9f,
            views = 3000,
            description = "A brave adventurer steps into a mystical realm where magic reigns supreme. Joined by a band of enchanted creatures, they embark on a quest to defeat an ancient evil and restore peace to the land in this fantastical journey.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 14,
            title = "Motion Comic: Dragon’s Legacy",
            thumbnailUrl = motionThumbnails[3],
            genre = "Fantasy",
            rating = 4.4f,
            views = 1900,
            description = "A young warrior inherits the legacy of a dragon, gaining magical powers to fight an evil sorcerer. Their journey through a fantastical world is filled with wonder and danger.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 15,
            title = "Motion Comic: Enchanted Forest",
            thumbnailUrl = motionThumbnails[4],
            genre = "Fantasy",
            rating = 4.7f,
            views = 2600,
            description = "A magical forest hides secrets that only the pure of heart can uncover. A young hero ventures in to save their village from an impending curse in this enchanting tale.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 16,
            title = "Motion Comic: Fairy Kingdoms",
            thumbnailUrl = motionThumbnails[0],
            genre = "Fantasy",
            rating = 4.6f,
            views = 2300,
            description = "In a hidden realm, fairy kingdoms battle for supremacy. A young fairy must rise to unite them against a dark force threatening their world in this magical story.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 17,
            title = "Motion Comic: Wizard’s Quest",
            thumbnailUrl = motionThumbnails[1],
            genre = "Fantasy",
            rating = 4.8f,
            views = 2800,
            description = "A young wizard embarks on a quest to find a legendary artifact that can save their kingdom. Along the way, they uncover hidden powers and face mythical creatures.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 18,
            title = "Motion Comic: Magic Chronicles",
            thumbnailUrl = motionThumbnails[2],
            genre = "Fantasy",
            rating = 4.5f,
            views = 2100,
            description = "Chronicles of a magical world where every spell tells a story. A group of mages must protect their realm from an ancient prophecy in this spellbinding adventure.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 19,
            title = "Motion Comic: Galactic Heroes",
            thumbnailUrl = motionThumbnails[3],
            genre = "Sci-Fi",
            rating = 4.7f,
            views = 2800,
            description = "In a distant galaxy, a team of heroes fights against an alien invasion threatening humanity. With advanced technology and unwavering courage, they embark on a mission to save the universe in this thrilling sci-fi motion comic adventure.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 20,
            title = "Motion Comic: Cyber Rebellion",
            thumbnailUrl = motionThumbnails[4],
            genre = "Sci-Fi",
            rating = 4.3f,
            views = 1600,
            description = "In a dystopian future, a group of rebels fights against a tyrannical AI that controls humanity. Their battle for freedom unfolds in a high-tech world of intrigue and danger.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 21,
            title = "Motion Comic: Star Voyage",
            thumbnailUrl = motionThumbnails[0],
            genre = "Sci-Fi",
            rating = 4.5f,
            views = 2000,
            description = "A starship crew voyages to the edge of the galaxy, encountering strange phenomena and alien civilizations. Their journey tests their courage and unity in this sci-fi epic.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 22,
            title = "Motion Comic: Alien Frontier",
            thumbnailUrl = motionThumbnails[1],
            genre = "Sci-Fi",
            rating = 4.6f,
            views = 2400,
            description = "Colonists on a distant planet face a hostile alien species that threatens their survival. They must adapt and fight back to secure their new frontier in this intense sci-fi saga.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 23,
            title = "Motion Comic: Time Warp",
            thumbnailUrl = motionThumbnails[2],
            genre = "Sci-Fi",
            rating = 4.4f,
            views = 1800,
            description = "A scientist accidentally triggers a time warp, sending them into a future where time travel is outlawed. They must navigate a dangerous timeline to return home.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 24,
            title = "Motion Comic: Future Echoes",
            thumbnailUrl = motionThumbnails[3],
            genre = "Sci-Fi",
            rating = 4.8f,
            views = 2600,
            description = "Echoes from the future warn a pilot of an impending disaster. With time running out, they must change the course of destiny in this gripping sci-fi adventure.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 25,
            title = "Motion Comic: The Cosmic Adventure",
            thumbnailUrl = motionThumbnails[4],
            genre = "Adventure",
            rating = 4.5f,
            views = 2000,
            description = "An explorer ventures into the cosmos, discovering uncharted planets and facing thrilling challenges. Along the way, they uncover ancient secrets that could change the fate of the universe in this epic adventure motion comic.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 26,
            title = "Motion Comic: Quest for Glory",
            thumbnailUrl = motionThumbnails[0],
            genre = "Adventure",
            rating = 4.8f,
            views = 2400,
            description = "A hero sets out on a quest for glory, battling fierce monsters and solving ancient puzzles to reclaim a lost kingdom in this action-packed adventure.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 27,
            title = "Motion Comic: Jungle Odyssey",
            thumbnailUrl = motionThumbnails[1],
            genre = "Adventure",
            rating = 4.6f,
            views = 2200,
            description = "A daring explorer journeys deep into a jungle filled with hidden dangers and ancient ruins. Their odyssey reveals a lost civilization waiting to be discovered.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 28,
            title = "Motion Comic: Pirate’s Treasure",
            thumbnailUrl = motionThumbnails[2],
            genre = "Adventure",
            rating = 4.4f,
            views = 1900,
            description = "A pirate crew hunts for a legendary treasure buried on a mysterious island. Their adventure is filled with traps, rival pirates, and unexpected allies.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 29,
            title = "Motion Comic: Mountain Expedition",
            thumbnailUrl = motionThumbnails[3],
            genre = "Adventure",
            rating = 4.7f,
            views = 2500,
            description = "A team of climbers scales a treacherous mountain, facing avalanches and hidden dangers. Their expedition uncovers a secret that changes everything.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        ),
        MotionComic(
            id = 30,
            title = "Motion Comic: Desert Quest",
            thumbnailUrl = motionThumbnails[4],
            genre = "Adventure",
            rating = 4.5f,
            views = 2100,
            description = "An archaeologist leads a team on a quest through a scorching desert to find a lost city. Their journey tests their resilience and reveals ancient wonders.",
            episodes = listOf(
                MotionEpisode(
                    id = 1,
                    title = "Episode 1",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                ),
                MotionEpisode(
                    id = 2,
                    title = "Episode 2",
                    videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_10mb.mp4",
                    isUnlocked = true
                )
            )
        )
    )

    fun getMotionComics(): List<MotionComic> {
        return motionComics.filter { it.episodes.isNotEmpty() }
    }

    fun getMotionComicById(id: Int): MotionComic? {
        val comic = motionComics.find { it.id == id }
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
            motionComic.episodes.map { episode -> episode.copy(isUnlocked = true) }
        } else {
            motionComic.episodes.mapIndexed { index, episode ->
                if (index < 5) episode.copy(isUnlocked = true) else episode
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
            val updatedEpisode = episode.copy(isUnlocked = true)
            updateMotionEpisode(motionComicId, episodeId, updatedEpisode)
            Log.d("MotionDummyData", "Episode unlocked successfully. New alfaCoins: ${userProfile.alfaCoins - coinsRequired}")
            return true
        } else {
            Log.d("MotionDummyData", "Not enough coins to unlock episode")
            return false
        }
    }

    fun updateMotionEpisode(motionComicId: Int, episodeId: Int, updatedEpisode: MotionEpisode) {
        val motionComicIndex = motionComics.indexOfFirst { it.id == motionComicId }
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