package com.example.infs3605;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.mapbox.api.directions.v5.models.BannerInstructions;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.services.android.navigation.ui.v5.NavigationView;
import com.mapbox.services.android.navigation.ui.v5.NavigationViewOptions;
import com.mapbox.services.android.navigation.ui.v5.OnNavigationReadyCallback;
import com.mapbox.services.android.navigation.ui.v5.listeners.BannerInstructionsListener;
import com.mapbox.services.android.navigation.ui.v5.listeners.InstructionListListener;
import com.mapbox.services.android.navigation.ui.v5.listeners.NavigationListener;
import com.mapbox.services.android.navigation.ui.v5.listeners.RouteListener;
import com.mapbox.services.android.navigation.v5.routeprogress.ProgressChangeListener;
import com.mapbox.services.android.navigation.v5.routeprogress.RouteProgress;
import com.mapbox.services.android.navigation.v5.routeprogress.RouteProgressState;

import timber.log.Timber;

public class MapBoxNavigationActivity extends AppCompatActivity implements ProgressChangeListener, OnNavigationReadyCallback, NavigationListener, InstructionListListener, RouteListener, BannerInstructionsListener {

    NavigationView navigationView;
    DirectionsRoute currentRoute;
    double DISTANCE_IN_METER_REMAINING = 25.00;
    boolean isNotificationFired = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance( MapBoxNavigationActivity.this, getString(R.string.mapbox_key_1));
        setContentView(R.layout.navigation_view);
        navigationView = findViewById(R.id.navigationView);
        navigationView.onCreate(savedInstanceState);
        navigationView.initialize(this);
        currentRoute = (DirectionsRoute) getIntent().getExtras().getSerializable("currentRoute");
    }

    @Override
    public void onProgressChange(Location location, RouteProgress routeProgress) {
        Log.e("onProgressChange", "==>" + routeProgress.currentState());
        Log.e("distanceRemaining", "==>" + routeProgress.distanceRemaining());
        if (routeProgress.currentState() == RouteProgressState.ROUTE_ARRIVED) {
            if (routeProgress.distanceRemaining() < DISTANCE_IN_METER_REMAINING) {
                if (!isNotificationFired)
                    notificationCall();
            }
        }
    }

    @Override
    public void onNavigationReady(boolean isRunning) {

        startNavigation(currentRoute);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        navigationView.onLowMemory();

    }

    @Override
    protected void onStart() {
        super.onStart();
        navigationView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        navigationView.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigationView.onPause();
    }

    @Override
    protected void onDestroy() {
        navigationView.onDestroy();
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        // If the navigation view didn't need to do anything, call super
        if (!navigationView.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        navigationView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        navigationView.onRestoreInstanceState(savedInstanceState);
    }

    private void startNavigation(DirectionsRoute directionsRoute) {
        NavigationViewOptions options = NavigationViewOptions.builder()
                .directionsRoute(directionsRoute)
                .navigationListener(this)
                .shouldSimulateRoute(BuildConfig.DEBUG)
                .progressChangeListener(this)
                .instructionListListener(this)
                .routeListener(this)
                .bannerInstructionsListener(this)
                .build();

        navigationView.startNavigation(options);
        Timber.d("Started with options");
    }

    @Override
    public void onCancelNavigation() {

    }

    @Override
    public void onNavigationFinished() {

    }

    @Override
    public void onNavigationRunning() {

    }

    @Override
    public void onInstructionListVisibilityChanged(boolean visible) {

    }

    @Override
    public boolean allowRerouteFrom(Point offRoutePoint) {
        return true;
    }

    @Override
    public void onOffRoute(Point offRoutePoint) {

    }

    @Override
    public void onRerouteAlong(DirectionsRoute directionsRoute) {

    }

    @Override
    public void onFailedReroute(String errorMessage) {

    }

    @Override
    public void onArrival() {

    }

    @Override
    public BannerInstructions willDisplay(BannerInstructions instructions) {
        return instructions;
    }


    public static final String CHANNNEL_ID = " Channel";
    public static final int NOTIFICATION_ID = 200;
    public static final int REQUEST_CODE_MORE = 100;
    public static final String KEY_INTENT_MORE = "keyintentmore";

    private void notificationCall() {
        isNotificationFired = true;
        PendingIntent morePendingIntent = PendingIntent.getBroadcast(
                MapBoxNavigationActivity.this,
                REQUEST_CODE_MORE,
                new Intent( MapBoxNavigationActivity.this, MapBoxMainFragment.class)
                        .putExtra(KEY_INTENT_MORE, REQUEST_CODE_MORE),
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), android.R.mipmap.sym_def_app_icon))
//                    .setLargeIconLargeIcon(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round), 128, 128, false))
                .setSmallIcon(android.R.drawable.ic_dialog_dialer)
                .setColor(ContextCompat.getColor(this, R.color.teal_200))
                .setContentTitle("You are near to destination!")
                .setAutoCancel(false)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);

            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            mBuilder.setChannelId(CHANNNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Toast.makeText(this, "You will soon be entering a conflict-free zone. This is a safe area!", Toast.LENGTH_LONG).show();

    }
}