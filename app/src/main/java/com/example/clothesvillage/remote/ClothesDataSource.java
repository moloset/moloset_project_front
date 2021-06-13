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
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;


public interface ClothesDataSource {


    Single<SingleResponse> checkEmail(SingleRequest request);

    Single<SingleResponse> checkName(SingleRequest request);

    Single<SignUpResponse> userInsert(InsertUser request);

    Single<SingleResponse> userUpdate(UpdateUserRequest request);

    Single<SingleResponse> login(LoginRequest request);

    Single<UserInfoResponse> getUserInfo(SingleRequest request);

    Single<List<ClothesListResponse>> getClothesList(ClothesListRequest request);

    Single<SingleResponse> clothesUpdate(ClothesUpdateRequest request);

    Single<SingleResponse> clothesDelete(ClothesDeleteRequest request);

    Single<ClothesDetailResponse> getClothesDetail(ClothesDetailRequest request);

    Single<SingleResponse> styleInsert(StyleInsertRequest request);

    Single<SingleResponse> styleDelete(StyleDeleteRequest request);

    Single<SingleResponse> styleSelect(StyleSelectRequest request);

    Single<List<StyleListResponse>> getStyleList(StyleListRequest request);

    Single<List<String>> getStyleDetailImages(StylePhotoListRequest request);

    Single<List<UserSelectListResponse>> getUserSelectList(StyleUserSelectRequest request);

    Single<SingleResponse> uploadFile(LinkedHashMap<String, RequestBody> params,
                                      List<MultipartBody.Part> file);

    Single<SingleResponse> tradeInsert(TradeInsertRequest request);

    Single<CurrentWeatherResponse> currentWeather(WeatherRequest request);

    Single<List<HourlyWeatherResponse>> hourlyWeather(WeatherRequest request);

    Single<List<DailyWeatherResponse>> dailyWeather(WeatherRequest request);

    Single<List<TradeListResponse>> getTradeList(TradeListRequest request);

    Single<TradeDetailResponse> getTradeDetail(TradeDetailRequest request);

    Single<SingleResponse> tradeUpdate(TradeUpdateRequest request);

    Single<SingleResponse> tradeDelete(TradeDeleteRequest request);

    Single<ImageResponse> uploadImg(List<MultipartBody.Part> file);

    Single<StyleDetailResponse> getStyleDetail(@Body StyleDetailRequest request);

    Single<ChatRoomResponse> getChatRoom(ChatRoomRequest request);

    Single<SingleResponse> sendMsg(ChatMessageRequest request);

    Single<List<ChatListResponse>> getChatList(ChatListRequest request);

    Single<List<ChatMsgListResponse>> getChatMsgList(ChatMsgListRequest request);

    Single<List<ReplyListResponse>> getReplyList(ReplyListRequest request);

    Single<ReplyListResponse> replyInsert(ReplyInsertRequest request);

}
