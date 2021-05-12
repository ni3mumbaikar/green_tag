package com.example.greentag.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.activity.CameraConfiguration;
import androidx.camera.activity.PhotoActivity;
import androidx.fragment.app.Fragment;

import com.example.greentag.PlantDetailsActivity;
import com.example.greentag.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import im.delight.android.location.SimpleLocation;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    int PHOTO_REQUEST_CODE = 99;
    SimpleLocation simpleLocation;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        floatingActionButton = getView().findViewById(R.id.fab_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PhotoActivity.class);
                startActivityForResult(i, PHOTO_REQUEST_CODE);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        simpleLocation = new SimpleLocation(getActivity());
        String LOG_TAG = "TESTURI";
        if (resultCode == RESULT_OK && requestCode == PHOTO_REQUEST_CODE) {
            Toast.makeText(getActivity(), String.valueOf(data.hasExtra(CameraConfiguration.IMAGE_URI)), Toast.LENGTH_SHORT).show();
            Bundle bundle = data.getExtras();
            String uri = bundle.get(CameraConfiguration.IMAGE_URI).toString();
            String path = uri.replace("file://", "");
//            Uri fileuri = Uri.parse(path);
            // if we can't access the location yet
            if (!simpleLocation.hasLocationEnabled()) {
                // ask the user to enable location access
                SimpleLocation.openSettings(getActivity());
            } else {
//                double lat = simpleLocation.getLatitude();
//                double longitude = simpleLocation.getLongitude();
                double lat = 19.0750888;
                double longitude = 72.90845;
                Log.d(LOG_TAG, "LAT: " + String.valueOf(lat) + "\nLONG: " + longitude);
                Toast.makeText(getActivity(), String.valueOf(lat), Toast.LENGTH_SHORT).show();

                Intent plantDetailsIntent = new Intent(getActivity(), PlantDetailsActivity.class);
                plantDetailsIntent.putExtra("PATH", path);
                startActivity(plantDetailsIntent);

            }


        }
    }



}