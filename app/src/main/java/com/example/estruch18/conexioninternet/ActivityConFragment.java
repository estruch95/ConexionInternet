package com.example.estruch18.conexioninternet;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.estruch18.conexioninternet.Fragments.FragmentDinamico;
import com.example.estruch18.conexioninternet.Fragments.FragmentDinamico1;

public class ActivityConFragment extends AppCompatActivity {

    private Button btnCambiar, btnNavDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_fragment);

        btnCambiar = (Button)findViewById(R.id.btnCambiar);
        btnNavDrawer = (Button)findViewById(R.id.btnNav_drawer);

        //CARGAMOS EL FRAGMENT
        FragmentDinamico fd = new FragmentDinamico();

        android.app.FragmentManager cManager = getFragmentManager();
        FragmentTransaction transaction = cManager.beginTransaction();
        transaction.add(R.id.fragmentContainer, fd).commit();
    }

    public void accionBtnCambiar(View v){

        FragmentDinamico1 fd1 = new FragmentDinamico1();
        //Reemplazar fragment
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fd1).commit();

    }

    public void accionBtnNavDrawer(View v){

        Intent i = new Intent(getApplicationContext(), NavigationDrawer.class);
        startActivity(i);
    }

    public void eliminarFragment(){

        //Eliminar Fragment
        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.fragmentContainer)).commit();

    }

    public void addFragment(){

        FragmentDinamico1 fd1 = new FragmentDinamico1();
        //Añade un nuevo fragment
        getFragmentManager().beginTransaction().add(R.id.fragmentContainer, fd1).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_activity_con, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }
}
