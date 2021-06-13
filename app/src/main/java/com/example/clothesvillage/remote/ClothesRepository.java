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
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public class ClothesRepository implements ClothesDataSource {

    private static ClothesRepository clothesRepository = null;

    private ClothesRemoteDataSource remoteDataSource;

    private ClothesRepository() {
        remoteDataSource = ClothesRemoteDataSource.getInstance();
    }

    public static ClothesRepository getInstance() {
        if (clothesRepository == null) {
            clothesRepository = new ClothesRepository();
        }
        return clothesRepository;
    }

    @Override
    public Single<SingleResponse> checkEmail(SingleRequest request) {
        return remoteDataSource.checkEmail(request);
    }

    @Override
    public Single<SingleResponse> checkName(SingleRequest request) {
        return remoteDataSource.checkEmail(request);
    }

    @Override
    public Single<SignUpResponse> userInsert(InsertUser request) {
        return remoteDataSource.userInsert(request);
    }

    @Override
    public Single<SingleResponse> userUpdate(UpdateUserRequest request) {
        return remoteDataSource.userUpdate(request);
    }

    @Override
    public Single<SingleResponse> login(LoginRequest request) {
        return remoteDataSource.login(request);
    }

    @Override
    public Single<UserInfoResponse> getUserInfo(SingleRequest request) {
        return remoteDataSource.getUserInfo(request);
    }

    @Override
    public Single<List<ClothesListResponse>> getClothesList(ClothesListRequest request) {
        return remoteDataSource.getClothesList(request);
    }

    @Override
    public Single<SingleResponse> clothesUpdate(ClothesUpdateRequest request) {
        return remoteDataSource.clothesUpdate(request);
    }

    @Override
    public Single<SingleResponse> clothesDelete(ClothesDeleteRequest request) {
        return remoteDataSource.clothesDelete(request);
    }

    @Override
    public Single<ClothesDetailResponse> getClothesDetail(ClothesDetailRequest request) {
        return remoteDataSource.getClothesDetail(request);
    }

    @Override
    public Single<SingleResponse> styleInsert(StyleInsertRequest request) {
        return remoteDataSource.styleInsert(request);
    }

    @Override
    public Single<SingleResponse> styleDelete(StyleDeleteRequest request) {
        return remoteDataSource.styleDelete(request);
    }

    @Override
    public Single<SingleResponse> styleSelect(StyleSelectRequest request) {
        return remoteDataSource.styleSelect(request);
    }

    @Override
    public Single<List<StyleListResponse>> getStyleList(StyleListRequest request) {
        return remoteDataSource.getStyleList(request);
    }

    @Override
    public Single<List<String>> getStyleDetailImages(StylePhotoListRequest request) {
        return remoteDataSource.getStyleDetailImages(request);
    }

    @Override
    public Single<List<UserSelectListResponse>> getUserSelectList(StyleUserSelectRequest request) {
        return remoteDataSource.getUserSelectList(request);
    }

    @Override
    public Single<SingleResponse> uploadFile(LinkedHashMap<String, RequestBody> params,
                                             List<MultipartBody.Part> file) {
        return remoteDataSource.uploadFile(params, file);
    }

    @Override
    public Single<SingleResponse> tradeInsert(TradeInsertRequest request) {
        return remoteDataSource.tradeInsert(request);
    }

    @Override
    public Single<CurrentWeatherResponse> currentWeather(WeatherRequest request) {
        return remoteDataSource.currentWeather(request);
    }

    @Override
    public Single<List<HourlyWeatherResponse>> hourlyWeather(WeatherRequest request) {
        return remoteDataSource.hourlyWeather(request);
    }

    @Override
    public Single<List<DailyWeatherResponse>> dailyWeather(WeatherRequest request) {
        return remoteDataSource.dailyWeather(request);
    }

    @Override
    public Single<List<TradeListResponse>> getTradeList(TradeListRequest request) {
        return remoteDataSource.getTradeList(request);
    }

    @Override
    public Single<TradeDetailResponse> getTradeDetail(TradeDetailRequest request) {
        return remoteDataSource.getTradeDetail(request);
    }

    @Override
    public Single<SingleResponse> tradeUpdate(TradeUpdateRequest request) {
        return remoteDataSource.tradeUpdate(request);
    }

    @Override
    public Single<SingleResponse> tradeDelete(TradeDeleteRequest request) {
        return remoteDataSource.tradeDelete(request);
    }

    @Override
    public Single<ImageResponse> uploadImg(List<MultipartBody.Part> file) {
        return remoteDataSource.uploadImg(file);
    }

    @Override
    public Single<StyleDetailResponse> getStyleDetail(StyleDetailRequest request) {
        return remoteDataSource.getStyleDetail(request);
    }

    @Override
    public Single<ChatRoomResponse> getChatRoom(ChatRoomRequest request) {
        return remoteDataSource.getChatRoom(request);
    }

    @Override
    public Single<SingleResponse> sendMsg(ChatMessageRequest request) {
        return remoteDataSource.sendMsg(request);
    }

    @Override
    public Single<List<ChatListResponse>> getChatList(ChatListRequest request) {
        return remoteDataSource.getChatList(request);
    }

    @Override
    public Single<List<ChatMsgListResponse>> getChatMsgList(ChatMsgListRequest request) {
        return remoteDataSource.getChatMsgList(request);
    }

    @Override
    public Single<List<ReplyListResponse>> getReplyList(ReplyListRequest request) {
        return remoteDataSource.getReplyList(request);
    }

    @Override
    public Single<ReplyListResponse> replyInsert(ReplyInsertRequest request) {
        return remoteDataSource.replyInsert(request);
    }
}
