package com.keelim.data.api

import com.keelim.data.model.notification.Notification
import com.keelim.data.model.open.Url
import com.keelim.data.response.notification.NotificationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface FreeServices {
    @Headers("Content-Type: application/json")
    @POST("other1")
    fun share(token:String): Response<List<Url>>

    @Headers("Content-Type: application/json")
    @POST("other1")
    fun inject(): Response<Any>

    @Headers("Content-Type: application/json")
    @GET("notifications")
    fun getNotificationList(userId:String): Response<List<NotificationResponse>>

//
//  @Headers("Content-Type: application/json")
//  @POST("other/sue/")
//  fun sue(): Response<UserSampleResponse>
//
//  @Headers("Content-Type: application/json")
//  @POST("other/sue/")
//  fun like(): Response<UserSampleResponse>
//
//  @Headers("Content-Type: application/json")
//  @POST("other/sue/")
//  fun authenticate(): Response<UserSampleResponse>
//
//  @Headers("Content-Type: application/json")
//  @POST("other/sue/")
//  fun share(): Response<UserSampleResponse>
//
//
//  @POST("invite/")
//  fun invite(
//    @Query("uid") uid: String,
//  ): Response<ResultResponse>
//
//  @GET("friends/{myid}")
//  fun getFriends(
//    @Query("myid") id: String,
//  ): Response<FriendsResponse>
//
//
//  @POST
//  fun getCompletedList(): List<Favorite>
//
//  @GET("api/badge/list")
//  suspend fun getAllBadgeList(): Response<BadgeResponse>
//
//  @GET("/api/badgesusers/{userId}")
//  suspend fun getMyBadge(
//    @Path("userId") userId: Int,
//  ): Response<List<BadgeResponse2>>
//
//  @GET("api/notification/select/{userId}")
//  suspend fun getNotificationList(@Path("userId") userId: Int): Response<List<NotificationResponse>>
//
//  @POST("api/user/signup")
//  suspend fun signup(
//    @Body call: SignUpCall,
//  ): Response<AuthResponse>
//
//  @POST("api/user/login")
//  suspend fun login(
//    @Body call: LoginCall,
//  ): Response<AuthResponse>
//
//  @GET("api/challenge/ranking/point")
//  suspend fun getPointRanking(): Response<List<RankingResponse>>
//
//  @GET("api/challenge/ranking/startdate")
//  suspend fun getStartRanking(): Response<List<RankingResponse>>
//
//  @GET("api/category/list")
//  suspend fun category(): Response<List<CategoryResponse>>
//
//  @DELETE("/api/notification/delete/{notificationId}")
//  suspend fun deleteNoti(@Path("notificationId") notificationId: Int): Response<NotificationDeleteResponse>
//
//  @GET("api/challenge/list")
//  suspend fun challengeList(): Response<List<ChallengeResponse>>
//
//
//  @GET("api/challenge/search/title/{searchTitle}")
//  suspend fun search(
//    @Path(value = "searchTitle",
//      encoded = true) query: String,
//  ): Response<List<SearchResponse>>
//
//  @POST("api/challenge/register/new-challenge")
//  suspend fun createChallengeList(
//    @Body call: ChallengeCall,
//  ): Response<ResultResponse>
//
//
//  @GET("api/user/userinfo/{userId}")
//  suspend fun profile(@Path("userId") userId: Int): Response<ProfileResponse>
//
//  @GET("api/challenge/{challengeId}")
//  suspend fun detail(@Path("challengeId") uid: Int): Response<ChallengeResponse>
//
//  @GET("api/challenge/search/user/{userId}")
//  suspend fun getIngList(@Path("userId") userId: Int): Response<List<SearchResponse>>
//
//
//  @GET("api/challenge/opponent/{challengeId}")
//  suspend fun getOpponent(@Path("challengeId") uid: Int): Response<ChallengeResponse>
//
//  @GET("api/article/challenge/{challengeId}")
//  suspend fun article(@Path("challengeId") challenge: Int): Response<List<ArticleResponse>>
//
//
//  @GET("api/comment/article/{articleId}")
//  suspend fun comments(@Path("articleId") article: Int): Response<List<CommentResponse>>
//
//  @POST("api/challenge/register/new-user")
//  suspend fun sign(
//    @Body call: SignCall,
//  ): Response<ResultResponse>
//
//  @POST("api/comment/create")
//  suspend fun writeComment(
//    @Body call: WriteComment,
//  )
//
//
//  @POST("api/article/create")
//  suspend fun upload(
//    @Body call: CreateCall,
//  ): Response<ArticleCreateResponse>
//
//  @GET("api/image/get/article/{articleId}")
//  suspend fun image(@Path("articleId") article: Int): Response<List<String>>
//
//  @Multipart
//  @POST("api/image/save/article")
//  suspend fun imageUpload(
//    @Part articleId: MultipartBody.Part,
//    @Part file: MultipartBody.Part?,
//  )
}
