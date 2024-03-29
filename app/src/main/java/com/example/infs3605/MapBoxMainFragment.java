package com.example.infs3605;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.turf.TurfConversion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

public class MapBoxMainFragment extends Fragment implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener {
    // variables for adding location layer
    private MapView mapView;
    private MapboxMap mapboxMap;
    // variables for adding location layer
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;
    // variables for calculating and drawing a route
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;
    // variables needed to initialize navigation
    private Button button;
    private Button bottom_popup_button;
    boolean isExpanded = false;
    private LinearLayout layer_available, layer_unavailable;
    private ImageView imageMain, close;
    private CardView cv_one_login;
    private TextView marker_label;
    private TextView label_of_duration;
    private MaterialSpinner category;
    ArrayAdapter<String> arrayAdapter;

    ToggleButton btnEC;
    Button btnAdd;

    private BottomSheetBehavior bottomSheetBehavior;
    private RecyclerView rvMember;
    private Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    Dialog mDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mapbox.getInstance(getActivity(), getString(R.string.mapbox_key_1));
        View view = inflater.inflate(R.layout.activity_map_box_main, container, false);
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        layer_available = view.findViewById(R.id.layer_available);
        layer_unavailable = view.findViewById(R.id.layer_unavailable);
        imageMain = view.findViewById(R.id.imageMain);
        cv_one_login = view.findViewById(R.id.cv_one_login);
        marker_label = view.findViewById(R.id.marker_label);
        label_of_duration = view.findViewById(R.id.label_of_duration);
        close = view.findViewById(R.id.close);
        button = view.findViewById(R.id.startButton);
        bottom_popup_button = view.findViewById(R.id.bottom_popup_button);
        category = view.findViewById(R.id.category);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissDetailView();
            }
        });

        LinearLayout linearLayout = view.findViewById(R.id.design_bottom_sheet);

        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);

        btnEC = view.findViewById(R.id.btnEC);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(getString(R.string.browse_rc));
        arrayList.add(getString(R.string.bondi));
        arrayList.add(getString(R.string.broadway));
        arrayList.add(getString(R.string.darlinghurst));
        arrayList.add(getString(R.string.lane_cove));
        arrayList.add(getString(R.string.manly));
        arrayList.add(getString(R.string.newtown));
        arrayList.add(getString(R.string.parramatta));
        arrayList.add(getString(R.string.randwick));
        arrayList.add(getString(R.string.syd_CBD));
        arrayList.add(getString(R.string.unsw));

        arrayAdapter = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,arrayList);
        category.setAdapter(arrayAdapter);

        category.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(position != 0)
                    showDropDown(item.toString());
            }
        });

        category.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {
            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                category.setHint("Browse Red Cross Stations");
            }
        });

        btnEC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_EXPANDED){
                    btnEC.setChecked(true);
                } else {
                    btnEC.setChecked(false);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        btnAdd = view.findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.setContentView(R.layout.popup_addnewmember);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.show();
            }
        });

        rvMember = view.findViewById(R.id.rvMember);

        layoutManager = new LinearLayoutManager(getActivity());
        rvMember.setLayoutManager(layoutManager);

        mDialog = new Dialog(getActivity());

        View pop = inflater.inflate(R.layout.popup_memberdetail,null);

        Adapter.Listener listener = new Adapter.Listener() {
            @Override
            public void onClick(View view, String name) {
                Intent intent = new Intent(getActivity(),DetailActivity.class);
                //add extended data to intent
                intent.putExtra(DetailActivity.INTENT_MESSAGE, name);

                startActivity(intent);
            }
        };

        adapter = new Adapter(getActivity(),User.getStaticUsers(),listener);

        rvMember.setAdapter(adapter);

        return view;
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {

        this.mapboxMap = mapboxMap;

        List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
        symbolLayerIconFeatureList.addAll(initCoordinateData());

        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cjf4m44iw0uza2spb3q0a7s41")
                // Add the SymbolLayer icon image to the map style
                .withImage("ICON_ID", BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default))
                // Adding a GeoJson source for the SymbolLayer icons.
                .withSource(new GeoJsonSource("SOURCE_ID", FeatureCollection.fromFeatures(symbolLayerIconFeatureList)))
                // Adding the actual SymbolLayer to the map style. An offset is added that the bottom of the red
                // marker icon gets fixed to the coordinate, rather than the middle of the icon being fixed to
                // the coordinate point. This is offset is not always needed and is dependent on the image
                // that you use for the SymbolLayer icon.
                .withLayer(new SymbolLayer("LAYER_ID", "SOURCE_ID")
                        .withProperties(
                                iconImage("ICON_ID"),
                                iconAllowOverlap(true),
                                iconIgnorePlacement(true)
                        )
                ), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                // Map is set up and the style has loaded. Now you can add additional data or make other map adjustments.
                enableLocationComponent(style);
                mapboxMap.addOnMapClickListener(MapBoxMainFragment.this);

                bottom_popup_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isExpanded) {
                            if(currentRoute==null){
                                Toast.makeText(getActivity(),"Failed to retrieve route. please try again!", Toast.LENGTH_LONG).show();
                            }
                            startActivity(new Intent(getActivity(),  MapBoxNavigationActivity.class).putExtra("currentRoute",currentRoute));
                        } else {
                            showDetailUI();
                        }
                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean simulateRoute = true;
                        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                .directionsRoute(currentRoute)
                                .shouldSimulateRoute(simulateRoute)
                                .build();
                        // Call this method with Context from within an Activity
                        NavigationLauncher.startNavigation(getActivity(), options);
                    }
                });
            }
        });
    }

    private List<Feature> initCoordinateData() {
        Feature singleFeatureOne = Feature.fromGeometry( Point.fromLngLat(151.24904, -33.89174));
        singleFeatureOne.addStringProperty("ICON_PROPERTY", "ICON_ID_ONE");

        Feature singleFeatureTwo = Feature.fromGeometry( Point.fromLngLat(151.19432, -33.88418));
        singleFeatureTwo.addStringProperty("ICON_PROPERTY", "ICON_ID_TWO");

        Feature singleFeatureThree = Feature.fromGeometry( Point.fromLngLat(151.21566, -33.88000));
        singleFeatureThree.addStringProperty("ICON_PROPERTY", "ICON_ID_THREE");

        Feature singleFeatureFour = Feature.fromGeometry( Point.fromLngLat(151.16966, -33.81384));
        singleFeatureFour.addStringProperty("ICON_PROPERTY", "ICON_ID_FOUR");

        Feature singleFeatureFive = Feature.fromGeometry( Point.fromLngLat(151.28557, -33.79579));
        singleFeatureFive.addStringProperty("ICON_PROPERTY", "ICON_ID_FIVE");

        Feature singleFeatureSix = Feature.fromGeometry( Point.fromLngLat(151.18742, -33.89231));
        singleFeatureSix.addStringProperty("ICON_PROPERTY", "ICON_ID_SIX");

        Feature singleFeatureSeven = Feature.fromGeometry( Point.fromLngLat(151.00716, -33.82066));
        singleFeatureSeven.addStringProperty("ICON_PROPERTY", "ICON_ID_SEVEN");

        Feature singleFeatureEight = Feature.fromGeometry( Point.fromLngLat(151.24014, -33.91375));
        singleFeatureEight.addStringProperty("ICON_PROPERTY", "ICON_ID_EIGHT");

        Feature singleFeatureNine = Feature.fromGeometry( Point.fromLngLat(151.20553, -33.87382));
        singleFeatureNine.addStringProperty("ICON_PROPERTY", "ICON_ID_NINE");

        Feature singleFeatureTen = Feature.fromGeometry( Point.fromLngLat(151.22005, -33.90975));
        singleFeatureTen.addStringProperty("ICON_PROPERTY", "ICON_ID_TEN");

        List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
        symbolLayerIconFeatureList.add(singleFeatureOne);
        symbolLayerIconFeatureList.add(singleFeatureTwo);
        symbolLayerIconFeatureList.add(singleFeatureThree);
        symbolLayerIconFeatureList.add(singleFeatureFour);
        symbolLayerIconFeatureList.add(singleFeatureFive);
        symbolLayerIconFeatureList.add(singleFeatureSix);
        symbolLayerIconFeatureList.add(singleFeatureSeven);
        symbolLayerIconFeatureList.add(singleFeatureEight);
        symbolLayerIconFeatureList.add(singleFeatureNine);
        symbolLayerIconFeatureList.add(singleFeatureTen);

        return symbolLayerIconFeatureList;
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        PointF screenPoint = mapboxMap.getProjection().toScreenLocation(point);
        List<Feature> features =  mapboxMap.queryRenderedFeatures(screenPoint,"LAYER_ID");

        if(!features.isEmpty())
        {
            Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(), locationComponent.getLastKnownLocation().getLatitude());
            Point destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
            Feature selectedFeature = features.get(0);
            String title = selectedFeature.getStringProperty("ICON_PROPERTY");
            getRoute(originPoint , destinationPoint);
            showDetailView(title);
        }
        Timber.d("===> ");
        return true;
    }

    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(getActivity())
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        // You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }
                        currentRoute = response.body().routes().get(0);
                        Log.d("Duration ==> ", currentRoute.duration().toString());
                        Log.d("Distance ==> ", currentRoute.distance().toString());

                        String duration = String.valueOf(TimeUnit.SECONDS.toMinutes(currentRoute.duration().longValue()));
                        String finalConvertedFormattedDistance = String.valueOf(new DecimalFormat("#.##")
                                .format(TurfConversion.convertLength(currentRoute.distance(),"meters","kilometres")));
                        label_of_duration.setText(duration + " Mins " + " / " + finalConvertedFormattedDistance + " Kms");

                        // Draw the route on the map
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(getActivity())) {
            // Activate the MapboxMap LocationComponent to show user location
            // Adding in LocationComponentOptions is also an optional parameter

            //Create and customize the LocationComponent's options
            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(getActivity())
                    .elevation(5)
                    .accuracyAlpha(.6f)
                    .accuracyColor(Color.RED)
                    .foregroundDrawable(R.drawable.ic_star_marker)
                    .build();
            locationComponent = mapboxMap.getLocationComponent();

            LocationComponentActivationOptions locationComponentActivationOptions = LocationComponentActivationOptions.builder(getActivity(),loadedMapStyle)
                    .locationComponentOptions(customLocationComponentOptions).build();

            locationComponent.activateLocationComponent(locationComponentActivationOptions);
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Toast.makeText(getActivity(), "onRequestPermissionsResult", Toast.LENGTH_LONG).show();
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(getActivity(), R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        Toast.makeText(getActivity(),granted + "", Toast.LENGTH_LONG).show();

        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded(){
                @Override
                public void onStyleLoaded(@NonNull Style style){
                    enableLocationComponent(mapboxMap.getStyle());
                }
            });
            //enableLocationComponent(mapboxMap.getStyle());
        } else {
            Toast.makeText(getActivity(), R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            //finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void showDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Designation")
                .setMessage("Address: " + "Home" + "\n" + "Telephone: ")
                .setPositiveButton("Ir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    private void showDetailUI() {
        isExpanded = true;
        bottom_popup_button.setText("Guide Me");
        layer_available.setVisibility(View.VISIBLE);
        layer_unavailable.setVisibility(View.VISIBLE);
        imageMain.setVisibility(View.VISIBLE);
    }

    private void hideDetailUI() {
        isExpanded = false;
        bottom_popup_button.setText("View Details");
        layer_available.setVisibility(View.GONE);
        layer_unavailable.setVisibility(View.GONE);
        imageMain.setVisibility(View.GONE);
    }

    private void dismissDetailView() {
        hideDetailUI();
        cv_one_login.setVisibility(View.GONE);
    }

    private void showDetailView(String title) {
        if(currentRoute != null){
            Log.e("","==Duration==>" + currentRoute.duration());
        }
        cv_one_login.setVisibility(View.VISIBLE);

        if (title.equals("ICON_ID_ONE"))
        {
            marker_label.setText( getString(R.string.bondi));
            imageMain.setImageResource(R.drawable.zstation1);

        } else if (title.equals("ICON_ID_TWO")) {
            marker_label.setText( getString(R.string.broadway));
            imageMain.setImageResource(R.drawable.zstation2);

        } else if (title.equals("ICON_ID_THREE")) {
            marker_label.setText( getString(R.string.darlinghurst));
            imageMain.setImageResource(R.drawable.zstation3);

        } else if (title.equals("ICON_ID_FOUR")) {
            marker_label.setText(getString(R.string.lane_cove));
            imageMain.setImageResource(R.drawable.zstation4);

        } else if (title.equals("ICON_ID_FIVE")) {
            marker_label.setText(getString(R.string.manly));
            imageMain.setImageResource(R.drawable.zstation5);

        } else if (title.equals("ICON_ID_SIX")) {
            marker_label.setText(getString(R.string.newtown));
            imageMain.setImageResource(R.drawable.zstation1);

        } else if (title.equals("ICON_ID_SEVEN")) {
            marker_label.setText( getString(R.string.parramatta));
            imageMain.setImageResource(R.drawable.zstation2);

        } else if (title.equals("ICON_ID_EIGHT")) {
            marker_label.setText(getString(R.string.randwick));
            imageMain.setImageResource(R.drawable.zstation3);

        } else if (title.equals("ICON_ID_NINE")) {
            marker_label.setText(getString(R.string.syd_CBD));
            imageMain.setImageResource(R.drawable.zstation4);

        } else  if (title.equals("ICON_ID_TEN")) {
            marker_label.setText(getString(R.string.unsw));
            imageMain.setImageResource(R.drawable.zstation5);
        }
    }

    private void showDropDown(String title) {

        cv_one_login.setVisibility(View.VISIBLE);

        if (title.equals( getString(R.string.bondi)))
        {
            marker_label.setText( getString(R.string.bondi));
            imageMain.setImageResource(R.drawable.zstation1);

        } else if (title.equals(getString(R.string.broadway))) {
            marker_label.setText( getString(R.string.broadway));
            imageMain.setImageResource(R.drawable.zstation2);

        } else if (title.equals( getString(R.string.darlinghurst))) {
            marker_label.setText( getString(R.string.darlinghurst));
            imageMain.setImageResource(R.drawable.zstation3);

        } else if (title.equals(getString(R.string.lane_cove))) {
            marker_label.setText(getString(R.string.lane_cove));
            imageMain.setImageResource(R.drawable.zstation4);

        } else if (title.equals(getString(R.string.manly))) {
            marker_label.setText(getString(R.string.manly));
            imageMain.setImageResource(R.drawable.zstation5);

        } else if (title.equals(getString(R.string.newtown))) {
            marker_label.setText(getString(R.string.newtown));
            imageMain.setImageResource(R.drawable.zstation1);

        } else if (title.equals(getString(R.string.parramatta))) {
            marker_label.setText( getString(R.string.parramatta));
            imageMain.setImageResource(R.drawable.zstation2);

        } else if (title.equals(getString(R.string.randwick))) {
            marker_label.setText(getString(R.string.randwick));
            imageMain.setImageResource(R.drawable.zstation3);

        } else if (title.equals(getString(R.string.syd_CBD))) {
            marker_label.setText(getString(R.string.syd_CBD));
            imageMain.setImageResource(R.drawable.zstation4);

        } else  if (title.equals(getString(R.string.unsw))) {
            marker_label.setText(getString(R.string.unsw));
            imageMain.setImageResource(R.drawable.zstation5);
        }
    }
}