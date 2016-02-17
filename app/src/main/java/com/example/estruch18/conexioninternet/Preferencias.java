package com.example.estruch18.conexioninternet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by estruch18 on 16/2/16.
 */
public class Preferencias extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.preferencias);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Preference preferencia = findPreference(key);

        switch (key){

            case "rotation_option":
                    boolean estado = sharedPreferences.getBoolean(key, true);

                    if(estado == true){
                        preferencia.setSummary("Activada");
                    }
                    else{
                        preferencia.setSummary("Desactivada");
                    }

                break;

            case "sound_options":
                MultiSelectListPreference selectListPreference = (MultiSelectListPreference)findPreference(key);
                ArrayList<String> selecciones = (ArrayList)selectListPreference.getValues();

                break;

            case "background_options":
                ListPreference listPreference = (ListPreference) findPreference(key);
                String seleccion = listPreference.getValue();
                preferencia.setSummary("Color seleccionado: "+seleccion);
                break;

            case "name_option":
                String nombre = sharedPreferences.getString(key, "");
                preferencia.setSummary("Nombre actual: "+nombre);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
