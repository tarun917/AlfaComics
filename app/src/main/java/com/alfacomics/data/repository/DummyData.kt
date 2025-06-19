package com.alfacomics.data.repository

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import com.alfacomics.R
import com.alfacomics.pratilipitv.data.repository.Comic
import com.alfacomics.pratilipitv.data.repository.Episode

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
    private val comics: List<Comic> = listOf(
        Comic(
            comic_id = 1,
            title = "Devi Sati",
            description = "loveable",
            image_url = "http://192.168.1.4:8000/media/comics/images/aaa121_2QD3fre.jpeg",
            genre = "Mystery",
            rating = 4.0f,
            ratings_count = 1,
            read_count = 1,
            price = 100.0,
            episodes = listOf(
                Episode(
                    id = 1,
                    comic_id = 1,
                    title = "Devi Sati Episode 1",
                    image_url = "",
                    pdf_url = "http://192.168.1.4:8000/media/comics/pdfs/HVU_Qg6VQ1V.pdf",
                    is_free = true,
                    is_unlocked = true
                )
            ),
            is_unlocked = false
        ),
        Comic(
            comic_id = 2,
            title = "Murti",
            description = "superb comic and super fantastic",
            image_url = "http://192.168.1.4:8000/media/comics/images/anuragCoFounder.jpeg",
            genre = "Mystery",
            rating = 0.0f,
            ratings_count = 0,
            read_count = 0,
            price = 80.0,
            episodes = listOf(
                Episode(
                    id = 2,
                    comic_id = 2,
                    title = "Devi Sati Episode 1",
                    image_url = "",
                    pdf_url = "http://192.168.1.4:8000/media/comics/pdfs/HVU_yfoLVGt.pdf",
                    is_free = true,
                    is_unlocked = false
                )
            ),
            is_unlocked = false
        ),
        Comic(
            comic_id = 3,
            title = "Alfa Centaury",
            description = "superb story",
            image_url = "http://192.168.1.4:8000/media/comics/images/chandrika_2.png",
            genre = "Romance",
            rating = 0.0f,
            ratings_count = 0,
            read_count = 0,
            price = 90.0,
            episodes = listOf(
                Episode(
                    id = 3,
                    comic_id = 3,
                    title = "Alfa Centaury Episode 1",
                    image_url = "",
                    pdf_url = "http://192.168.1.4:8000/media/comics/pdfs/108799156_8Rmk7ez.pdf",
                    is_free = true,
                    is_unlocked = true
                ),
                Episode(
                    id = 4,
                    comic_id = 3,
                    title = "Alfa Centaury Episode 2",
                    image_url = "",
                    pdf_url = "http://192.168.1.4:8000/media/comics/pdfs/HVU_wQIdk94_EHTmPUj.pdf",
                    is_free = true,
                    is_unlocked = false
                ),
                Episode(
                    id = 5,
                    comic_id = 3,
                    title = "Alfa Centaury Episode 3",
                    image_url = "",
                    pdf_url = "http://192.168.1.4:8000/media/comics/pdfs/HVU_NzS5aNv_nfH8Yg5.pdf",
                    is_free = true,
                    is_unlocked = false
                )
            ),
            is_unlocked = false
        ),
        Comic(
            comic_id = 4,
            title = "Maati",
            description = "superb story",
            image_url = "http://192.168.1.4:8000/media/comics/images/ic_coin_uehQnXw.png",
            genre = "Fantasy",
            rating = 0.0f,
            ratings_count = 0,
            read_count = 0,
            price = 85.0,
            episodes = listOf(
                Episode(
                    id = 6,
                    comic_id = 4,
                    title = "Maati 1",
                    image_url = "",
                    pdf_url = "http://192.168.1.4:8000/media/comics/pdfs/108799156_DmCgSag.pdf",
                    is_free = true,
                    is_unlocked = true
                ),
                Episode(
                    id = 7,
                    comic_id = 4,
                    title = "Maati 2",
                    image_url = "",
                    pdf_url = "http://192.168.1.4:8000/media/comics/pdfs/HVU_8lHblVK.pdf",
                    is_free = true,
                    is_unlocked = true
                ),
                Episode(
                    id = 8,
                    comic_id = 4,
                    title = "Maati 3",
                    image_url = "",
                    pdf_url = "http://192.168.1.4:8000/media/comics/pdfs/108799156_JOPl5Tv.pdf",
                    is_free = true,
                    is_unlocked = true
                )
            ),
            is_unlocked = false
        ),
        Comic(
            comic_id = 5,
            title = "Andhadhun",
            description = "mind blowing",
            image_url = "http://192.168.1.4:8000/media/comics/images/aaa11.jpeg",
            genre = "Mystery",
            rating = 0.0f,
            ratings_count = 0,
            read_count = 0,
            price = 95.0,
            episodes = listOf(
                Episode(
                    id = 9,
                    comic_id = 5,
                    title = "Andhadhun 1",
                    image_url = "",
                    pdf_url = "http://192.168.1.4:8000/media/comics/pdfs/108799156_7kT5VOY.pdf",
                    is_free = true,
                    is_unlocked = true
                ),
                Episode(
                    id = 10,
                    comic_id = 5,
                    title = "Andhadhun 2",
                    image_url = "",
                    pdf_url = "http://192.168.1.4:8000/media/comics/pdfs/108799156_Lu0pxcE.pdf",
                    is_free = true,
                    is_unlocked = false
                )
            ),
            is_unlocked = false
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
    private val notifications = mutableStateListOf<Notification>()

    private var userIdCounter: Long = 102342

    internal var userProfile = UserProfile(
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

    internal val allUserProfiles = mutableMapOf<String, UserProfile>().apply {
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
        addCommunityPost("Just finished reading Superhero Classic 1! Amazing story!", "https://example.com/post1.jpg", null)
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
        addCommunityPost("Loved the new Action Classic 2! #ActionFans", "https://example.com/post2.jpg", null)
        addCommunityPost("Looking for some Adventure comics recommendations!", null, null)

        userProfile = allUserProfiles["FantasyReader"] ?: userProfile
        Log.d("DummyData", "After FantasyReader, userProfile alfaCoins: ${userProfile.alfaCoins}")
        addCommunityPost("Fantasy Modern 1 is a must-read! #FantasyLovers", "https://example.com/post3.jpg", null)

        userProfile = allUserProfiles["Admin"] ?: userProfile
        Log.d("DummyData", "Final userProfile alfaCoins: ${userProfile.alfaCoins}")
    }

    fun getUserProfile(): UserProfile {
        return userProfile.copy(aboutMe = userAboutMeState.value)
    }

    fun getUserProfileByUsername(username: String): UserProfile? {
        return allUserProfiles[username]
    }

    fun getComicById(id: Int): Comic? {
        return comics.find { comic -> comic.comic_id == id }
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
            comic.episodes.map { episode -> episode.copy(is_unlocked = true) }
        } else {
            comic.episodes.mapIndexed { index, episode ->
                if (index < 5) episode.copy(is_unlocked = true) else episode
            }
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
            val updatedEpisode = episode.copy(is_unlocked = true)
            updateEpisode(comicId, episodeId, updatedEpisode)
            Log.d("DummyData", "Episode unlocked successfully. New alfaCoins: ${userProfile.alfaCoins}")
            return true
        } else {
            Log.d("DummyData", "Not enough coins to unlock episode")
            return false
        }
    }

    fun updateEpisode(comicId: Int, episodeId: Int, updatedEpisode: Episode) {
        val comic = getComicById(comicId) ?: return
        val updatedEpisodes = comic.episodes.map { episode ->
            if (episode.id == episodeId) updatedEpisode else episode
        }
        Log.d("DummyData", "Episode $episodeId updated for comic $comicId")
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
        return comics.filter { comic -> favoriteComicIds.contains(comic.comic_id) }
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

    fun updateUserCoins(newBalance: Int) {
        updateAlfaCoins(newBalance)
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