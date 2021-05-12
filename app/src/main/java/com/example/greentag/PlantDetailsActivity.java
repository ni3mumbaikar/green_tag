package com.example.greentag;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.HashMap;

public class PlantDetailsActivity extends AppCompatActivity {

    ImageView plantImage;
    String path, lat, longitude;
    EditText projectField, speciesField, dateField;
    Switch projectSwtich;
    Button submitBtn;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);

        plantImage = findViewById(R.id.plantImageView);
        projectField = findViewById(R.id.projectField);
        dateField = findViewById(R.id.dateField);
        speciesField = findViewById(R.id.speciesField);
        projectSwtich = findViewById(R.id.project_toggle);
        submitBtn = findViewById(R.id.submitBtn);

        path = getIntent().getStringExtra("PATH");
        lat = getIntent().getStringExtra("LAT");
        longitude = getIntent().getStringExtra("LONG");

        File imgFile = new File(path);

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            plantImage.setImageBitmap(myBitmap);
        } else {
            onBackPressed();
        }

        projectSwtich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                projectField.setEnabled(b);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                path = Environment.getExternalStorageDirectory() + File.separator +
                        "/AppName/App_cache/data" + File.separator;

                writeIntoDatabase();
                /*JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("lastModified",  String.valueOf(System.currentTimeMillis()/1000));
                    mCreateAndSaveFile("plant",jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(PlantDetailsActivity.this, mReadJsonData("plant"), Toast.LENGTH_SHORT).show();*/

            }
        });

    }

    private Boolean isValid() {
        String dateString, speciesString, projectString;
        dateString = dateField.getText().toString().trim();
        projectString = projectField.getText().toString().trim();
        speciesString = speciesField.getText().toString().trim();
        if (projectSwtich.isChecked()) {
            if (TextUtils.isEmpty(projectString) || TextUtils.equals(projectString, null)) {
                Toast.makeText(PlantDetailsActivity.this, "Ener Project Name", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "True", Toast.LENGTH_SHORT).show();
                return true;
            }
        } else if (TextUtils.equals(dateString, null) || TextUtils.equals(speciesString, null) ||
                TextUtils.isEmpty(dateString)
                || TextUtils.isEmpty(speciesString)) {
            Toast.makeText(PlantDetailsActivity.this, "Fill all  the details correctly", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "True", Toast.LENGTH_SHORT).show();
            return true;
        }
        Toast.makeText(this, "False", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void writeIntoDatabase() {
        Toast.makeText(this, "Triggered", Toast.LENGTH_SHORT).show();
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getUid());
        String pid = userReference.push().getKey();
        HashMap<String, String> plantdetails = new HashMap<>();
        plantdetails.put("species", speciesField.getText().toString().trim());
        plantdetails.put("date", dateField.getText().toString().trim());
        if (projectSwtich.isChecked()) {
            plantdetails.put("project", projectField.getText().toString().trim());
        }
        userReference.child("plant").child(pid).setValue(plantdetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                }
            }
        });

    }

/*    private void writeToLocalJSON() throws IOException {
        File file = new File("/data/data/" + getApplicationContext().getPackageName() + "/plant");
        if (file.exists()){
            JSONObject jsonObject =

        } else {
            JSONObject jsonObject = new JSONObject();
            JSONObject plant = new JSONObject();

                try {
                    String timestamp = String.valueOf(System.currentTimeMillis()/1000);
                    jsonObject.put(timestamp,  String.valueOf(System.currentTimeMillis()/1000));
                    plant.put("species",speciesField.getText().toString().trim());
                    plant.put("dateofplantation",dateField.getText().toString().trim());
                    plant.put("isUnderProject",projectSwtich.isEnabled());
                    plant.put("imagepath", getIntent().getStringExtra("PATH"));
                    if (projectSwtich.isEnabled()){
                        plant.put("project_name",projectField.getText().toString().trim());
                    }
                    mCreateAndSaveFile("plant",jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }


    }*/


    /*public void mCreateAndSaveFile(String params, String mJsonResponse) {
        try {
            FileWriter file = new FileWriter("/data/data/" + getApplicationContext().getPackageName() + "/" + params);
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String mReadJsonData(String params) {
        try {
            File f = new File("/data/data/" + getPackageName() + "/" + params);
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }*/

}