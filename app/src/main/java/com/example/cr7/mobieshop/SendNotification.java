package com.example.cr7.mobieshop;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendNotification {

    public void sendNotification(String fcmToken,String title,String bodytest) {
        Gson gson = new Gson();
        Data data = new Data();
        data.setTitle(title);
        data.setBody(bodytest);
        PostRequestData postRequestData = new PostRequestData();
        postRequestData.setTo(fcmToken);
        postRequestData.setData(data);
        String json = gson.toJson(postRequestData);
        String url = "https://fcm.googleapis.com/fcm/send";

        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "key = AAAAYBaKn2s:APA91bHXeVYij3uY9PEMz3cN7KxICEzLuD-fwgdGmdzMuu6n7PnjXjNNzMOfuX_gWogevYBa-oz5DyIyWSNdtcWUdKIzV1ZNdA-b7oqfG-81zwlj0OtGPt6y_BFIWnspe_f3blkMLgSW")
                .post(body)
                .build();


        Callback responseCallBack = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("Fail Message", "fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v("response", response.toString());
            }


        };
        okhttp3.Call call = client.newCall(request);
        call.enqueue(responseCallBack);
    }
}
