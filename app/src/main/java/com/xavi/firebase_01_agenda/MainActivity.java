package com.xavi.firebase_01_agenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xavi.firebase_01_agenda.entidad.Contacto;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextInputEditText inpu_email;
    TextInputEditText inpu_nombre;
    Button guardar;
    Button mostrar;
    Context com = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inpu_nombre = findViewById(R.id.txt_inp_nombre);
        inpu_email = findViewById(R.id.txt_input_email);
        guardar = findViewById(R.id.btn_grabar);
        mostrar = findViewById(R.id.btn_mostra_recycler);
        RecyclerView rec = findViewById(R.id.recyclerView);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference referencia = db.getReference("Agenda").child("Contactos");
       // Contacto c = new Contacto("Xavier", "Xavier.Robles.R@Gmail.com");
        //referencia.setValue(c);

        ArrayList<Contacto> contactosRecycle = new ArrayList<>();
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contacto c1 = new Contacto();
                c1.setNombre(inpu_nombre.getText().toString());
                c1.setEmail(inpu_email.getText().toString());
                referencia.push().setValue(c1);
            }
        });


    referencia.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Iterable<DataSnapshot> datos = dataSnapshot.getChildren();
            for (DataSnapshot d: datos) {
                String key = dataSnapshot.getKey();
                Contacto contactos = dataSnapshot.getValue(Contacto.class);
                Log.d("DATOS", ":"+key+ contactos);
                contactosRecycle.add(contactos);
            }



        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.d("ERROR", databaseError.getMessage());
        }
    });


mostrar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        RecyclerView.LayoutManager gestor = new LinearLayoutManager(com);
        MiAdaptador adaptador = new MiAdaptador(contactosRecycle);
        rec.setLayoutManager(gestor);
        rec.setAdapter(adaptador);
    }
});




    }



}