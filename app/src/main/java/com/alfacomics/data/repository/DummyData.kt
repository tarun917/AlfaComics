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
    val coverImageUrl: String,
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

data class HardCopyComic(
    val id: Int,
    val title: String,
    val coverImageUrl: String,
    val price: Int,
    val stockQuantity: Int,
    val rating: Float,
    val ratingsCount: Int,
    val pages: Int,
    val reviews: List<Review>,
    val description: String = "This is a hard copy of a comic book, perfect for collectors and enthusiasts! This is a hard copy of a comic book, perfect for collectors and enthusiasts! This is a hard copy of a comic book, perfect for collectors and enthusiasts!",
    val dimensions: String = "8.5 x 11 inches",
    val weight: String = "300g"
)

data class Review(
    val buyerName: String,
    val comment: String,
    val timestamp: String
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
    val userId: Long,  // Added userId property
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

data class BuyerDetails(
    val comicId: Int,
    val buyerName: String,
    val email: String,
    val mobileNumber: String,
    val address: String,
    val pinCode: String,
    val purchaseTimestamp: String
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
    private val comics = mutableStateListOf(
        // Classic Comics (50 comics: 10 per genre)
        // Superhero (Classic)
        Comic(1, "Superhero Classic 1", "placeholder_url_1", 4.5f, "Superhero", "Classic", 299, 1200),
        Comic(2, "Superhero Classic 2", "placeholder_url_2", 4.2f, "Superhero", "Classic", 349, 900),
        Comic(3, "Superhero Classic 3", "placeholder_url_37", 4.7f, "Superhero", "Classic", 319, 1300),
        Comic(4, "Superhero Classic 4", "placeholder_url_38", 4.3f, "Superhero", "Classic", 329, 1100),
        Comic(5, "Superhero Classic 5", "placeholder_url_39", 4.6f, "Superhero", "Classic", 339, 1400),
        Comic(6, "Superhero Classic 6", "placeholder_url_40", 4.4f, "Superhero", "Classic", 309, 1000),
        Comic(7, "Superhero Classic 7", "placeholder_url_41", 4.8f, "Superhero", "Classic", 359, 1500),
        Comic(8, "Superhero Classic 8", "placeholder_url_42", 4.1f, "Superhero", "Classic", 299, 950),
        Comic(9, "Superhero Classic 9", "placeholder_url_43", 4.5f, "Superhero", "Classic", 319, 1250),
        Comic(10, "Superhero Classic 10", "placeholder_url_44", 4.9f, "Superhero", "Classic", 369, 1600),

        // Action (Classic)
        Comic(11, "Action Classic 1", "placeholder_url_3", 4.0f, "Action", "Classic", 279, 1500),
        Comic(12, "Action Classic 2", "placeholder_url_4", 4.3f, "Action", "Classic", 319, 800),
        Comic(13, "Action Classic 3", "placeholder_url_45", 4.6f, "Action", "Classic", 299, 1200),
        Comic(14, "Action Classic 4", "placeholder_url_46", 4.2f, "Action", "Classic", 309, 900),
        Comic(15, "Action Classic 5", "placeholder_url_47", 4.7f, "Action", "Classic", 329, 1300),
        Comic(16, "Action Classic 6", "placeholder_url_48", 4.4f, "Action", "Classic", 339, 1100),
        Comic(17, "Action Classic 7", "placeholder_url_49", 4.8f, "Action", "Classic", 349, 1400),
        Comic(18, "Action Classic 8", "placeholder_url_50", 4.1f, "Action", "Classic", 289, 950),
        Comic(19, "Action Classic 9", "placeholder_url_51", 4.5f, "Action", "Classic", 319, 1250),
        Comic(20, "Action Classic 10", "placeholder_url_52", 4.9f, "Action", "Classic", 359, 1600),

        // Adventure (Classic)
        Comic(21, "Adventure Classic 1", "placeholder_url_5", 4.7f, "Adventure", "Classic", 399, 2000),
        Comic(22, "Adventure Classic 2", "placeholder_url_6", 4.1f, "Adventure", "Classic", 289, 600),
        Comic(23, "Adventure Classic 3", "placeholder_url_53", 4.4f, "Adventure", "Classic", 339, 1100),
        Comic(24, "Adventure Classic 4", "placeholder_url_54", 4.8f, "Adventure", "Classic", 359, 1400),
        Comic(25, "Adventure Classic 5", "placeholder_url_55", 4.2f, "Adventure", "Classic", 299, 950),
        Comic(26, "Adventure Classic 6", "placeholder_url_56", 4.6f, "Adventure", "Classic", 319, 1300),
        Comic(27, "Adventure Classic 7", "placeholder_url_57", 4.3f, "Adventure", "Classic", 329, 1200),
        Comic(28, "Adventure Classic 8", "placeholder_url_58", 4.9f, "Adventure", "Classic", 369, 1500),
        Comic(29, "Adventure Classic 9", "placeholder_url_59", 4.1f, "Adventure", "Classic", 289, 900),
        Comic(30, "Adventure Classic 10", "placeholder_url_60", 4.7f, "Adventure", "Classic", 349, 1350),

        // Mystery (Classic)
        Comic(31, "Mystery Classic 1", "placeholder_url_13", 4.4f, "Mystery", "Classic", 329, 1100),
        Comic(32, "Mystery Classic 2", "placeholder_url_14", 4.6f, "Mystery", "Classic", 359, 1400),
        Comic(33, "Mystery Classic 3", "placeholder_url_61", 4.2f, "Mystery", "Classic", 299, 950),
        Comic(34, "Mystery Classic 4", "placeholder_url_62", 4.8f, "Mystery", "Classic", 319, 1300),
        Comic(35, "Mystery Classic 5", "placeholder_url_63", 4.5f, "Mystery", "Classic", 339, 1200),
        Comic(36, "Mystery Classic 6", "placeholder_url_64", 4.7f, "Mystery", "Classic", 349, 1400),
        Comic(37, "Mystery Classic 7", "placeholder_url_65", 4.3f, "Mystery", "Classic", 309, 1000),
        Comic(38, "Mystery Classic 8", "placeholder_url_66", 4.9f, "Mystery", "Classic", 369, 1500),
        Comic(39, "Mystery Classic 9", "placeholder_url_67", 4.1f, "Mystery", "Classic", 289, 900),
        Comic(40, "Mystery Classic 10", "placeholder_url_68", 4.6f, "Mystery", "Classic", 329, 1250),

        // Fantasy (Classic)
        Comic(41, "Fantasy Classic 1", "placeholder_url_15", 4.8f, "Fantasy", "Classic", 399, 1700),
        Comic(42, "Fantasy Classic 2", "placeholder_url_16", 4.2f, "Fantasy", "Classic", 339, 950),
        Comic(43, "Fantasy Classic 3", "placeholder_url_21", 4.5f, "Fantasy", "Classic", 369, 1300),
        Comic(44, "Fantasy Classic 4", "placeholder_url_22", 4.7f, "Fantasy", "Classic", 389, 1400),
        Comic(45, "Fantasy Classic 5", "placeholder_url_23", 4.3f, "Fantasy", "Classic", 359, 1200),
        Comic(46, "Fantasy Classic 6", "placeholder_url_24", 4.6f, "Fantasy", "Classic", 379, 1500),
        Comic(47, "Fantasy Classic 7", "placeholder_url_25", 4.4f, "Fantasy", "Classic", 349, 1100),
        Comic(48, "Fantasy Classic 8", "placeholder_url_26", 4.9f, "Fantasy", "Classic", 399, 1600),
        Comic(49, "Fantasy Classic 9", "placeholder_url_27", 4.1f, "Fantasy", "Classic", 329, 1000),
        Comic(50, "Fantasy Classic 10", "placeholder_url_28", 4.5f, "Fantasy", "Classic", 369, 1350),

        // Modern Comics (50 comics: 10 per genre)
        // Superhero (Modern)
        Comic(51, "Superhero Modern 1", "placeholder_url_7", 4.8f, "Superhero", "Modern", 499, 3000),
        Comic(52, "Superhero Modern 2", "placeholder_url_8", 4.6f, "Superhero", "Modern", 459, 1800),
        Comic(53, "Superhero Modern 3", "placeholder_url_69", 4.2f, "Superhero", "Modern", 439, 1500),
        Comic(54, "Superhero Modern 4", "placeholder_url_70", 4.7f, "Superhero", "Modern", 469, 2000),
        Comic(55, "Superhero Modern 5", "placeholder_url_71", 4.4f, "Superhero", "Modern", 449, 1700),
        Comic(56, "Superhero Modern 6", "placeholder_url_72", 4.9f, "Superhero", "Modern", 479, 2200),
        Comic(57, "Superhero Modern 7", "placeholder_url_73", 4.3f, "Superhero", "Modern", 429, 1400),
        Comic(58, "Superhero Modern 8", "placeholder_url_74", 4.5f, "Superhero", "Modern", 459, 1600),
        Comic(59, "Superhero Modern 9", "placeholder_url_75", 4.1f, "Superhero", "Modern", 419, 1300),
        Comic(60, "Superhero Modern 10", "placeholder_url_76", 4.6f, "Superhero", "Modern", 489, 1900),

        // Action (Modern)
        Comic(61, "Action Modern 1", "placeholder_url_9", 4.4f, "Action", "Modern", 379, 1100),
        Comic(62, "Action Modern 2", "placeholder_url_10", 4.2f, "Action", "Modern", 339, 700),
        Comic(63, "Action Modern 3", "placeholder_url_77", 4.7f, "Action", "Modern", 359, 1300),
        Comic(64, "Action Modern 4", "placeholder_url_78", 4.3f, "Action", "Modern", 369, 1000),
        Comic(65, "Action Modern 5", "placeholder_url_79", 4.8f, "Action", "Modern", 389, 1500),
        Comic(66, "Action Modern 6", "placeholder_url_80", 4.5f, "Action", "Modern", 349, 1200),
        Comic(67, "Action Modern 7", "placeholder_url_81", 4.9f, "Action", "Modern", 399, 1700),
        Comic(68, "Action Modern 8", "placeholder_url_82", 4.1f, "Action", "Modern", 329, 900),
        Comic(69, "Action Modern 9", "placeholder_url_83", 4.6f, "Action", "Modern", 369, 1400),
        Comic(70, "Action Modern 10", "placeholder_url_84", 4.4f, "Action", "Modern", 379, 1600),

        // Adventure (Modern)
        Comic(71, "Adventure Modern 1", "placeholder_url_11", 4.9f, "Adventure", "Modern", 599, 2500),
        Comic(72, "Adventure Modern 2", "placeholder_url_12", 4.5f, "Adventure", "Modern", 429, 1300),
        Comic(73, "Adventure Modern 3", "placeholder_url_85", 4.2f, "Adventure", "Modern", 439, 1400),
        Comic(74, "Adventure Modern 4", "placeholder_url_86", 4.7f, "Adventure", "Modern", 469, 1800),
        Comic(75, "Adventure Modern 5", "placeholder_url_87", 4.4f, "Adventure", "Modern", 449, 1500),
        Comic(76, "Adventure Modern 6", "placeholder_url_88", 4.8f, "Adventure", "Modern", 479, 2000),
        Comic(77, "Adventure Modern 7", "placeholder_url_89", 4.3f, "Adventure", "Modern", 459, 1600),
        Comic(78, "Adventure Modern 8", "placeholder_url_90", 4.6f, "Adventure", "Modern", 489, 1900),
        Comic(79, "Adventure Modern 9", "placeholder_url_91", 4.1f, "Adventure", "Modern", 419, 1200),
        Comic(80, "Adventure Modern 10", "placeholder_url_92", 4.5f, "Adventure", "Modern", 469, 1700),

        // SciFi (Modern)
        Comic(81, "SciFi Modern 1", "placeholder_url_17", 4.7f, "SciFi", "Modern", 479, 2200),
        Comic(82, "SciFi Modern 2", "placeholder_url_18", 4.3f, "SciFi", "Modern", 439, 1600),
        Comic(83, "SciFi Modern 3", "placeholder_url_93", 4.8f, "SciFi", "Modern", 459, 1900),
        Comic(84, "SciFi Modern 4", "placeholder_url_94", 4.4f, "SciFi", "Modern", 469, 1700),
        Comic(85, "SciFi Modern 5", "placeholder_url_95", 4.9f, "SciFi", "Modern", 489, 2000),
        Comic(86, "SciFi Modern 6", "placeholder_url_96", 4.2f, "SciFi", "Modern", 429, 1400),
        Comic(87, "SciFi Modern 7", "placeholder_url_97", 4.6f, "SciFi", "Modern", 449, 1800),
        Comic(88, "SciFi Modern 8", "placeholder_url_98", 4.5f, "SciFi", "Modern", 469, 1600),
        Comic(89, "SciFi Modern 9", "placeholder_url_99", 4.1f, "SciFi", "Modern", 419, 1300),
        Comic(90, "SciFi Modern 10", "placeholder_url_100", 4.7f, "SciFi", "Modern", 479, 1950),

        // Fantasy (Modern)
        Comic(91, "Fantasy Modern 1", "placeholder_url_19", 4.6f, "Fantasy", "Modern", 499, 1900),
        Comic(92, "Fantasy Modern 2", "placeholder_url_20", 4.4f, "Fantasy", "Modern", 459, 1250),
        Comic(93, "Fantasy Modern 3", "placeholder_url_29", 4.8f, "Fantasy", "Modern", 479, 1700),
        Comic(94, "Fantasy Modern 4", "placeholder_url_30", 4.2f, "Fantasy", "Modern", 439, 1400),
        Comic(95, "Fantasy Modern 5", "placeholder_url_31", 4.5f, "Fantasy", "Modern", 459, 1600),
        Comic(96, "Fantasy Modern 6", "placeholder_url_32", 4.7f, "Fantasy", "Modern", 469, 1800),
        Comic(97, "Fantasy Modern 7", "placeholder_url_33", 4.3f, "Fantasy", "Modern", 449, 1500),
        Comic(98, "Fantasy Modern 8", "placeholder_url_34", 4.9f, "Fantasy", "Modern", 489, 2000),
        Comic(99, "Fantasy Modern 9", "placeholder_url_35", 4.1f, "Fantasy", "Modern", 429, 1300),
        Comic(100, "Fantasy Modern 10", "placeholder_url_36", 4.6f, "Fantasy", "Modern", 469, 1650)
    )

    private val hardCopyComics = listOf(
        HardCopyComic(
            1,
            "Superhero Classic 1",
            "placeholder_url_1",
            599,
            10,
            4.5f,
            1200,
            120,
            listOf(
                Review("Amit Sharma", "Amazing comic with great artwork!", "2025-05-15 10:00 AM"),
                Review("Priya Singh", "Loved the story, a must-buy for collectors!", "2025-05-16 02:30 PM")
            )
        ),
        HardCopyComic(
            2,
            "Superhero Classic 2",
            "placeholder_url_2",
            649,
            5,
            4.2f,
            900,
            110,
            listOf(
                Review("Rahul Verma", "Good read, but the binding could be better.", "2025-05-14 09:15 AM")
            )
        ),
        HardCopyComic(
            3,
            "Action Classic 1",
            "placeholder_url_3",
            579,
            8,
            4.0f,
            1500,
            130,
            listOf(
                Review("Sneha Gupta", "Thrilling action sequences!", "2025-05-13 11:45 AM"),
                Review("Vikram Rao", "Worth every penny!", "2025-05-12 03:20 PM")
            )
        ),
        HardCopyComic(
            4,
            "Action Classic 2",
            "placeholder_url_4",
            619,
            3,
            4.3f,
            800,
            125,
            listOf(
                Review("Anjali Mehta", "Really enjoyed this one!", "2025-05-11 08:50 AM")
            )
        ),
        HardCopyComic(
            5,
            "Adventure Classic 1",
            "placeholder_url_5",
            799,
            12,
            4.7f,
            2000,
            150,
            listOf(
                Review("Karan Patel", "Epic adventure, great for collectors!", "2025-05-10 01:10 PM"),
                Review("Neha Kapoor", "Loved the storyline!", "2025-05-09 04:00 PM")
            )
        ),
        HardCopyComic(
            6,
            "Adventure Classic 2",
            "placeholder_url_6",
            589,
            7,
            4.1f,
            600,
            115,
            listOf(
                Review("Ravi Sharma", "Good but delivery took time.", "2025-05-08 10:30 AM")
            )
        ),
        HardCopyComic(
            7,
            "Mystery Classic 1",
            "placeholder_url_13",
            629,
            6,
            4.4f,
            1100,
            140,
            listOf(
                Review("Suman Das", "Intriguing plot!", "2025-05-07 02:15 PM")
            )
        ),
        HardCopyComic(
            8,
            "Mystery Classic 2",
            "placeholder_url_14",
            659,
            4,
            4.6f,
            1400,
            135,
            listOf(
                Review("Deepak Jain", "A masterpiece for mystery lovers!", "2025-05-06 09:00 AM"),
                Review("Pooja Reddy", "Highly recommended!", "2025-05-05 05:45 PM")
            )
        ),
        HardCopyComic(
            9,
            "Fantasy Classic 1",
            "placeholder_url_15",
            799,
            9,
            4.8f,
            1700,
            160,
            listOf(
                Review("Arjun Singh", "Magical and captivating!", "2025-05-04 11:20 AM")
            )
        ),
        HardCopyComic(
            10,
            "Fantasy Classic 2",
            "placeholder_url_16",
            639,
            2,
            4.2f,
            950,
            145,
            listOf(
                Review("Meera Nair", "Good fantasy read, worth the price.", "2025-05-03 03:30 PM")
            )
        )
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
    private val buyerDetailsList = mutableStateListOf<BuyerDetails>()
    private val notifications = mutableStateListOf<Notification>()

    // Counter for generating unique user IDs, starting from 102342
    private var userIdCounter: Long = 102342

    private var userProfile = UserProfile(
        userId = userIdCounter++,  // Assign initial userId to Guest
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
            following = listOf("SuperheroLover", "ActionHero")
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
            following = listOf("SuperheroLover", "ActionHero")
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
            following = listOf("SuperheroLover", "FantasyReader")
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
        // Adding sample posts for different users
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

    fun getUserProfileByUsername(username: String): UserProfile? {
        return userProfiles.find { it.username == username }
    }

    private val userProfiles = mutableListOf(
        UserProfile(
            userId = 102342,  // Admin's userId (already assigned above, but explicitly set here)
            username = "Admin",
            email = "admin@alfacomics.com",
            profilePictureResourceId = R.drawable.ic_launcher_background,
            aboutMe = "Lover of comics and superheroes!",
            alfaCoins = 1500,
            followers = listOf("ComicFan123", "FantasyReader"),
            episodesRead = 600,
            badges = listOf(
                Badge("Copper Star", Color(0xFFB87333)),
                Badge("Silver Star", Color(0xFFC0C0C0)),
                Badge("Gold Star", Color(0xFFFFD700)),
                Badge("Platinum Star", Color(0xFF00C4B4))
            )
        ),
        UserProfile(
            userId = 102343,  // ComicFan123's userId
            username = "ComicFan123",
            email = "comicfan123@alfacomics.com",
            profilePictureResourceId = R.drawable.ic_launcher_background,
            aboutMe = "I read comics all day!",
            alfaCoins = 500,
            followers = listOf("Admin"),
            episodesRead = 60,
            badges = listOf(Badge("Copper Star", Color(0xFFB87333)))
        ),
        UserProfile(
            userId = 102344,  // FantasyReader's userId
            username = "FantasyReader",
            email = "fantasyreader@alfacomics.com",
            profilePictureResourceId = R.drawable.ic_launcher_background,
            aboutMe = "Fantasy comics are the best!",
            alfaCoins = 800,
            followers = listOf("Admin"),
            episodesRead = 300,
            badges = listOf(
                Badge("Copper Star", Color(0xFFB87333)),
                Badge("Silver Star", Color(0xFFC0C0C0)),
                Badge("Gold Star", Color(0xFFFFD700))
            )
        )
    )

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

    fun getHardCopyComicById(id: Int): HardCopyComic? {
        return hardCopyComics.find { comic -> comic.id == id }
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

    fun getAllHardCopyComics(): List<HardCopyComic> {
        return hardCopyComics
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
        post?.comments?.add(
            Comment(
                username = userProfile.username,
                content = comment,
                timestamp = timestamp
            )
        )
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

    fun getUserProfile(): UserProfile {
        return userProfile.copy(aboutMe = userAboutMeState.value)
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
        val currentUserId = userProfile.userId  // Preserve the existing userId
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
            userId = userIdCounter++,  // Assign new userId to Guest
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

    fun getReadCountForHardCopyComic(hardCopyComicId: Int): Int {
        return comics.find { it.id == hardCopyComicId }?.readCount ?: 0
    }

    fun saveBuyerDetails(details: BuyerDetails) {
        buyerDetailsList.add(details)
    }

    fun getBuyerDetailsForComic(comicId: Int): List<BuyerDetails> {
        return buyerDetailsList.filter { it.comicId == comicId }
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
            userId = userIdCounter++,  // Assign new userId
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