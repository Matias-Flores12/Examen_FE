package com.example.universidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.universidad.Model.Carrera;
import com.example.universidad.Utils.Apis;
import com.example.universidad.Utils.CarreraService;
import com.example.universidad.dto.CarreraDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditarCarrera extends AppCompatActivity {

    Carrera carrera;
    CarreraService carreraService;

    EditText EditarCarrera;
    Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_carrera);


        Intent detailIntent = getIntent();
        carrera = (Carrera) detailIntent.getSerializableExtra("carrera");
       // Log.i("carre: ", carrera.toString());
        EditarCarrera = findViewById(R.id.txt_EditarCarrera);
        EditarCarrera.setText(carrera.getNombre());

        btn_update = findViewById(R.id.btn_actualizar);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CarreraDto dto= new CarreraDto(EditarCarrera.getText().toString());
                editarCarrera(dto);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);



            }
        });

    }
    public void editarCarrera(CarreraDto dto){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Apis.Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        carreraService = retrofit.create(CarreraService.class);
        int id = Math.toIntExact(carrera.getId());
        Call<Carrera> call = carreraService.edit(id,dto);
        call.enqueue(new Callback<Carrera>() {
            @Override
            public void onResponse(Call<Carrera> call, Response<Carrera> response) {

                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("Respuesta de error",response.message());
                    return;
                }
                Carrera carrera = response.body();
                Toast toast = Toast.makeText(getApplicationContext(), carrera.getNombre()+ " edit!!!", Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onFailure(Call<Carrera> call, Throwable t) {
                Log.e("Repuesta de error: ",t.getMessage());

            }
        });
    }
}