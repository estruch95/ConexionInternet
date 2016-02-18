package com.example.estruch18.conexioninternet;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView imagen;
    private TextView tvInfo;
    private ProgressBar barraProgreso;
    private TextView tvPor;
    private Button btnSiguiente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagen = (ImageView)findViewById(R.id.imagenObtenida);
        tvInfo = (TextView)findViewById(R.id.tvInfo);
        barraProgreso = (ProgressBar)findViewById(R.id.progressBar);
        tvPor = (TextView)findViewById(R.id.tvPor);
        btnSiguiente = (Button)findViewById(R.id.btnSiguiente);

        /*
        ProcesoDescargaImagen thread = new ProcesoDescargaImagen();
        thread.execute();

        ProcesoDescargaImagen1 thread1 = new ProcesoDescargaImagen1();
        thread1.execute();

        ProcesoCargaBarraProgreso thread2 = new ProcesoCargaBarraProgreso();
        thread2.execute(0);
        */

        //Para comprobar que las preferencias permanecen guardadas en la app
        /*
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        boolean rotacion = sharedPref.getBoolean("rotation_option", true);
        String nombre = sharedPref.getString("name_option", "");
        String color = sharedPref.getString("background_options", "");

        Log.d("info", "La preferencia color es: "+color);
        Log.d("info", "La preferencia nombre es: "+nombre);
        Log.d("info", "La preferencia rotacion esta: "+rotacion);
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {

            Intent prefs = new Intent(getApplicationContext(), Preferencias.class);
            startActivity(prefs);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //ROTACIÓN DE PANTALLA
    /*
    @Override
    protected void onSaveInstanceState(Bundle guardarEstado) {
        super.onSaveInstanceState(guardarEstado);

        guardarEstado.putString("porcentaje", tvInfo.getText().toString());
        guardarEstado.getInt("progreso_barra", barraProgreso.getProgress());
    }

    @Override
    protected void onRestoreInstanceState(Bundle recuperarEstado) {
        super.onRestoreInstanceState(recuperarEstado);

        String porcentaje = recuperarEstado.getString("porcentaje");
        int progresoBarra = recuperarEstado.getInt("progreso_barra");

        tvInfo.setText(porcentaje);
        barraProgreso.setProgress(progresoBarra);
    }
    */

    //LISTENER BOTON
    public void accionBtnSiguiente(View v){
        Intent i = new Intent(getApplicationContext(), ActivityConFragment.class);
        startActivity(i);
    }

    public class ProcesoDescargaImagen extends AsyncTask<Void, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(Void... params) {

            Bitmap bitmap = downloadImage();
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imagen.setImageBitmap(bitmap);
        }
    }

    public class ProcesoDescargaImagen1 extends AsyncTask<Void, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(Void... voids) {

            Bitmap bitmap = downloadImage1();
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            imagen.setImageBitmap(bitmap);
        }
    }

    public class ProcesoCargaBarraProgreso extends AsyncTask<Integer, Integer, Void>{

        @Override
        protected Void doInBackground(Integer... integers) {

            int contador = integers[0];

            for(int a=0; a<5; a++){

                try {

                    if(contador != 100){

                        Thread.sleep(1000);
                        contador += 20;
                        publishProgress(contador);

                    }

                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... valores) {

            barraProgreso.setProgress(valores[0]);
            tvPor.setText(valores[0]+"%");

        }

        @Override
        protected void onPostExecute(Void aVoid) {

            tvInfo.setText("Imagen cargada!");

            ProcesoDescargaImagen1 thread1 = new ProcesoDescargaImagen1();
            thread1.execute();
        }

    }

    public Bitmap downloadImage(){

        InputStream is = null;
        Bitmap bitmap = null;
        HttpURLConnection conexion = null;
        ConnectivityManager cManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();

        if(nInfo != null && nInfo.isConnected()){

            try {
                URL url = new URL("http://www.microlog.net/jml/tienda/catalog/images/42-rueda-goma-tecnologia.jpg");
                conexion = (HttpURLConnection) url.openConnection();
                conexion.setReadTimeout(10000);
                conexion.setConnectTimeout(15000);
                conexion.setRequestMethod("GET");
                conexion.setDoInput(true);

                conexion.connect();

                int codigoDevuelto = conexion.getResponseCode();

                //El código de conexión OK  puede variar en función de la web o la URL
                //Tener en cuenta el tema de los permisos en el Manifest
                if(codigoDevuelto == 200){
                    is = conexion.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    Log.d("bitmap", bitmap.toString());
                    Log.d("ivan", "Conexion con el servidor exitosa");

                }
                else{
                    Log.d("ivan", "Conexion con el servidor fallida");
                }

            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {

                try {
                    is.close();
                    conexion.disconnect();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        else{
            Log.d("info", "No hay conexion de red");
        }

        return bitmap;
    }

    public Bitmap downloadImage1(){

        InputStream is = null;
        Bitmap bitmap = null;
        HttpURLConnection conexion = null;
        ConnectivityManager cManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();

        if(nInfo != null && nInfo.isConnected()){

            try {

                URL url = new URL("http://www.apple.com/mx/ipod/home/images/social/og.jpg?201601060706");
                conexion = (HttpURLConnection) url.openConnection();
                //conexion.setReadTimeout(10000);
                //conexion.setConnectTimeout(15000);
                conexion.setRequestMethod("GET");

                conexion.connect();
                int responseCod = conexion.getResponseCode();
                Log.d("info", "Código de respuesta: "+responseCod);

                is = conexion.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);

            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
        else{

            Log.d("info", "No hay conexion de red");

        }

        return bitmap;
    }





}
