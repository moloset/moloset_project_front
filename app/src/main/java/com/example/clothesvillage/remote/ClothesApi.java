package com.example.clothesvillage.remote;


import com.example.clothesvillage.remote.request.ChatListRequest;
import com.example.clothesvillage.remote.request.ChatMessageRequest;
import com.example.clothesvillage.remote.request.ChatMsgListRequest;
import com.example.clothesvillage.remote.request.ChatRoomRequest;
import com.example.clothesvillage.remote.request.ClothesDeleteRequest;
import com.example.clothesvillage.remote.request.ClothesDetailRequest;
import com.example.clothesvillage.remote.request.ClothesListRequest;
import com.example.clothesvillage.remote.request.ClothesUpdateRequest;
import com.example.clothesvillage.remote.request.InsertUser;
import com.example.clothesvillage.remote.request.LoginRequest;
import com.example.clothesvillage.remote.request.ReplyInsertRequest;
import com.example.clothesvillage.remote.request.ReplyListRequest;
import com.example.clothesvillage.remote.request.SingleRequest;
import com.example.clothesvillage.remote.request.StyleDeleteRequest;
import com.example.clothesvillage.remote.request.StyleDetailRequest;
import com.example.clothesvillage.remote.request.StyleInsertRequest;
import com.example.clothesvillage.remote.request.StyleListRequest;
import com.example.clothesvillage.remote.request.StylePhotoListRequest;
import com.example.clothesvillage.remote.request.StyleSelectRequest;
import com.example.clothesvillage.remote.request.StyleUserSelectRequest;
import com.example.clothesvillage.remote.request.TradeDeleteRequest;
import com.example.clothesvillage.remote.request.TradeDetailRequest;
import com.example.clothesvillage.remote.request.TradeInsertRequest;
import com.example.clothesvillage.remote.request.TradeListRequest;
import com.example.clothesvillage.remote.request.TradeUpdateRequest;
import com.example.clothesvillage.remote.request.UpdateUserRequest;
import com.example.clothesvillage.remote.request.WeatherRequest;
import com.example.clothesvillage.remote.response.ChatListResponse;
import com.example.clothesvillage.remote.response.ChatMsgListResponse;
import com.example.clothesvillage.remote.response.ChatRoomResponse;
import com.example.clothesvillage.remote.response.ClothesDetailResponse;
import com.example.clothesvillage.remote.response.ClothesListResponse;
import com.example.clothesvillage.remote.response.CurrentWeatherResponse;
import com.example.clothesvillage.remote.response.DailyWeatherResponse;
import com.example.clothesvillage.remote.response.HourlyWeatherResponse;
import com.example.clothesvillage.remote.response.ImageResponse;
import com.example.clothesvillage.remote.response.ReplyInsertResponse;
import com.example.clothesvillage.remote.response.ReplyListResponse;
import com.example.clothesvillage.remote.response.SignUpResponse;
import com.example.clothesvillage.remote.response.SingleResponse;
import com.example.clothesvillage.remote.response.StyleDetailResponse;
import com.example.clothesvillage.remote.response.StyleListResponse;
import com.example.clothesvillage.remote.response.TradeDetailResponse;
import com.example.clothesvillage.remote.response.TradeListResponse;
import com.example.clothesvillage.remote.response.UserInfoResponse;
import com.example.clothesvillage.remote.response.UserSelectListResponse;

