package com.alfacomics.data.repository

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
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
    val reactions: Map<String, List<String>> = emptyMap() // Added reactions field
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

data class UserProfile(
    val username: String,
    val email: String,
    val profilePictureResourceId: Int,
    val aboutMe: String,
    val alfaCoins: Int
)

object DummyData {
    private val comics = listOf(
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

    private val episodeSocialDataMap = mutableMapOf<String, EpisodeSocialData>()
    private var isSubscribed = false
    private val purchaseConfirmations = mutableMapOf<Int, Boolean>()
    private val communityPosts = mutableStateListOf<CommunityPost>()
    private var postIdCounter = 1
    private val favoriteComicIds: SnapshotStateList<Int> = mutableStateListOf()
    // Map to store votes for each poll post (postId -> votes list)
    private val pollVotesMap = mutableStateMapOf<Int, MutableList<Int>>()
    // Map to store the voted option index for each poll post (postId -> votedOptionIndex)
    private val votedOptionMap = mutableStateMapOf<Int, Int>()

    private val userProfile = UserProfile(
        username = "ComicFan123",
        email = "comicfan123@example.com",
        profilePictureResourceId = R.drawable.ic_launcher_background,
        aboutMe = "Avid comic reader and collector. Love superhero stories!",
        alfaCoins = 500
    )

    private val userAboutMeState = mutableStateOf(userProfile.aboutMe)

    // Mock list of all usernames (for reference)
    private val allUsernames = listOf(
        "ComicFan123",
        "SuperheroLover",
        "FantasyReader",
        "ActionHero",
        "AdventureSeeker",
        "MysterySolver",
        "SciFiGeek"
    )

    // Mock list of usernames that the current user (ComicFan123) follows
    private val followedUsernames = listOf(
        "SuperheroLover",
        "FantasyReader",
        "ActionHero",
        "AdventureSeeker"
    )

    // Initialize some sample community posts with timestamps, images, and polls
    init {
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
    }

    fun getEpisodesWithSubscription(comicId: Int): List<Episode> {
        val comic = getComicById(comicId) ?: return emptyList()
        return if (isSubscribed) {
            comic.episodes.map { episode -> episode.copy(isFree = true) }
        } else {
            comic.episodes
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
        // Sort posts by id in descending order (newest first)
        return communityPosts.sortedByDescending { post -> post.id }
    }

    fun addCommunityPost(content: String, imageUrl: String? = null, poll: Poll? = null) {
        // Mock timestamp (in real app, use actual date/time)
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
                reactions = emptyMap()
            )
        )
        // Initialize votes for the new poll post
        if (poll != null) {
            pollVotesMap[postId] = MutableList(poll.options.size) { 0 }
        }
    }

    fun addCommentToPost(postId: Int, comment: String) {
        // Mock timestamp (in real app, use actual date/time)
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
        // Return only the usernames that the current user follows
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

    fun getFavoriteComicIds(): SnapshotStateList<Int> {
        return favoriteComicIds
    }

    fun getFavoriteComics(): List<Comic> {
        return comics.filter { comic -> favoriteComicIds.contains(comic.id) }
    }

    fun getUserProfile(): UserProfile {
        return userProfile.copy(aboutMe = userAboutMeState.value)
    }

    fun updateAboutMe(newAboutMe: String) {
        userAboutMeState.value = newAboutMe
    }

    fun getFavoriteComicsCount(): Int {
        return favoriteComicIds.size
    }

    fun getCommunityPostsCount(): Int {
        return communityPosts.size
    }

    // Methods to manage poll votes
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

            // Toggle reaction: if user already reacted, remove their reaction; otherwise, add it
            if (userId in userReactions) {
                userReactions.remove(userId)
            } else {
                userReactions.add(userId)
            }
            currentReactions[reactionType] = userReactions

            // Update the post in the list
            val index = communityPosts.indexOf(it)
            if (index != -1) {
                communityPosts[index] = it.copy(reactions = currentReactions)
            }
        }
    }
}