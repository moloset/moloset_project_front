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
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;
import retrofit2.http.PartMap;


public class ClothesRemoteDataSource implements ClothesDataSource {

    private static ClothesRemoteDataSource clothesRemoteDataSource = null;

    private ClothesRemoteDataSource() {
    }

    public static ClothesRemoteDataSource getInstance() {
        if (clothesRemoteDataSource == null) {
            clothesRemoteDataSource = new ClothesRemoteDataSource();
        }
        return clothesRemoteDataSource;
    }

    @Override
    public Single<SingleResponse> checkEmail(SingleRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).checkEmail(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<SingleResponse> checkName(SingleRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).checkName(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<SignUpResponse> userInsert(InsertUser request) {
        return RetrofitClient.getClient().create(ClothesApi.class).userInsert(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<SingleResponse> userUpdate(UpdateUserRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).userUpdate(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<SingleResponse> login(LoginRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).login(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<UserInfoResponse> getUserInfo(SingleRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).getUserInfo(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<ClothesListResponse>> getClothesList(ClothesListRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).getClothesList(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<SingleResponse> clothesUpdate(ClothesUpdateRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).clothesUpdate(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<SingleResponse> clothesDelete(ClothesDeleteRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).clothesDelete(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<ClothesDetailResponse> getClothesDetail(ClothesDetailRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).getClothesDetail(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<SingleResponse> styleInsert(StyleInsertRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).styleInsert(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<SingleResponse> styleDelete(StyleDeleteRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).styleDelete(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<SingleResponse> styleSelect(StyleSelectRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).styleSelect(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<StyleListResponse>> getStyleList(StyleListRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).getStyleList(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<String>> getStyleDetailImages(StylePhotoListRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).getStyleDetailImages(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<UserSelectListResponse>> getUserSelectList(StyleUserSelectRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).getUserSelectList(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<SingleResponse> uploadFile(LinkedHashMap<String, RequestBody> params,
                                             List<MultipartBody.Part> file) {
        return RetrofitClient.getClient().create(ClothesApi.class).uploadFile(params, file).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<SingleResponse> tradeInsert(TradeInsertRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).tradeInsert(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<CurrentWeatherResponse> currentWeather(WeatherRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).currentWeather(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<HourlyWeatherResponse>> hourlyWeather(WeatherRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).hourlyWeather(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<DailyWeatherResponse>> dailyWeather(WeatherRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).dailyWeather(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<TradeListResponse>> getTradeList(TradeListRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).getTradeList(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<TradeDetailResponse> getTradeDetail(TradeDetailRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).getTradeDetail(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<SingleResponse> tradeUpdate(TradeUpdateRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).tradeUpdate(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<SingleResponse> tradeDelete(TradeDeleteRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).tradeDelete(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<ImageResponse> uploadImg(List<MultipartBody.Part> file) {
        return RetrofitClient.getClient().create(ClothesApi.class).uploadImg(file).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<StyleDetailResponse> getStyleDetail(StyleDetailRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).getStyleDetail(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<ChatRoomResponse> getChatRoom(ChatRoomRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).getChatRoom(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<SingleResponse> sendMsg(ChatMessageRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).sendMsg(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<ChatListResponse>> getChatList(ChatListRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).getChatList(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<ChatMsgListResponse>> getChatMsgList(ChatMsgListRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).getChatMsgList(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<ReplyListResponse>> getReplyList(ReplyListRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).getReplyList(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<ReplyListResponse> replyInsert(ReplyInsertRequest request) {
        return RetrofitClient.getClient().create(ClothesApi.class).replyInsert(request).subscribeOn(Schedulers.io());
    }
}
