package mx.edu.ittepic.unidad3_31jsonclima;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements AsyncResponse  {
    ConexionWeb conexionWeb;
    TextView clima1,clima2,climat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clima1=findViewById(R.id.Clima1);
        clima2=findViewById(R.id.Clima2);
        climat=findViewById(R.id.climat);
        CargarClima();
    }
    public void CargarClima() {
        try {
            conexionWeb = new ConexionWeb(MainActivity.this);
            URL direcciopn = new URL("http://api.openweathermap.org/data/2.5/weather?q=Tepic,mx&APPID=043746939e7268316c8bbe640799c06d");
            conexionWeb.execute(direcciopn);
        } catch (MalformedURLException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void procesarRespuesta(String r) {
        try {
            JSONObject object = new JSONObject(r);
            JSONArray clima =           object.getJSONArray("weather");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < clima.length(); i++) {
                JSONObject objeto = clima.getJSONObject(i);
                String main = objeto.getString("main");
                String des = objeto.getString("description");
                sb.append(main+" : "+des+"         ");
            }
                JSONObject  clima3j= object.getJSONObject("wind");
            clima1.setText(sb+"visibilidad: "+ object.getString("visibility")+"\n"+"Velocidad : "+clima3j.getString("speed")+"\n"+" Grados: "+clima3j.getString("deg"));
                        JSONObject  clima2j= object.getJSONObject("main");
            clima2.setText("\n"+"Temperatura: "+clima2j.getString("temp")+" Humedad:"+clima2j.getString("pressure")+" temperatura minima: "+clima2j.getString("temp_min")+" temperatura maxima: "+clima2j.getString("temp_max"));


        }catch (JSONException e){
            System.out.println(""+e);
        }


    }
}
