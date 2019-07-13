package com.example.pascal_pc.baghali.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.pascal_pc.baghali.Network.Api;
import com.example.pascal_pc.baghali.Network.RetrofitClientInstance;
import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.controller.productInfo.ProductInfoActivity;
import com.example.pascal_pc.baghali.dataBase.prefs.UserPrefrences;
import com.example.pascal_pc.baghali.model.product.Product;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PullService extends IntentService {


    private Product mLatesProduct;
    private int mLastIdLive;

    private static final long POLL_INTERVAL = TimeUnit.MINUTES.toMillis(1);

    public PullService() {
        super("PullService");
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, PullService.class);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (!isNetworkAvailableAndConnected())
            return;

        Log.d("test", "received intent: " + intent);
        //Services.pollServerAndSendNotification(this, TAG);
        pollServerAndSendNotigication(PullService.this);
    }

    public static void setServiceAlarm(Context context, boolean isOn, int time) {
        Intent i = newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (isOn) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), POLL_INTERVAL * time, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    public static boolean isAlarmOn(Context context) {
        Intent i = newIntent(context);
        PendingIntent pi = PendingIntent.getService(context,
                0,
                i,
                PendingIntent.FLAG_NO_CREATE);

        return pi != null;
    }
    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo()!= null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();

        return isNetworkConnected;
    }
    public void pollServerAndSendNotigication(Context context) {

//if last id aln != lat id perfence then notification
        // int mLastIdLive=0;
        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    Log.e("test", "is succesful ");
                    List<Product> productList = response.body();
                    
                    mLatesProduct = productList.get(0);
                    mLastIdLive = mLatesProduct.getId();

                    Log.e("test", "id is" + mLastIdLive);
//////////////
                    int savedLastId = UserPrefrences.getPrefLastProductId(PullService.this);
                    if (savedLastId == mLastIdLive) {
                        Log.e("test", "id is" + savedLastId);
                        Intent i = ProductInfoActivity.newIntent(PullService.this, mLastIdLive);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        PendingIntent pi = PendingIntent.getActivity(PullService.this, 0, i, 0);



                        String channelId = getString(R.string.channel_id);
                        @SuppressLint("ResourceAsColor") Notification notification = new NotificationCompat.Builder(PullService.this, channelId)
                                .setContentTitle("New offer!!")
                                .setContentText("Check sBuy store, new product for you... ")
                                .setSmallIcon(R.mipmap.ic_slider_zero)
                                .setColor(R.color.Red)
                                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                                .setLights(Color.RED, 3000, 3000)
                                .setContentIntent(pi)
                                .setAutoCancel(true)
                                .build();

                        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(PullService.this);
                        notificationManagerCompat.notify(0, notification);

                        UserPrefrences.setPrefLastProductId(PullService.this, mLastIdLive);

                    } else {
//        send notification


                    }
                    Log.d("alisalek", "sikdi");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("test", "fail" + t.getMessage());
                Toast.makeText(PullService.this, "Connection is failed", Toast.LENGTH_SHORT).show();
            }
        });



    }
}

