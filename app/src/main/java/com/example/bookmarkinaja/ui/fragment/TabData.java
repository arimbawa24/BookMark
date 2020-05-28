package com.example.bookmarkinaja.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.fragment.app.Fragment;


import com.example.bookmarkinaja.Data;
import com.example.bookmarkinaja.R;


public class TabData extends Fragment {


    private  Button lihat;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntancesState){

        View view = inflater.inflate(R.layout.fragment_tabdata,container,false);

        lihat = view.findViewById(R.id.btnView);


        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Data.class);
                startActivity(intent);

            }
        });

        return view;
    }
}
