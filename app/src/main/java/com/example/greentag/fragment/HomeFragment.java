package com.example.greentag.fragment;

import android.content.Intent;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

import com.example.greentag.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    int PHOTO_REQUEST_CODE = 99;

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

        String LOG_TAG = "TESTURI";
        if (resultCode == RESULT_OK && requestCode == PHOTO_REQUEST_CODE) {
            Toast.makeText(getActivity(), String.valueOf(data.hasExtra(CameraConfiguration.IMAGE_URI)), Toast.LENGTH_SHORT).show();
            Bundle bundle = data.getExtras();
            String uri = bundle.get(CameraConfiguration.IMAGE_URI).toString();

            ExifInterface exif = null;
            try {
                exif = new ExifInterface(getActivity().getContentResolver().openInputStream(Uri.parse(uri)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            float[] latLong = new float[2];
            boolean hasLatLong = exif.getLatLong(latLong);
            if (hasLatLong) {
                Toast.makeText(getActivity(), "Latitude: " + latLong[0], Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Longitude: " + latLong[1], Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Not Found Geo Tag", Toast.LENGTH_SHORT).show();
            }

        }
    }

    /*public static void writeFile (File photo, double latitude, double longitude) throws IOException{
        ExifInterface exif = null;

        try{
            Log.v("latiDouble", ""+latitude);
            Log.v("longiDouble", ""+longitude);
            exif = new ExifInterface(photo.getCanonicalPath());
            if (exif != null) {
                double latitu = latitude;
                double longitu = longitude;
                double alat = Math.abs(latitu);
                double along = Math.abs(longitu);
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, stringLati);
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, stringLongi);
                Log.v("latiString", ""+ stringLati);
                Log.v("longiString", ""+ stringLongi);
                exif.saveAttributes();
                String lati = exif.getAttribute (ExifInterface.TAG_GPS_LATITUDE);
                String longi = exif.getAttribute (ExifInterface.TAG_GPS_LONGITUDE);
                Log.v("latiResult", ""+ lati);
                Log.v("longiResult", ""+ longi);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

}