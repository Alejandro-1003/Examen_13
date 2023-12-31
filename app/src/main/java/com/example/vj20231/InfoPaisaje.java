package com.example.vj20231;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vj20231.services.ContactService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoPaisaje extends AppCompatActivity {
    TextView tvNombreContact;
    Button btnEliminar, btnActualizar,bnt;
    ContactService contactService;
    int idContacto;

    private String nombreActual;
    private String imagenActual;
    private String tipoActual;

    private static final int REQUEST_CODE_ACTUALIZAR_CONTACTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_paisaje);

        tvNombreContact = findViewById(R.id.tvNombreContact);
        ImageView ivContactImage = findViewById(R.id.ivContactImage);

        btnEliminar = findViewById((R.id.btnEliminar));
        btnActualizar = findViewById((R.id.btnActualizar));


        nombreActual = getIntent().getStringExtra("nombre");
        imagenActual = getIntent().getStringExtra("imagen");
        tipoActual = getIntent().getStringExtra("tipo");

        // Obtener el ID del intent
        idContacto = getIntent().getIntExtra("id", 0);
        String nombreContacto = getIntent().getStringExtra("nombre");
        String imagenContacto = getIntent().getStringExtra("imagen");

        // Mostrar los datos en los TextView e ImageView correspondientes
        tvNombreContact.setText(nombreContacto);
        Picasso.get().load(imagenContacto).into(ivContactImage);

        // Crear una instancia de Retrofit y ContactService
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://64779d129233e82dd53beed7.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        contactService = retrofit.create(ContactService.class);

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamar al método delete del ContactService para eliminar el contacto por su ID
                Call<Void> call = contactService.delete(idContacto);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Eliminación exitosa, puedes realizar cualquier acción adicional necesaria
                            Toast.makeText(InfoPaisaje.this, "Contacto eliminado exitosamente", Toast.LENGTH_SHORT).show();
                            // Enviar resultado a ListaPaisajeActivity
                            finish();
                            Intent intent = new Intent(InfoPaisaje.this, ListaPaisajeActivity.class);
                            startActivity(intent);
                        } else {
                            // Error en la eliminación, muestra un mensaje de error o realiza una acción de manejo de errores
                            Log.e("INFO_CONTACT", "Error al eliminar el contacto: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Error en la solicitud, muestra un mensaje de error o realiza una acción de manejo de errores
                        Log.e("INFO_CONTACT", "Error al realizar la solicitud: " + t.getMessage());
                    }
                });
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoPaisaje.this, ActualizarPaisaje.class);
                intent.putExtra("id", idContacto);
                intent.putExtra("nombre", nombreActual);
                intent.putExtra("imagen", imagenActual);
                intent.putExtra("tipo", tipoActual);
                startActivityForResult(intent, REQUEST_CODE_ACTUALIZAR_CONTACTO);
            }
        });
    }
}