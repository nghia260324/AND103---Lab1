package com.example.and103_labs1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity {

    Button btn_logout;
    EditText txt_name,txt_country,txt_population;
    Button btn_writeData;
    RecyclerView recyclerView_city;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<City> listCity = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_logout = findViewById(R.id.btn_logout);
        txt_name = findViewById(R.id.txt_name);
        txt_population = findViewById(R.id.txt_population);
        txt_country = findViewById(R.id.txt_country);
        btn_writeData = findViewById(R.id.btn_writeData);
        recyclerView_city = findViewById(R.id.recyclerView_city);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btn_writeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txt_name.getText().toString().trim();
                String population = txt_population.getText().toString().trim();
                String country = txt_country.getText().toString().trim();
                if (name.isEmpty() || population.isEmpty() || country.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Không được để trống thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                WriteDataToFirestore(new City(UUID.randomUUID().toString(),name,Integer.parseInt(population),country));
            }
        });

        ReadDataFormFirestore();
    }
    private void ReadDataFormFirestore() {
        db.collection("City")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (listCity != null) {
                                listCity.clear();
                            }
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                City city =  document.toObject(City.class);
                                listCity.add(city);
                            }
                            FillCityToRecyclerView();
                        } else {
                        }
                    }
                });
    }
    private void FillCityToRecyclerView() {
        RecyclerViewCityAdapter cityAdapter = new RecyclerViewCityAdapter(this,listCity,this);
        recyclerView_city.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView_city.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView_city.setAdapter(cityAdapter);

    }
    private void WriteDataToFirestore(City city) {
        db.collection("City")
                .add(city)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e("documentReference Id",documentReference.getId());
                        Toast.makeText(HomeActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        txt_name.setText("");
                        txt_population.setText("");
                        txt_country.setText("");
                        ReadDataFormFirestore();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Error documentReference getId ",e.toString());
                    }
                });
    }
}