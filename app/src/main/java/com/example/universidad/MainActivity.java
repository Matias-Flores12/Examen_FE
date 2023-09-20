package com.example.universidad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.universidad.fragment.AlumnoFragment;
import com.example.universidad.fragment.CarreraFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Button btn_editar;
    AlumnoFragment alumnoFragment = new AlumnoFragment();
    CarreraFragment carreraFragment = new CarreraFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = findViewById(R.id.bottom_navegation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();

            if (itemId == R.id.btn_alumno) {
                loadFragment(alumnoFragment);
                return true;
            } else if (itemId == R.id.btn_carrera) {
                loadFragment(carreraFragment);
                return true;
            }

            return false;
    };
    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contenedor_frang, fragment);
        transaction.commit();
    }
};

}