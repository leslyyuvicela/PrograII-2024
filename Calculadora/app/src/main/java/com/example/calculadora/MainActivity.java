package com.example.calculadora;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TabHost tbh;
    Button btn;

    conversores objConversor = new conversores();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbh = findViewById(R.id.tbhConversores);
        tbh.setup();

        tbh.addTab(tbh.newTabSpec("LONGITUD").setContent(R.id.tabLongitud).setIndicator("LONGITUD", null));
        tbh.addTab(tbh.newTabSpec("ALMACENAMIENTO").setContent(R.id.tabAlmacenamiento).setIndicator("ALMACENAMIENTO", null));
        tbh.addTab(tbh.newTabSpec("MONEDAS").setContent(R.id.tabMonedas).setIndicator("MONEDAS",null));
        tbh.addTab(tbh.newTabSpec("MASA").setContent(R.id.tabMasa).setIndicator("MASA",null));
        tbh.addTab(tbh.newTabSpec("TIEMPO").setContent(R.id.tabTiempo).setIndicator("TIEMPO",null));
        tbh.addTab(tbh.newTabSpec("TRANSFERENCIA DE DATOS").setContent(R.id.tabTransferencia).setIndicator("TRANSFERENCIA",null));
        tbh.addTab(tbh.newTabSpec("VOLUMEN").setContent(R.id.tabVolumen).setIndicator("VOLUMEN",null));


        btn=findViewById(R.id.btnLongitudConvertir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {try{
                double resp = objConversor.convertir(0,findViewById(R.id.spnLongitudDe),findViewById(R.id.spnLongitudA),
                        findViewById(R.id.txtLongitudCantidad));
                Toast.makeText(getApplicationContext(), "Respuesta: " + resp, Toast.LENGTH_LONG).show();
            }catch (Exception e){} }
        });
        btn=findViewById(R.id.btnAlmacenamientoConvertir);///a
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    double resp = objConversor.convertir(1,findViewById(R.id.spnAlmacenamientoA),findViewById(R.id.spnAlmacenamientoDe),
                            findViewById(R.id.txtAlmacenamientoCantidad));
                    Toast.makeText(getApplicationContext(), "Respuesta: " + resp, Toast.LENGTH_LONG).show();
                }catch (Exception e){} }
        });
        btn=findViewById(R.id.btnMonedasConvertir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    double resp = objConversor.convertir(2,findViewById(R.id.spnMonedasDe),findViewById(R.id.spnMonedasA),
                            findViewById(R.id.txtMonedasCantidad));
                    Toast.makeText(getApplicationContext(), "Respuesta: " + resp, Toast.LENGTH_LONG).show();
                }catch (Exception e){} }
        });
        btn=findViewById(R.id.btnMasaConvertir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    double resp = objConversor.convertir(3,findViewById(R.id.spnMasaDe),findViewById(R.id.spnMasaA),
                            findViewById(R.id.txtMasaCantidad));
                    Toast.makeText(getApplicationContext(), "Respuesta: " + resp, Toast.LENGTH_LONG).show();
                }catch (Exception e){}  }
        });
        btn=findViewById(R.id.btnTiempoConvertir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    double resp = objConversor.convertir(4,findViewById(R.id.spnTiempoDe),findViewById(R.id.spnTiempoA),
                            findViewById(R.id.txtTiempoCantidad));
                    Toast.makeText(getApplicationContext(), "Respuesta: " + resp, Toast.LENGTH_LONG).show();
                }catch (Exception e){}  }
        });
        btn=findViewById(R.id.btnTransferenciaConvertir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    double resp = objConversor.convertir(5,findViewById(R.id.spnTransferenciaDe),findViewById(R.id.spnTransferenciaA),
                            findViewById(R.id.txtTransferenciaCantidad));
                    Toast.makeText(getApplicationContext(), "Respuesta: " + resp, Toast.LENGTH_LONG).show();
                }catch (Exception e){} }
        });
        btn=findViewById(R.id.btnVolumenConvertir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    double resp = objConversor.convertir(6, findViewById(R.id.spnVolumenDe), findViewById(R.id.spnVolumenA),
                            findViewById(R.id.txtVolumenCantidad));
                    Toast.makeText(getApplicationContext(), "Respuesta: " + resp, Toast.LENGTH_LONG).show();
                }catch (Exception e){}
            }
        });

    }
}
class conversores{
    double [][] valores = {
            //LONGITUD
            {1, 100, 39.3701, 3.28084166667,1.193, 1.0936138888889999077, 0.001, 0.000621371, 0.001,0.000001, 0.000000001},
            //ALMACENAMIENTO  ////////////////////////////////////
            {1, 8,(1000*8), (Math.pow(1000,2)*8),(Math.pow(1000,3)*8), (Math.pow(1000,4)*8),( Math.pow(1000,5)*8), (Math.pow(1000,6)*8), (Math.pow(1000,7)*8),
                    (1024*8), (Math.pow(1024,2)*8), (Math.pow(1024,3)*8), (Math.pow(1024,4)*8), (Math.pow(1024,5)*8), (Math.pow(1024,6)*8), (Math.pow(1024,7)*8) },
            //MONEDAS
            {1,0.93, 7.81,17.14,149.27,0.79,24.73,36.78,1.35,8.76},
            //MASA
            {1,1000,1000000,1000000000,5000,0.15747304, 2.20462262,0.001,35.273962,0.01},
            //TIEMPO
            {1,60,3600,3600000,3600000000.0,1.0/24.0,1.0/168.0, 1.0/(30.417*24.0),1.0/(24.0*365.0),1/(24.0*3650.0)},
            //TRANFERENCIA DE DATOS
            {1000000,125000,1000,125,1,0.125,0.001,0.000125,0.000001,0.000000125},
            //VOLUMEN
            {1,4,8,128,256,768,3785.41,3.78541,15.7725, 0.00378541}
    };
    public  double convertir(int opcion,Spinner spnDe,Spinner spnA,EditText txtCantidad){
        int de =spnDe.getSelectedItemPosition();
        int a =spnA.getSelectedItemPosition();
        double cantidad= Double.parseDouble(txtCantidad.getText().toString());
        return valores [opcion][a]/valores[opcion][de]*cantidad;
    }
}