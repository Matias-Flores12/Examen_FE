package com.example.universidad.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.FragmentManager;

import com.example.universidad.EditarCarrera;
import com.example.universidad.MainActivity;
import com.example.universidad.Model.Carrera;
import com.example.universidad.R;
import com.example.universidad.Utils.Apis;
import com.example.universidad.Utils.CarreraService;
import com.example.universidad.fragment.EliminarCarrera;
import com.example.universidad.fragment.ElimnarCarreraInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CarreraAdapter extends BaseAdapter implements ElimnarCarreraInterface{
    List<Carrera> carreras;
    CarreraService carreraService;
    Context context;
    Carrera carrera;
    TextView txtnombre;
    List<Carrera> litaCarrera ;

    ImageView btn_editarss, btn_eliminar;
    private FragmentManager supportFragmentManger;


    public CarreraAdapter(List<Carrera> carreras, Context context) {
        this.carreras = carreras;
        this.context = context;
    }

    @Override
    public int getCount() {
        return carreras.size();

    }

    @Override
    public Object getItem(int i) {
        return carreras.get(i);
    }

    @Override
    public long getItemId(int i) {
        return carreras.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {


        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.content_carrera_main, viewGroup,false);

        }
        txtnombre = view.findViewById(R.id.nombre_carrera);
        txtnombre.setText(carreras.get(position).getNombre());

        btn_editarss = view.findViewById(R.id.btn_Editars);
        Cambiar(btn_editarss, carreras.get(position)); // Pasamos el botón y la carrera como parámetros

        btn_eliminar = view.findViewById(R.id.btn_eliminar);
        Eliminar(btn_eliminar, carreras.get(position));


        return view;

    }
    public void Eliminar(ImageView btn_eliminar, final Carrera carrera) {
        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(Math.toIntExact(carrera.getId()));
                callMain();
            }
        });
    }
    // Modificamos el método Cambiar para recibir el botón y la carrera como parámetros
    public void Cambiar(ImageView btn_editar, final Carrera carrera) {
        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditarCarrera.class);
                // intent.putExtra("id", carrera.getId()); // Puedes agregar esto si es necesario
                intent.putExtra("carrera", carrera);
                context.startActivity(intent);
            }
        });
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void showDeleteDialog(int id) {
        FragmentManager fragmentManager = getSupportFragmentManger();
        EliminarCarrera deleteFragment = new EliminarCarrera("Delete Carrera: ", Math.toIntExact(carrera.getId()), this);
    deleteFragment.show(fragmentManager,"Alert");

    }

    @Override
    public void delete(int id) {
        carreraService = getcarreraServices();
        Call<List<Carrera>> call = carreraService.delete(id);
        call.enqueue(new Callback<List<Carrera>>() {
            @Override
            public void onResponse(Call<List<Carrera>> call, Response<List<Carrera>> response) {
                if(!response.isSuccessful()){
                    Log.e("Respuesta: ",response.message());
                    return;
                }
                litaCarrera = response.body();
                callMain();
            }

            @Override
            public void onFailure(Call<List<Carrera>> call, Throwable t) {
                Log.e("Repuesta de error: ",t.getMessage());
            }
        });


    }
    private CarreraService getcarreraServices(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Apis.Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        carreraService = retrofit.create(CarreraService.class);
        return carreraService;
    }
    private void callMain(){
        Intent intent = new Intent(context, MainActivity.class);
        Toast toast =Toast.makeText(context, "Eliminado correctamente", Toast.LENGTH_SHORT);
        toast.show();
        context.startActivity(intent);
    }

    public FragmentManager getSupportFragmentManger() {
        return supportFragmentManger;
    }

    public void setSupportFragmentManger(FragmentManager supportFragmentManger) {
        this.supportFragmentManger = supportFragmentManger;
    }
}
