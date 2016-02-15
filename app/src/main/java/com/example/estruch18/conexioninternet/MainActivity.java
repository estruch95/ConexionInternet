package com.example.estruch18.conexioninternet;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagen = (ImageView)findViewById(R.id.imagenObtenida);

        ProcesoDescargaImagen thread = new ProcesoDescargaImagen();
        thread.execute();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
