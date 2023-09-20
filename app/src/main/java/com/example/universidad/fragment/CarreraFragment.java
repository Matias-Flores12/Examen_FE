package com.example.universidad.fragment;

import static com.example.universidad.R.id.btn_eliminar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.universidad.Adapters.CarreraAdapter;
import com.example.universidad.EditarCarrera;
import com.example.universidad.Model.Carrera;
import com.example.universidad.R;
import com.example.universidad.Utils.Apis;
import com.example.universidad.Utils.CarreraService;
import com.example.universidad.dto.CarreraDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CarreraFragment extends Fragment {
    CarreraService carreraService;
    List<Carrera> litaCarrera ;
    ListView listView;
    Button btn_registrar;
    EditText txt_nombreCarrera;
    Carrera carrera;





    public CarreraFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_carrera, container, false);

        ///////ELIMINAR//////////////////////////////

        //////////////////////////////////////////////
        listView=v.findViewById(R.id.listarCarreras);
        listarCarreras();


        btn_registrar = v.findViewById(R.id.btn_insertar);
        txt_nombreCarrera=v.findViewById(R.id.text_addCarrera);

        //////////////////////////////////HABILITADO O NO BOTON ///////////////////////////////////
        btn_registrar.setEnabled(false);
        txt_nombreCarrera.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String texto = charSequence.toString();
                btn_registrar.setEnabled(!texto.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CarreraDto dto = new CarreraDto(txt_nombreCarrera.getText().toString());
                RegistrarCarrera(dto);

                // Limpia el EditText después de agregar una carrera
                txt_nombreCarrera.setText("");
            }
        });
        return v;

    }
    ///////////////////////////////////////////////LISTAR///////////////////////////////////////////
    public void listarCarreras() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Apis.Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        carreraService = retrofit.create(CarreraService.class);
        Call<List<Carrera>> call = carreraService.getCarrera();
        call.enqueue(new Callback<List<Carrera>>() {
            @Override
            public void onResponse(Call<List<Carrera>> call, Response<List<Carrera>> response) {
                if(!response.isSuccessful()){
                    Log.e("Respuesta de error",response.message());
                    return;
                }
                litaCarrera = response.body();

                CarreraAdapter carreraAdapter = new CarreraAdapter(litaCarrera,getActivity());
                listView.setAdapter(carreraAdapter);
                litaCarrera.forEach(p-> Log.e("Error: ", p.toString()));
            }

            @Override
            public void onFailure(Call<List<Carrera>> call, Throwable t) {
                Log.e("Repuesta de error: ",t.getMessage());
            }
        });
    }
    ///////////////////////////////////////////////LISTAR///////////////////////////////////////////

    /////////////////////////////////////////////REGISTRAR/////////////////////////////////////////
    public void RegistrarCarrera(CarreraDto dto){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Apis.Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        carreraService = retrofit.create(CarreraService.class);
        Call<Carrera> call = carreraService.create(dto);
        call.enqueue(new Callback<Carrera>() {
            @Override
            public void onResponse(Call<Carrera> call, Response<Carrera> response) {

                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("Respuesta de error",response.message());
                    return;
                }
                Carrera carrera = response.body();
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), carrera.getNombre()+ "create!!!", Toast.LENGTH_LONG);
                toast.show();
            }


            @Override
            public void onFailure(Call<Carrera> call, Throwable t) {
                Log.e("Repuesta de error: ",t.getMessage());
            }
        });


    }

    /////////////////////////////////////////////REGISTRAR/////////////////////////////////////////

}