package com.example.estruch18.conexioninternet.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.estruch18.conexioninternet.R;

public class FragmentDinamico extends Fragment {

    public FragmentDinamico() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_dinamico, container, false);

        //DECLARACIÓN DE LOS VIEWS CORRESPONDIENTES AL FRAGMENT

        return  v;
    }

}
