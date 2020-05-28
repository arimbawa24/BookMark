package com.example.bookmarkinaja.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmarkinaja.Data;
import com.example.bookmarkinaja.HalamanUtama;
import com.example.bookmarkinaja.R;

public class TabData extends Fragment {

    private Button btnView;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tabdata, container, false);
        btnView = root.findViewById(R.id.btnView);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Data.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        return root;
    }
}
