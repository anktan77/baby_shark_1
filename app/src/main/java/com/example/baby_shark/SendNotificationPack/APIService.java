package com.example.baby_shark.SendNotificationPack;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAkab74Ek:APA91bFCynyK0_lgGXF1ocecp6JbD6dbJU5xgr3Sxa8WPN5KtgyScpw4d2Xw8frfn5X5hcH096uiBwh8UwX0ZRbGn8ehphGTiLExqiUsVG_3_j4R1afO9uKc1CT6XVHig3KevpKaHaAf"
    })
    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}
