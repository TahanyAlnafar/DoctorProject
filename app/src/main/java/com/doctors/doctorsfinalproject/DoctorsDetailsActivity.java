package com.doctors.doctorsfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.doctors.doctorsfinalproject.databinding.ActivityDoctorsDetailsBinding;
import com.doctors.doctorsfinalproject.databinding.ActivityEditBinding;

public class DoctorsDetailsActivity extends AppCompatActivity {
    ActivityDoctorsDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorsDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getData();

    }

    private void getData(){

        if (getIntent().getExtras()!=null){
            String name =  getIntent().getStringExtra("name");
            String title =  getIntent().getStringExtra("title");
            String description =  getIntent().getStringExtra("description");
            String image1 =  getIntent().getStringExtra("image");
            String image2 =  getIntent().getStringExtra("logo");

            binding.doctorName.setText(name);
            binding.description.setText(description);
            binding.name.setText(title);

            Glide.with(this).load(image1).placeholder(R.drawable.ic_launcher_background).into(binding.userImage);

        }
    }
}