import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ClothesApi {
    @Headers("Content-type: application/json")
    @POST("user/checkEmail")
    Single<SingleResponse> checkEmail(@Body SingleRequest request);

    @Headers("Content-type: application/json")
    @POST("user/checkName")
    Single<SingleResponse> checkName(@Body SingleRequest request);

    @Headers("Content-type: application/json")
    @POST("user/userInsert")
    Single<SignUpResponse> userInsert(@Body InsertUser request);

    @Headers("Content-type: application/json")
    @POST("user/userUpdate")
    Single<SingleResponse> userUpdate(@Body UpdateUserRequest request);

    @Headers("Content-type: application/json")
    @POST("user/login")
    Single<SingleResponse> login(@Body LoginRequest request);

    @Headers("Content-type: application/json")
    @POST("user/getUserInfo")
    Single<UserInfoResponse> getUserInfo(@Body SingleRequest request);

    @Headers("Content-type: application/json")
    @POST("style/styleInsert")
    Single<SingleResponse> styleInsert(@Body StyleInsertRequest request);

    @Headers("Content-type: application/json")
    @POST("style/styleDelete")
    Single<SingleResponse> styleDelete(@Body StyleDeleteRequest request);

    @Headers("Content-type: application/json")
    @POST("style/getStyleList")
    Single<List<StyleListResponse>> getStyleList(@Body StyleListRequest request);


    @Headers("Content-type: application/json")
    @POST("style/styleSelect")
    Single<SingleResponse> styleSelect(@Body StyleSelectRequest request);

    @Headers("Content-type: application/json")
    @POST("style/getUserSelectList")
    Single<List<UserSelectListResponse>> getUserSelectList(@Body StyleUserSelectRequest request);

    @Headers("Content-type: application/json")
    @POST("style/getStyleDetail")
    Single<StyleDetailResponse> getStyleDetail(@Body StyleDetailRequest request);


    @Headers("Content-type: application/json")
    @POST("style/getStyleDetailImages")
    Single<List<String>> getStyleDetailImages(@Body StylePhotoListRequest request);


    @Headers("Content-type: application/json")
    @POST("clothes/getClothesList")
    Single<List<ClothesListResponse>> getClothesList(@Body ClothesListRequest request);

    @Headers("Content-type: application/json")
    @POST("clothes/clothesDelete")
    Single<SingleResponse> clothesDelete(@Body ClothesDeleteRequest request);


    @Headers("Content-type: application/json")
    @POST("clothes/clothesUpdate")
    Single<SingleResponse> clothesUpdate(@Body ClothesUpdateRequest request);


    @Headers("Content-type: application/json")
    @POST("clothes/getClothesDetail")
    Single<ClothesDetailResponse> getClothesDetail(@Body ClothesDetailRequest request);


    @Multipart
    @POST("clothes/clothesInsert")
    Single<SingleResponse> uploadFile(@PartMap() LinkedHashMap<String, RequestBody> params,
                                      @Part List<MultipartBody.Part> file);


    @Headers("Content-type: application/json")
    @POST("trade/tradeInsert")
    Single<SingleResponse> tradeInsert(@Body TradeInsertRequest request);

    @Headers("Content-type: application/json")
    @POST("trade/getTradeList")
    Single<List<TradeListResponse>> getTradeList(@Body TradeListRequest request);

    @Headers("Content-type: application/json")
    @POST("trade/getTradeDetail")
    Single<TradeDetailResponse> getTradeDetail(@Body TradeDetailRequest request);

    @Headers("Content-type: application/json")
    @POST("trade/tradeUpdate")
    Single<SingleResponse> tradeUpdate(@Body TradeUpdateRequest request);

    @Headers("Content-type: application/json")
    @POST("trade/tradeDelete")
    Single<SingleResponse> tradeDelete(@Body TradeDeleteRequest request);

    @Headers("Content-type: application/json")
    @POST("weather/currentWeather")
    Single<CurrentWeatherResponse> currentWeather(@Body WeatherRequest request);

    @Headers("Content-type: application/json")
    @POST("weather/hourlyWeather")
    Single<List<HourlyWeatherResponse>> hourlyWeather(@Body WeatherRequest request);

    @Headers("Content-type: application/json")
    @POST("weather/dailyWeather")
    Single<List<DailyWeatherResponse>> dailyWeather(@Body WeatherRequest request);


    @Multipart
    @POST("/img/uploadImg")
    Single<ImageResponse> uploadImg(@Part List<MultipartBody.Part> file);

    @Headers("Content-type: application/json")
    @POST("chat/getChatRoom")
    Single<ChatRoomResponse> getChatRoom(@Body ChatRoomRequest request);

    @Headers("Content-type: application/json")
    @POST("chat/sendMsg")
    Single<SingleResponse> sendMsg(@Body ChatMessageRequest request);

    @Headers("Content-type: application/json")
    @POST("chat/getChatList")
    Single<List<ChatListResponse>> getChatList(@Body ChatListRequest request);

    @Headers("Content-type: application/json")
    @POST("chat/getChatMsgList")
    Single<List<ChatMsgListResponse>> getChatMsgList(@Body ChatMsgListRequest request);


    @Headers("Content-type: application/json")
    @POST("reply/getReplyList")
    Single<List<ReplyListResponse>> getReplyList(@Body ReplyListRequest request);

    @Headers("Content-type: application/json")
    @POST("reply/replyInsert")
    Single<ReplyListResponse> replyInsert(@Body ReplyInsertRequest request);


}
