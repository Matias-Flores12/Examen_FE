package com.example.universidad.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EliminarCarrera extends DialogFragment {

    private String mesaje;
    private int id;
    private ElimnarCarreraInterface elimnarCarreraInterface;

    public EliminarCarrera(String mesaje, int id, ElimnarCarreraInterface elimnarCarreraInterface) {
        this.mesaje = mesaje;
        this.id = id;
        this.elimnarCarreraInterface = elimnarCarreraInterface;
    }

    //ALERTA PARA EL ELIMINAR/////////////////////////////////////
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(mesaje + id + "?")
                .setPositiveButton("Acceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    elimnarCarreraInterface.delete(id);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("Accion: " , "Cancelar");
                    }
                });
        return builder.create();
    }
}
