package com.doctors.doctorsfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;

import com.doctors.doctorsfinalproject.activities.AddDataActivity;
import com.doctors.doctorsfinalproject.activities.AllDoctorsActivity;
import com.doctors.doctorsfinalproject.activities.HiddenDataActivity;
import com.doctors.doctorsfinalproject.adapters.AllDoctorsAdapter;
import com.doctors.doctorsfinalproject.adapters.DoctorsAdapter;
import com.doctors.doctorsfinalproject.adapters.DoctorsCategoryAdapter;
import com.doctors.doctorsfinalproject.databinding.ActivityAllDoctorsBinding;
import com.doctors.doctorsfinalproject.databinding.ActivityPatientBinding;
import com.doctors.doctorsfinalproject.databinding.ActivityDoctorsCategoryBinding;
import com.doctors.doctorsfinalproject.databinding.ActivityPatientBinding;
import com.doctors.doctorsfinalproject.model.AllDoctorsModel;
import com.doctors.doctorsfinalproject.model.DoctorsCategoryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

//بتعرض للمريض الموضوعات
public class PatientActivity extends AppCompatActivity {
    ActivityPatientBinding binding;
    DoctorsAdapter adapter;
    ArrayList<AllDoctorsModel> myDataList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getData();
        getAdapter();
        clickListener();

    }

    private void clickListener() {

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchByName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchByName(newText);
                return false;
            }
        });
    }

    private void getData() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference collectionRef = db.collection("AllDoctors");

        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    myDataList = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        AllDoctorsModel myData = document.toObject(AllDoctorsModel.class);
                        myDataList.add(myData);
                    }

                    adapter = new DoctorsAdapter(getBaseContext(), myDataList, new MyListener() {
                        @Override
                        public void delete(String id, int pos) {
                        }
                    });

                    binding.recyclerview.setAdapter(adapter);

                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("TAG", "Error getting documents: ", task.getException());
                }
            }
        });


    }

    private void getAdapter() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        binding.recyclerview.setLayoutManager(layoutManager);

        adapter = new DoctorsAdapter(getBaseContext(), myDataList, new MyListener() {
            @Override
            public void delete(String id, int pos) {
            }

        });


    }

    private void searchByName(String query) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference usersRef = db.collection("AllDoctors");
        Query nameQuery = usersRef.whereGreaterThanOrEqualTo("name", query)
                .whereLessThanOrEqualTo("name", query + "\uf8ff");

        nameQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<AllDoctorsModel> userList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        AllDoctorsModel user = document.toObject(AllDoctorsModel.class);
                        userList.add(user);
                    }

                    adapter = new DoctorsAdapter(getBaseContext(), userList, null);
                    binding.recyclerview.setAdapter(adapter);
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
    }

}