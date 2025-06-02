package com.alfacomics.data.repository

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import com.alfacomics.R

data class Comic(
    val id: Int,
    val title: String,
    val coverImageResId: Int,  // Changed from coverImageUrl to coverImageResId
    val rating: Float,
    val genre: String,
    val category: String,
    val price: Int,
    val readCount: Int,
    val description: String = "This is a sample description for the comic. It includes exciting adventures, thrilling plots, and amazing characters that will keep you hooked!",
    val episodes: List<Episode> = List(10) { index ->
        Episode(
            id = index + 1,
            title = "Episode ${index + 1}",
            isFree = index < 5
        )
    }
)

data class Episode(
    val id: Int,
    val title: String,
    val isFree: Boolean,
    val pages: List<EpisodePage> = List(10) { index ->
        EpisodePage(
            imageResourceId = R.drawable.ic_launcher_background,
            text = when (index % 3) {
                0 -> "Hero: I will save the city!"
                1 -> "Villain: You’ll never stop me!"
                else -> "Narrator: The battle intensifies..."
            }
        )
    }
)

data class EpisodePage(
    val imageResourceId: Int,
    val text: String
)

data class EpisodeSocialData(
    val comicId: Int,
    val episodeId: Int,
    var isLiked: Boolean = false,
    var likeCount: Int = 0,
    val tags: MutableList<String> = mutableListOf(),
    val comments: MutableList<String> = mutableListOf()
)

data class CommunityPost(
    val id: Int,
    val content: String,
    val username: String,
    val timestamp: String,
    val imageUrl: String? = null,
    val poll: Poll? = null,
    val comments: MutableList<Comment> = mutableListOf(),
    val reactions: Map<String, List<String>> = emptyMap(),
    val userProfilePictureResourceId: Int = R.drawable.ic_launcher_background
)

data class Comment(
    val username: String,
    val content: String,
    val timestamp: String
)

data class Poll(
    val question: String,
    val options: List<PollOption>
)

data class PollOption(
    val text: String,
    val votes: Int = 0
)

data class Badge(
    val name: String,
    val color: Color
)

data class UserProfile(
    val userId: Long,
    val username: String,
    val email: String,
    val profilePictureResourceId: Int,
    val profilePictureBitmap: ImageBitmap? = null,
    val aboutMe: String,
    val alfaCoins: Int,
    val followers: List<String> = emptyList(),
    val following: List<String> = emptyList(),
    val episodesRead: Int = 0,
    val badges: List<Badge> = emptyList()
)

data class PremiumSubscription(
    val planDuration: String,
    val price: String,
    val subscriptionStartDate: String
)

data class Notification(
    val message: String,
    val timestamp: String,
    val targetUser: String,
    val isRead: Boolean = false
)

