package com.example.bookmarkinaja.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookmarkinaja.HalamanUtama;
import com.example.bookmarkinaja.R;
import com.example.bookmarkinaja.profil;

import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;

public class TabSettings extends Fragment {

    private Button btnProfil, btnKeluar;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tabsettings, container, false);
        btnKeluar = root.findViewById(R.id.btnKeluar);
        btnProfil = root.findViewById(R.id.btnProfil);


        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferen = getActivity().getSharedPreferences("masuk", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferen.edit();
                editor.putString("ingat", "false");
                editor.apply();
                getActivity().finish();
            }
        });

        btnProfil.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), profil.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        return root;


    }

}
