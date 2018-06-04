package id.noidea.firstblood.api.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import id.noidea.firstblood.R;
import id.noidea.firstblood.api.ApiClient;
import id.noidea.firstblood.api.ApiData;
import id.noidea.firstblood.api.ApiInterface;
import id.noidea.firstblood.db.DbPosting;
import id.noidea.firstblood.db.DbUsers;
import id.noidea.firstblood.model.Notif;
import id.noidea.firstblood.model.Posting;
import id.noidea.firstblood.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//TODO trigger UI whenever UI exist, current implementation just insert to DB
//TODO Notification click + text editing
public class ProcessingService extends Service {

    private static final String TAG = ProcessingService.class.getSimpleName();

    private Timer timer = new Timer();
    private ApiData<List<Notif>> listNotif;
    private SharedPreferences sp;
    private ApiData<List<Posting>> listPost;
    private DbPosting dbP;
    private DbUsers dbU;
    private int notifCount = 0;
    private Users us;



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sp = getSharedPreferences("id.noidea.firstblood.user", MODE_PRIVATE);
        dbP = new DbPosting(this);
        dbU = new DbUsers(this);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getTimeline();
                getNotif();
                setCurrentSync();
            }
        }, 0, 10000);//10 Seconds
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbP.close();
        dbU.close();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Intent intent = new Intent("id.noidea.firstblood.api.service.ProcessingService");
        sendBroadcast(intent);
    }

    private void getNotif(){
        listNotif = new ApiData<>();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        String key = sp.getString("api_key", null);
        String date = sp.getString("last_sync", "0000-00-00 00.00.00");

        Call<ApiData<List<Notif>>> call = apiService.getLatestNotif(key,date);
        call.enqueue(new Callback<ApiData<List<Notif>>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<List<Notif>>> call, @NonNull Response<ApiData<List<Notif>>> response) {
                listNotif = response.body();

                if (listNotif != null) {
                    if (listNotif.getStatus().equals("success")) {
                        if (listNotif.getData()!=null && listNotif.getData().size()>0){
                            for (Notif nf:listNotif.getData()){
                                Posting ps = dbP.getPosting(nf.getId_post());
                                if (ps.getRhesus().equals(us.getRhesus())&& ps.getGoldar().equals(us.getGoldar())&& !ps.getStatus().equals("2")){
                                    notifCount++;
                                    triggerNotif();
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<List<Notif>>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }

        });
    }


    private void getTimeline(){
        dbP.open();
        dbU.open();

        String username = sp.getString("username", "");
        us = dbU.getUser(username);

        listPost = new ApiData<>();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String key = sp.getString("api_key", null);
        String date = sp.getString("last_sync", "0000-00-00 00.00.00");

        Call<ApiData<List<Posting>>> call = apiService.getLatestPosting(key,date);
        call.enqueue(new Callback<ApiData<List<Posting>>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<List<Posting>>> call, @NonNull Response<ApiData<List<Posting>>> response) {
                listPost = response.body();

                if (listPost != null) {
                    if (listPost.getStatus().equals("success")){
                        for (Posting pst : listPost.getData()) {
                            Boolean check = dbP.isInserted(pst.getId_post());
                            if (!check){//if value is not exist
                                //add to list
                                dbP.insertPosting(pst);
                                //map the value
                            }else{
                                //update db
                                dbP.updatePosting(pst);
                                //get index
                                //update list
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<List<Posting>>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private void setCurrentSync(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        SharedPreferences.Editor ed;
        ed = sp.edit();
        ed.putString("last_sync", strDate);
        ed.apply();
    }

    private void triggerNotif(){
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle("First Blood")
                .setContentText("Ada "+notifCount+" orang yang membutuhkan darah anda!")
                .setContentTitle("Bantuanmu dibutuhkan,"+us.getNama().split(" ")[0]+"!")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        if (manager != null) {
            manager.notify(0, notification);
        }
        ringtone.play();
    }

}