object DummyData {
    // Array of drawable resource IDs for cyclic mapping
    private val coverImages = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5,
        R.drawable.image6,
        R.drawable.image7
    )

    private val comics = mutableStateListOf(
        // Classic Comics (50 comics: 10 per genre)
        // Superhero (Classic)
        Comic(1, "Superhero Classic 1", coverImages[0], 4.5f, "Superhero", "Classic", 299, 1200),
        Comic(2, "Superhero Classic 2", coverImages[1], 4.2f, "Superhero", "Classic", 349, 900),
        Comic(3, "Superhero Classic 3", coverImages[2], 4.7f, "Superhero", "Classic", 319, 1300),
        Comic(4, "Superhero Classic 4", coverImages[3], 4.3f, "Superhero", "Classic", 329, 1100),
        Comic(5, "Superhero Classic 5", coverImages[4], 4.6f, "Superhero", "Classic", 339, 1400),
        Comic(6, "Superhero Classic 6", coverImages[5], 4.4f, "Superhero", "Classic", 309, 1000),
        Comic(7, "Superhero Classic 7", coverImages[6], 4.8f, "Superhero", "Classic", 359, 1500),
        Comic(8, "Superhero Classic 8", coverImages[0], 4.1f, "Superhero", "Classic", 299, 950),
        Comic(9, "Superhero Classic 9", coverImages[1], 4.5f, "Superhero", "Classic", 319, 1250),
        Comic(10, "Superhero Classic 10", coverImages[2], 4.9f, "Superhero", "Classic", 369, 1600),

        // Action (Classic)
        Comic(11, "Action Classic 1", coverImages[3], 4.0f, "Action", "Classic", 279, 1500),
        Comic(12, "Action Classic 2", coverImages[4], 4.3f, "Action", "Classic", 319, 800),
        Comic(13, "Action Classic 3", coverImages[5], 4.6f, "Action", "Classic", 299, 1200),
        Comic(14, "Action Classic 4", coverImages[6], 4.2f, "Action", "Classic", 309, 900),
        Comic(15, "Action Classic 5", coverImages[0], 4.7f, "Action", "Classic", 329, 1300),
        Comic(16, "Action Classic 6", coverImages[1], 4.4f, "Action", "Classic", 339, 1100),
        Comic(17, "Action Classic 7", coverImages[2], 4.8f, "Action", "Classic", 349, 1400),
        Comic(18, "Action Classic 8", coverImages[3], 4.1f, "Action", "Classic", 289, 950),
        Comic(19, "Action Classic 9", coverImages[4], 4.5f, "Action", "Classic", 319, 1250),
        Comic(20, "Action Classic 10", coverImages[5], 4.9f, "Action", "Classic", 359, 1600),

        // Adventure (Classic)
        Comic(21, "Adventure Classic 1", coverImages[6], 4.7f, "Adventure", "Classic", 399, 2000),
        Comic(22, "Adventure Classic 2", coverImages[0], 4.1f, "Adventure", "Classic", 289, 600),
        Comic(23, "Adventure Classic 3", coverImages[1], 4.4f, "Adventure", "Classic", 339, 1100),
        Comic(24, "Adventure Classic 4", coverImages[2], 4.8f, "Adventure", "Classic", 359, 1400),
        Comic(25, "Adventure Classic 5", coverImages[3], 4.2f, "Adventure", "Classic", 299, 950),
        Comic(26, "Adventure Classic 6", coverImages[4], 4.6f, "Adventure", "Classic", 319, 1300),
        Comic(27, "Adventure Classic 7", coverImages[5], 4.3f, "Adventure", "Classic", 329, 1200),
        Comic(28, "Adventure Classic 8", coverImages[6], 4.9f, "Adventure", "Classic", 369, 1500),
        Comic(29, "Adventure Classic 9", coverImages[0], 4.1f, "Adventure", "Classic", 289, 900),
        Comic(30, "Adventure Classic 10", coverImages[1], 4.7f, "Adventure", "Classic", 349, 1350),

        // Mystery (Classic)
        Comic(31, "Mystery Classic 1", coverImages[2], 4.4f, "Mystery", "Classic", 329, 1100),
        Comic(32, "Mystery Classic 2", coverImages[3], 4.6f, "Mystery", "Classic", 359, 1400),
        Comic(33, "Mystery Classic 3", coverImages[4], 4.2f, "Mystery", "Classic", 299, 950),
        Comic(34, "Mystery Classic 4", coverImages[5], 4.8f, "Mystery", "Classic", 319, 1300),
        Comic(35, "Mystery Classic 5", coverImages[6], 4.5f, "Mystery", "Classic", 339, 1200),
        Comic(36, "Mystery Classic 6", coverImages[0], 4.7f, "Mystery", "Classic", 349, 1400),
        Comic(37, "Mystery Classic 7", coverImages[1], 4.3f, "Mystery", "Classic", 309, 1000),
        Comic(38, "Mystery Classic 8", coverImages[2], 4.9f, "Mystery", "Classic", 369, 1500),
        Comic(39, "Mystery Classic 9", coverImages[3], 4.1f, "Mystery", "Classic", 289, 900),
        Comic(40, "Mystery Classic 10", coverImages[4], 4.6f, "Mystery", "Classic", 329, 1250),

        // Fantasy (Classic)
        Comic(41, "Fantasy Classic 1", coverImages[5], 4.8f, "Fantasy", "Classic", 399, 1700),
        Comic(42, "Fantasy Classic 2", coverImages[6], 4.2f, "Fantasy", "Classic", 339, 950),
        Comic(43, "Fantasy Classic 3", coverImages[0], 4.5f, "Fantasy", "Classic", 369, 1300),
        Comic(44, "Fantasy Classic 4", coverImages[1], 4.7f, "Fantasy", "Classic", 389, 1400),
        Comic(45, "Fantasy Classic 5", coverImages[2], 4.3f, "Fantasy", "Classic", 359, 1200),
        Comic(46, "Fantasy Classic 6", coverImages[3], 4.6f, "Fantasy", "Classic", 379, 1500),
        Comic(47, "Fantasy Classic 7", coverImages[4], 4.4f, "Fantasy", "Classic", 349, 1100),
        Comic(48, "Fantasy Classic 8", coverImages[5], 4.9f, "Fantasy", "Classic", 399, 1600),
        Comic(49, "Fantasy Classic 9", coverImages[6], 4.1f, "Fantasy", "Classic", 329, 1000),
        Comic(50, "Fantasy Classic 10", coverImages[0], 4.5f, "Fantasy", "Classic", 369, 1350),

        // Modern Comics (50 comics: 10 per genre)
        // Superhero (Modern)
        Comic(51, "Superhero Modern 1", R.drawable.ic_launcher_background, 4.8f, "Superhero", "Modern", 499, 3000),
        Comic(52, "Superhero Modern 2", R.drawable.ic_launcher_background, 4.6f, "Superhero", "Modern", 459, 1800),
        Comic(53, "Superhero Modern 3", R.drawable.ic_launcher_background, 4.2f, "Superhero", "Modern", 439, 1500),
        Comic(54, "Superhero Modern 4", R.drawable.ic_launcher_background, 4.7f, "Superhero", "Modern", 469, 2000),
        Comic(55, "Superhero Modern 5", R.drawable.ic_launcher_background, 4.4f, "Superhero", "Modern", 449, 1700),
        Comic(56, "Superhero Modern 6", R.drawable.ic_launcher_background, 4.9f, "Superhero", "Modern", 479, 2200),
        Comic(57, "Superhero Modern 7", R.drawable.ic_launcher_background, 4.3f, "Superhero", "Modern", 429, 1400),
        Comic(58, "Superhero Modern 8", R.drawable.ic_launcher_background, 4.5f, "Superhero", "Modern", 459, 1600),
        Comic(59, "Superhero Modern 9", R.drawable.ic_launcher_background, 4.1f, "Superhero", "Modern", 419, 1300),
        Comic(60, "Superhero Modern 10", R.drawable.ic_launcher_background, 4.6f, "Superhero", "Modern", 489, 1900),

        // Action (Modern)
        Comic(61, "Action Modern 1", R.drawable.ic_launcher_background, 4.4f, "Action", "Modern", 379, 1100),
        Comic(62, "Action Modern 2", R.drawable.ic_launcher_background, 4.2f, "Action", "Modern", 339, 700),
        Comic(63, "Action Modern 3", R.drawable.ic_launcher_background, 4.7f, "Action", "Modern", 359, 1300),
        Comic(64, "Action Modern 4", R.drawable.ic_launcher_background, 4.3f, "Action", "Modern", 369, 1000),
        Comic(65, "Action Modern 5", R.drawable.ic_launcher_background, 4.8f, "Action", "Modern", 389, 1500),
        Comic(66, "Action Modern 6", R.drawable.ic_launcher_background, 4.5f, "Action", "Modern", 349, 1200),
        Comic(67, "Action Modern 7", R.drawable.ic_launcher_background, 4.9f, "Action", "Modern", 399, 1700),
        Comic(68, "Action Modern 8", R.drawable.ic_launcher_background, 4.1f, "Action", "Modern", 329, 900),
        Comic(69, "Action Modern 9", R.drawable.ic_launcher_background, 4.6f, "Action", "Modern", 369, 1400),
        Comic(70, "Action Modern 10", R.drawable.ic_launcher_background, 4.4f, "Action", "Modern", 379, 1600),

        // Adventure (Modern)
        Comic(71, "Adventure Modern 1", R.drawable.ic_launcher_background, 4.9f, "Adventure", "Modern", 599, 2500),
        Comic(72, "Adventure Modern 2", R.drawable.ic_launcher_background, 4.5f, "Adventure", "Modern", 429, 1300),
        Comic(73, "Adventure Modern 3", R.drawable.ic_launcher_background, 4.2f, "Adventure", "Modern", 439, 1400),
        Comic(74, "Adventure Modern 4", R.drawable.ic_launcher_background, 4.7f, "Adventure", "Modern", 469, 1800),
        Comic(75, "Adventure Modern 5", R.drawable.ic_launcher_background, 4.4f, "Adventure", "Modern", 449, 1500),
        Comic(76, "Adventure Modern 6", R.drawable.ic_launcher_background, 4.8f, "Adventure", "Modern", 479, 2000),
        Comic(77, "Adventure Modern 7", R.drawable.ic_launcher_background, 4.3f, "Adventure", "Modern", 459, 1600),
        Comic(78, "Adventure Modern 8", R.drawable.ic_launcher_background, 4.6f, "Adventure", "Modern", 489, 1900),
        Comic(79, "Adventure Modern 9", R.drawable.ic_launcher_background, 4.1f, "Adventure", "Modern", 419, 1200),
        Comic(80, "Adventure Modern 10", R.drawable.ic_launcher_background, 4.5f, "Adventure", "Modern", 469, 1700),

        // SciFi (Modern)
        Comic(81, "SciFi Modern 1", R.drawable.ic_launcher_background, 4.7f, "SciFi", "Modern", 479, 2200),
        Comic(82, "SciFi Modern 2", R.drawable.ic_launcher_background, 4.3f, "SciFi", "Modern", 439, 1600),
        Comic(83, "SciFi Modern 3", R.drawable.ic_launcher_background, 4.8f, "SciFi", "Modern", 459, 1900),
        Comic(84, "SciFi Modern 4", R.drawable.ic_launcher_background, 4.4f, "SciFi", "Modern", 469, 1700),
        Comic(85, "SciFi Modern 5", R.drawable.ic_launcher_background, 4.9f, "SciFi", "Modern", 489, 2000),
        Comic(86, "SciFi Modern 6", R.drawable.ic_launcher_background, 4.2f, "SciFi", "Modern", 429, 1400),
        Comic(87, "SciFi Modern 7", R.drawable.ic_launcher_background, 4.6f, "SciFi", "Modern", 449, 1800),
        Comic(88, "SciFi Modern 8", R.drawable.ic_launcher_background, 4.5f, "SciFi", "Modern", 469, 1600),
        Comic(89, "SciFi Modern 9", R.drawable.ic_launcher_background, 4.1f, "SciFi", "Modern", 419, 1300),
        Comic(90, "SciFi Modern 10", R.drawable.ic_launcher_background, 4.7f, "SciFi", "Modern", 479, 1950),

        // Fantasy (Modern)
        Comic(91, "Fantasy Modern 1", R.drawable.ic_launcher_background, 4.6f, "Fantasy", "Modern", 499, 1900),
        Comic(92, "Fantasy Modern 2", R.drawable.ic_launcher_background, 4.4f, "Fantasy", "Modern", 459, 1250),
        Comic(93, "Fantasy Modern 3", R.drawable.ic_launcher_background, 4.8f, "Fantasy", "Modern", 479, 1700),
        Comic(94, "Fantasy Modern 4", R.drawable.ic_launcher_background, 4.2f, "Fantasy", "Modern", 439, 1400),
        Comic(95, "Fantasy Modern 5", R.drawable.ic_launcher_background, 4.5f, "Fantasy", "Modern", 459, 1600),
        Comic(96, "Fantasy Modern 6", R.drawable.ic_launcher_background, 4.7f, "Fantasy", "Modern", 469, 1800),
        Comic(97, "Fantasy Modern 7", R.drawable.ic_launcher_background, 4.3f, "Fantasy", "Modern", 449, 1500),
        Comic(98, "Fantasy Modern 8", R.drawable.ic_launcher_background, 4.9f, "Fantasy", "Modern", 489, 2000),
        Comic(99, "Fantasy Modern 9", R.drawable.ic_launcher_background, 4.1f, "Fantasy", "Modern", 429, 1300),
        Comic(100, "Fantasy Modern 10", R.drawable.ic_launcher_background, 4.6f, "Fantasy", "Modern", 469, 1650)
    )

    private val episodeSocialDataMap = mutableMapOf<String, EpisodeSocialData>()
    private var isSubscribed = false
    private var premiumSubscription: PremiumSubscription? = null
    private val purchaseConfirmations = mutableMapOf<Int, Boolean>()
    private val communityPosts = mutableStateListOf<CommunityPost>()
    private var postIdCounter = 1
    private val favoriteComicIds: SnapshotStateList<Int> = mutableStateListOf()
    private val favoriteMotionComicIds: SnapshotStateList<Int> = mutableStateListOf()
    private val pollVotesMap = mutableStateMapOf<Int, MutableList<Int>>()
    private val votedOptionMap = mutableStateMapOf<Int, Int>()
    private val notifications = mutableStateListOf<Notification>()

    private var userIdCounter: Long = 102342

    private var userProfile = UserProfile(
        userId = userIdCounter++,
        username = "Guest",
        email = "guest@example.com",
        profilePictureResourceId = R.drawable.ic_launcher_background,
        profilePictureBitmap = null,
        aboutMe = "",
        alfaCoins = 0,
        followers = emptyList(),
        following = listOf("SuperheroLover", "FantasyReader", "ActionHero")
    )

    var isLoggedIn: Boolean = false
    private val registeredUsers = mutableMapOf<String, String>().apply {
        put("comicfan123@example.com", "password123")
        put("superherolover@example.com", "super123")
        put("fantasyreader@example.com", "fantasy123")
        put("admin@example.com", "112233")
        put("actionhero@example.com", "action123")
        put("adventureseeker@example.com", "adventure123")
        put("mysterysolver@example.com", "mystery123")
        put("scifigeek@example.com", "scifi123")
    }

    private val userDetails = mutableMapOf<String, String>().apply {
        put("comicfan123@example.com", "ComicFan123")
        put("superherolover@example.com", "SuperheroLover")
        put("fantasyreader@example.com", "FantasyReader")
        put("admin@example.com", "Admin")
        put("actionhero@example.com", "ActionHero")
        put("adventureseeker@example.com", "AdventureSeeker")
        put("mysterysolver@example.com", "MysterySolver")
        put("scifigeek@example.com", "SciFiGeek")
    }

    private val userProfilePictures = mutableMapOf<String, Int>().apply {
        put("ComicFan123", R.drawable.ic_launcher_background)
        put("SuperheroLover", R.drawable.ic_launcher_background)
        put("FantasyReader", R.drawable.ic_launcher_background)
        put("Admin", R.drawable.ic_launcher_background)
        put("ActionHero", R.drawable.ic_launcher_background)
        put("AdventureSeeker", R.drawable.ic_launcher_background)
        put("MysterySolver", R.drawable.ic_launcher_background)
        put("SciFiGeek", R.drawable.ic_launcher_background)
    }

    private val allUserProfiles = mutableMapOf<String, UserProfile>().apply {
        put("ComicFan123", UserProfile(
            userId = userIdCounter++,
            username = "ComicFan123",
            email = "comicfan123@example.com",
            profilePictureResourceId = R.drawable.ic_launcher_background,
            profilePictureBitmap = null,
            aboutMe = "I love reading superhero comics!",
            alfaCoins = 500,
            followers = listOf("SuperheroLover", "FantasyReader", "Admin"),
            following = listOf("SuperheroLover", "ActionHero"),
            episodesRead = 60,
            badges = listOf(Badge("Copper Star", Color(0xFFB87333)))
        ))
        put("SuperheroLover", UserProfile(
            userId = userIdCounter++,
            username = "SuperheroLover",
            email = "superherolover@example.com",
            profilePictureResourceId = R.drawable.ic_launcher_background,
            profilePictureBitmap = null,
            aboutMe = "Superhero fan forever!",
            alfaCoins = 700,
            followers = listOf("ComicFan123", "AdventureSeeker"),
            following = listOf("ComicFan123", "FantasyReader")
        ))
        put("FantasyReader", UserProfile(
            userId = userIdCounter++,
            username = "FantasyReader",
            email = "fantasyreader@example.com",
            profilePictureResourceId = R.drawable.ic_launcher_background,
            profilePictureBitmap = null,
            aboutMe = "I enjoy fantasy worlds!",
            alfaCoins = 600,
            followers = listOf("ComicFan123", "SuperheroLover"),
            following = listOf("SuperheroLover", "ActionHero"),
            episodesRead = 300,
            badges = listOf(
                Badge("Copper Star", Color(0xFFB87333)),
                Badge("Silver Star", Color(0xFFC0C0C0)),
                Badge("Gold Star", Color(0xFFFFD700))
            )
        ))
        put("Admin", UserProfile(
            userId = userIdCounter++,
            username = "Admin",
            email = "admin@example.com",
            profilePictureResourceId = R.drawable.ic_launcher_background,
            profilePictureBitmap = null,
            aboutMe = "Administrator of Alfa Comics",
            alfaCoins = 1000,
            followers = listOf("ComicFan123"),
            following = listOf("SuperheroLover", "FantasyReader"),
            episodesRead = 600,
            badges = listOf(
                Badge("Copper Star", Color(0xFFB87333)),
                Badge("Silver Star", Color(0xFFC0C0C0)),
                Badge("Gold Star", Color(0xFFFFD700)),
                Badge("Platinum Star", Color(0xFF00C4B4))
            )
        ))
        put("ActionHero", UserProfile(
            userId = userIdCounter++,
            username = "ActionHero",
            email = "actionhero@example.com",
            profilePictureResourceId = R.drawable.ic_launcher_background,
            profilePictureBitmap = null,
            aboutMe = "Action comics are the best!",
            alfaCoins = 550,
            followers = listOf("ComicFan123", "FantasyReader"),
            following = listOf("SuperheroLover", "AdventureSeeker")
        ))
        put("AdventureSeeker", UserProfile(
            userId = userIdCounter++,
            username = "AdventureSeeker",
            email = "adventureseeker@example.com",
            profilePictureResourceId = R.drawable.ic_launcher_background,
            profilePictureBitmap = null,
            aboutMe = "Always seeking adventures in comics!",
            alfaCoins = 650,
            followers = listOf("SuperheroLover"),
            following = listOf("ActionHero", "MysterySolver")
        ))
        put("MysterySolver", UserProfile(
            userId = userIdCounter++,
            username = "MysterySolver",
            email = "mysterysolver@example.com",
            profilePictureResourceId = R.drawable.ic_launcher_background,
            profilePictureBitmap = null,
            aboutMe = "I love solving mysteries in comics!",
            alfaCoins = 500,
            followers = listOf(),
            following = listOf("AdventureSeeker", "SciFiGeek")
        ))
        put("SciFiGeek", UserProfile(
            userId = userIdCounter++,
            username = "SciFiGeek",
            email = "scifigeek@example.com",
            profilePictureResourceId = R.drawable.ic_launcher_background,
            profilePictureBitmap = null,
            aboutMe = "Sci-fi comics are my favorite!",
            alfaCoins = 600,
            followers = listOf(),
            following = listOf("MysterySolver")
        ))
    }

    private var notificationsEnabled = true
    private var selectedLanguage = "en"
    private var referralRewards = 0

    private val userAboutMeState = mutableStateOf(userProfile.aboutMe)

    private val allUsernames = listOf(
        "ComicFan123",
        "SuperheroLover",
        "FantasyReader",
        "ActionHero",
        "AdventureSeeker",
        "MysterySolver",
        "Admin",
        "SciFiGeek"
    )

    private val followedUsernames = listOf(
        "SuperheroLover",
        "FantasyReader",
        "ActionHero",
        "Admin",
        "AdventureSeeker"
    )

    init {
        userProfile = allUserProfiles["Admin"] ?: userProfile
        Log.d("DummyData", "Initial userProfile alfaCoins: ${userProfile.alfaCoins}")
        isLoggedIn = true
        addCommunityPost("Just finished reading Superhero Classic 1! Amazing story!", "placeholder_image_1", null)
        addCommunityPost("Any recommendations for Fantasy comics? #FantasyReader", null, null)
        addCommunityPost(
            content = "Which comic genre do you prefer?",
            imageUrl = null,
            poll = Poll(
                question = "Which comic genre do you prefer?",
                options = listOf(
                    PollOption("Superhero", 0),
                    PollOption("Fantasy", 0)
                )
            )
        )

        userProfile = allUserProfiles["ComicFan123"] ?: userProfile
        Log.d("DummyData", "After ComicFan123, userProfile alfaCoins: ${userProfile.alfaCoins}")
        addCommunityPost("Loved the new Action Classic 2! #ActionFans", "placeholder_image_2", null)
        addCommunityPost("Looking for some Adventure comics recommendations!", null, null)

        userProfile = allUserProfiles["FantasyReader"] ?: userProfile
        Log.d("DummyData", "After FantasyReader, userProfile alfaCoins: ${userProfile.alfaCoins}")
        addCommunityPost("Fantasy Modern 1 is a must-read! #FantasyLovers", "placeholder_image_3", null)

        userProfile = allUserProfiles["Admin"] ?: userProfile
        Log.d("DummyData", "Final userProfile alfaCoins: ${userProfile.alfaCoins}")
    }

    fun getUserProfile(): UserProfile {
        return userProfile.copy(aboutMe = userAboutMeState.value)
    }

    fun getUserProfileByUsername(username: String): UserProfile? {
        return allUserProfiles[username]
    }

    fun getComicsByCategory(category: String): List<Comic> {
        return comics.filter { comic -> comic.category == category }
    }

    fun getComicsByGenreAndCategory(genre: String, category: String): List<Comic> {
        return comics.filter { comic -> comic.genre == genre && comic.category == category }
    }

    fun getGenresByCategory(category: String): List<String> {
        return comics.filter { comic -> comic.category == category }
            .map { comic -> comic.genre }
            .distinct()
    }

    fun getComicById(id: Int): Comic? {
        return comics.find { comic -> comic.id == id }
    }

    fun getEpisodeSocialData(comicId: Int, episodeId: Int): EpisodeSocialData {
        val key = "$comicId-$episodeId"
        return episodeSocialDataMap.getOrPut(key) {
            EpisodeSocialData(comicId, episodeId)
        }
    }

    fun isUserSubscribed(): Boolean {
        return isSubscribed
    }

    fun setUserSubscribed(subscribed: Boolean) {
        isSubscribed = subscribed
        if (!subscribed) {
            premiumSubscription = null
        }
    }

    fun subscribeToPremium(planDuration: String, price: String, startDate: String) {
        isSubscribed = true
        premiumSubscription = PremiumSubscription(
            planDuration = planDuration,
            price = price,
            subscriptionStartDate = startDate
        )
    }

    fun getPremiumSubscription(): PremiumSubscription? {
        return premiumSubscription
    }

    fun getEpisodesWithSubscription(comicId: Int): List<Episode> {
        val comic = getComicById(comicId) ?: return emptyList()
        return if (isSubscribed) {
            comic.episodes.map { episode -> episode.copy(isFree = true) }
        } else {
            comic.episodes
        }
    }

    fun unlockEpisodeWithCoins(comicId: Int, episodeId: Int, coinsRequired: Int = 10): Boolean {
        Log.d("DummyData", "Attempting to unlock episode $episodeId for comic $comicId")
        Log.d("DummyData", "Current alfaCoins: ${userProfile.alfaCoins}, Required: $coinsRequired")

        val comic = getComicById(comicId) ?: return false
        val episode = comic.episodes.find { it.id == episodeId } ?: return false

        if (userProfile.alfaCoins >= coinsRequired) {
            userProfile = userProfile.copy(alfaCoins = userProfile.alfaCoins - coinsRequired)
            allUserProfiles[userProfile.username] = userProfile
            val updatedEpisode = episode.copy(isFree = true)
            updateEpisode(comicId, episodeId, updatedEpisode)
            Log.d("DummyData", "Episode unlocked successfully. New alfaCoins: ${userProfile.alfaCoins}")
            return true
        } else {
            Log.d("DummyData", "Not enough coins to unlock episode")
            return false
        }
    }

    fun updateEpisode(comicId: Int, episodeId: Int, updatedEpisode: Episode) {
        val comicIndex = comics.indexOfFirst { it.id == comicId }
        if (comicIndex != -1) {
            val comic = comics[comicIndex]
            val updatedEpisodes = comic.episodes.map { episode ->
                if (episode.id == episodeId) updatedEpisode else episode
            }
            comics[comicIndex] = comic.copy(episodes = updatedEpisodes)
            Log.d("DummyData", "Episode $episodeId updated for comic $comicId")
        }
    }

    fun getAllComics(): List<Comic> {
        return comics
    }

    fun isComicPurchased(comicId: Int): Boolean {
        return purchaseConfirmations[comicId] ?: false
    }

    fun confirmPurchase(comicId: Int) {
        purchaseConfirmations[comicId] = true
    }

    fun getCommunityPosts(): List<CommunityPost> {
        return communityPosts.sortedByDescending { post -> post.id }
    }

    fun getUserPosts(): List<CommunityPost> {
        val currentUser = userProfile.username
        return communityPosts.filter { post -> post.username == currentUser }
    }

    fun addCommunityPost(content: String, imageUrl: String? = null, poll: Poll? = null) {
        if (!isLoggedIn) return
        val timestamp = "2025-05-18 12:00 PM"
        val postId = postIdCounter++
        communityPosts.add(
            CommunityPost(
                id = postId,
                content = content,
                username = userProfile.username,
                timestamp = timestamp,
                imageUrl = imageUrl,
                poll = poll,
                comments = mutableListOf(),
                reactions = emptyMap(),
                userProfilePictureResourceId = userProfile.profilePictureResourceId
            )
        )
        if (poll != null) {
            pollVotesMap[postId] = MutableList(poll.options.size) { 0 }
        }
    }

    fun addCommentToPost(postId: Int, comment: String) {
        if (!isLoggedIn) return
        val timestamp = "2025-05-18 12:05 PM"
        val post = communityPosts.find { it.id == postId }
        post?.let {
            it.comments.add(
                Comment(
                    username = userProfile.username,
                    content = comment,
                    timestamp = timestamp
                )
            )
        }
    }

    fun addReactionToPost(postId: Int, reactionType: String, userId: String) {
        val post = communityPosts.find { it.id == postId }
        post?.let {
            val currentReactions = it.reactions.toMutableMap()
            val userReactions = currentReactions[reactionType]?.toMutableList() ?: mutableListOf()
            if (userId in userReactions) {
                userReactions.remove(userId)
            } else {
                userReactions.add(userId)
            }
            currentReactions[reactionType] = userReactions
            val index = communityPosts.indexOf(it)
            if (index != -1) {
                communityPosts[index] = it.copy(reactions = currentReactions)
            }
        }
    }

    fun getMockUsernames(): List<String> {
        return followedUsernames
    }

    fun toggleFavoriteComic(comicId: Int) {
        if (favoriteComicIds.contains(comicId)) {
            favoriteComicIds.remove(comicId)
        } else {
            favoriteComicIds.add(comicId)
        }
    }

    fun isFavoriteComic(comicId: Int): Boolean {
        return favoriteComicIds.contains(comicId)
    }

    fun toggleFavoriteMotionComic(motionComicId: Int) {
        if (favoriteMotionComicIds.contains(motionComicId)) {
            favoriteMotionComicIds.remove(motionComicId)
        } else {
            favoriteMotionComicIds.add(motionComicId)
        }
    }

    fun isFavoriteMotionComic(motionComicId: Int): Boolean {
        return favoriteMotionComicIds.contains(motionComicId)
    }

    fun getFavoriteComicIds(): SnapshotStateList<Int> {
        return favoriteComicIds
    }

    fun getFavoriteMotionComicIds(): SnapshotStateList<Int> {
        return favoriteMotionComicIds
    }

    fun getFavoriteComics(): List<Comic> {
        return comics.filter { comic -> favoriteComicIds.contains(comic.id) }
    }

    fun getFavoriteMotionComics(): List<MotionComic> {
        return MotionDummyData.getMotionComics().filter { motionComic -> favoriteMotionComicIds.contains(motionComic.id) }
    }

    fun updateAboutMe(newAboutMe: String) {
        userAboutMeState.value = newAboutMe
        userProfile = userProfile.copy(aboutMe = newAboutMe)
        allUserProfiles[userProfile.username] = userProfile
    }

    fun updateProfilePicture(newPicture: ImageBitmap) {
        userProfile = userProfile.copy(profilePictureBitmap = newPicture)
        allUserProfiles[userProfile.username] = userProfile
    }

    fun updateProfilePicture(newPictureResourceId: Int) {
        userProfile = userProfile.copy(profilePictureResourceId = newPictureResourceId)
        allUserProfiles[userProfile.username] = userProfile
    }

    fun updateUserProfile(newUsername: String, newEmail: String) {
        val oldUsername = userProfile.username
        val currentUserId = userProfile.userId
        userProfile = userProfile.copy(username = newUsername, email = newEmail, userId = currentUserId)
        allUserProfiles.remove(oldUsername)
        allUserProfiles[newUsername] = userProfile
    }

    fun addAlfaCoins(coins: Int) {
        userProfile = userProfile.copy(alfaCoins = userProfile.alfaCoins + coins)
        allUserProfiles[userProfile.username] = userProfile
    }

    fun updateAlfaCoins(newBalance: Int) {
        userProfile = userProfile.copy(alfaCoins = newBalance)
        allUserProfiles[userProfile.username] = userProfile
    }

    fun getNotificationsEnabled(): Boolean = notificationsEnabled

    fun setNotificationsEnabled(enabled: Boolean) {
        notificationsEnabled = enabled
    }

    fun clearUserData() {
        userProfile = UserProfile(
            userId = userIdCounter++,
            username = "Guest",
            email = "guest@example.com",
            profilePictureResourceId = R.drawable.ic_launcher_background,
            profilePictureBitmap = null,
            aboutMe = "",
            alfaCoins = 0,
            followers = emptyList(),
            following = listOf("SuperheroLover", "FantasyReader", "ActionHero")
        )
        userAboutMeState.value = ""
        notificationsEnabled = true
        favoriteComicIds.clear()
        favoriteMotionComicIds.clear()
        purchaseConfirmations.clear()
        selectedLanguage = "en"
        referralRewards = 0
        isLoggedIn = false
        isSubscribed = false
        premiumSubscription = null
    }

    fun getFavoriteComicsCount(): Int {
        return favoriteComicIds.size
    }

    fun getFavoriteMotionComicsCount(): Int {
        return favoriteMotionComicIds.size
    }

    fun getCommunityPostsCount(): Int {
        return communityPosts.size
    }

    fun getPollVotes(postId: Int): MutableList<Int> {
        return pollVotesMap[postId]?.toMutableList() ?: mutableListOf()
    }

    fun setPollVotes(postId: Int, votes: MutableList<Int>) {
        pollVotesMap[postId] = votes
    }

    fun getVotedOptionIndex(postId: Int): Int {
        return votedOptionMap[postId] ?: -1
    }

    fun setVotedOptionIndex(postId: Int, index: Int) {
        votedOptionMap[postId] = index
    }

    fun getSelectedLanguage(): String = selectedLanguage

    fun setSelectedLanguage(languageCode: String) {
        selectedLanguage = languageCode
    }

    fun getReferralRewards(): Int = referralRewards

    fun addReferralRewards(coins: Int) {
        referralRewards += coins
        addAlfaCoins(coins)
    }

    fun loginUser(email: String, password: String): Boolean {
        val storedPassword = registeredUsers[email]
        if (storedPassword != null && storedPassword == password) {
            val username = userDetails[email] ?: "User"
            userProfile = allUserProfiles[username] ?: UserProfile(
                userId = userIdCounter++,
                username = username,
                email = email,
                profilePictureResourceId = R.drawable.ic_launcher_background,
                profilePictureBitmap = null,
                aboutMe = "",
                alfaCoins = 500,
                followers = emptyList(),
                following = listOf("SuperheroLover", "FantasyReader", "ActionHero")
            )
            userAboutMeState.value = userProfile.aboutMe
            isLoggedIn = true
            return true
        }
        return false
    }

    fun signUpUser(fullName: String, email: String, mobileNumber: String, password: String): Boolean {
        if (registeredUsers.containsKey(email)) {
            return false
        }
        registeredUsers[email] = password
        userDetails[email] = fullName
        val newUserProfile = UserProfile(
            userId = userIdCounter++,
            username = fullName,
            email = email,
            profilePictureResourceId = R.drawable.ic_launcher_background,
            profilePictureBitmap = null,
            aboutMe = "",
            alfaCoins = 500,
            followers = emptyList(),
            following = listOf("SuperheroLover", "FantasyReader", "ActionHero")
        )
        userProfile = newUserProfile
        allUserProfiles[fullName] = newUserProfile
        userAboutMeState.value = ""
        return true
    }

    fun followUser(currentUser: String, targetUser: String) {
        if (currentUser == targetUser) return
        val targetProfile = allUserProfiles[targetUser] ?: return
        val currentProfile = allUserProfiles[currentUser] ?: return

        val updatedFollowers = targetProfile.followers.toMutableList().apply {
            if (!contains(currentUser)) add(currentUser)
        }
        allUserProfiles[targetUser] = targetProfile.copy(followers = updatedFollowers)

        val updatedFollowing = currentProfile.following.toMutableList().apply {
            if (!contains(targetUser)) add(targetUser)
        }
        allUserProfiles[currentUser] = currentProfile.copy(following = updatedFollowing)

        if (currentUser == userProfile.username) {
            userProfile = currentProfile.copy(following = updatedFollowing)
        }

        if (allUserProfiles[targetUser]?.username != currentUser) {
            addNotification("${currentProfile.username} has followed you.", targetUser)
        }
    }

    fun unfollowUser(currentUser: String, targetUser: String) {
        if (currentUser == targetUser) return
        val targetProfile = allUserProfiles[targetUser] ?: return
        val currentProfile = allUserProfiles[currentUser] ?: return

        val updatedFollowers = targetProfile.followers.toMutableList().apply {
            remove(currentUser)
        }
        allUserProfiles[targetUser] = targetProfile.copy(followers = updatedFollowers)

        val updatedFollowing = currentProfile.following.toMutableList().apply {
            remove(targetUser)
        }
        allUserProfiles[currentUser] = currentProfile.copy(following = updatedFollowing)

        if (currentUser == userProfile.username) {
            userProfile = currentProfile.copy(following = updatedFollowing)
        }
    }

    fun isUserFollowed(currentUser: String, targetUser: String): Boolean {
        val currentProfile = allUserProfiles[currentUser] ?: return false
        return currentProfile.following.contains(targetUser)
    }

    fun getNotifications(): List<Notification> {
        val currentUserProfile = userProfile
        return notifications.filter { notification ->
            notification.targetUser == currentUserProfile.username
        }
    }

    fun getUnreadNotificationsCount(): Int {
        val currentUserProfile = userProfile
        return notifications.count { notification ->
            notification.targetUser == currentUserProfile.username && !notification.isRead
        }
    }

    fun markNotificationsAsRead() {
        val currentUserProfile = userProfile
        notifications.forEachIndexed { index, notification ->
            if (notification.targetUser == currentUserProfile.username && !notification.isRead) {
                notifications[index] = notification.copy(isRead = true)
            }
        }
    }

    private fun addNotification(message: String, targetUser: String) {
        val timestamp = "2025-05-26 09:45 PM"
        notifications.add(Notification(message, timestamp, targetUser, isRead = false))
    }
}