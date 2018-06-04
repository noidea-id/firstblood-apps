package id.noidea.firstblood.api.service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ServiceBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(ServiceBroadcast.class.getSimpleName(), "Notification Service Closed");
        context.startService(new Intent(context, ProcessingService.class));;
    }
}