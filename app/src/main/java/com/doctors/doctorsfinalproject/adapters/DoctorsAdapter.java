package com.doctors.doctorsfinalproject.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doctors.doctorsfinalproject.DoctorsDetailsActivity;
import com.doctors.doctorsfinalproject.MyListener;
import com.doctors.doctorsfinalproject.R;
import com.doctors.doctorsfinalproject.activities.EditActivity;
import com.doctors.doctorsfinalproject.databinding.DoctorsItemBinding;
import com.doctors.doctorsfinalproject.model.AllDoctorsModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.viewHolder> {

    Context context;
    private ArrayList<AllDoctorsModel> list;
    MyListener myListener;

    public DoctorsAdapter(Context context, ArrayList<AllDoctorsModel> list, MyListener myListener) {
        this.context = context;
        this.list = list;
        this.myListener = myListener;
    }


    @NonNull
    @Override
    public DoctorsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DoctorsItemBinding binding = DoctorsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new viewHolder(binding);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull DoctorsAdapter.viewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.doctorName.setText(list.get(position).getName());
        holder.binding.doctorMajor.setText(list.get(position).getTitle());
        holder.binding.description.setText(list.get(position).getDescription());
        Glide.with(context).load(list.get(position).getImage()).placeholder(R.drawable.ic_launcher_background).into((holder).binding.imageDoctor);

        holder.binding.hide.setVisibility(View.GONE);
        holder.binding.edit.setVisibility(View.GONE);
        holder.binding.delete.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(View->{
            Intent intent = new Intent(context, DoctorsDetailsActivity.class);
            intent.putExtra("name", list.get(position).getName());
            intent.putExtra("title", list.get(position).getTitle());
            intent.putExtra("description", list.get(position).getDescription());
            intent.putExtra("image", list.get(position).getImage());
            intent.putExtra("logo", list.get(position).getLogo());
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        DoctorsItemBinding binding;

        public viewHolder(DoctorsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }


}

