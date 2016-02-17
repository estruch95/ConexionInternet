package com.example.estruch18.conexioninternet.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.estruch18.conexioninternet.R;

public class FragmentDinamico1 extends Fragment {

    public FragmentDinamico1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dinamico1, container, false);

        //Declaración de views correspondientes al Fragment

        return v;
    }



}
