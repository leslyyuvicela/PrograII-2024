package com.example.controlesbasicos;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TabHost tbh;
    TextView tempVal;
    Button btn;
    Spinner spn;
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

        btn = findViewById(R.id.btnLongitudConvertir);
        btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              spn = findViewById(R.id.spnLongitudDe);
              int  de = spn.getSelectedItemPosition();

              spn = findViewById(R.id.spnLongitudA);
              int a = spn.getSelectedItemPosition();

              tempVal = findViewById(R.id.txtLongitudCantidad);
              double cantidad = Double.parseDouble(tempVal.getText().toString());

              double resp = objConversor.convertir(0, de, a, cantidad);
               Toast.makeText(getApplicationContext(),"Respuesta:" +
                       resp, Toast.LENGTH_LONG).show();

           }
       });
        btn = findViewById(R.id.btnAlmacenamientoConvertir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spn = findViewById(R.id.spnAlmacenamientoDe);
                int  de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spnAlmacenamientoA);
                int a = spn.getSelectedItemPosition();

                tempVal = findViewById(R.id.txtAlmacenamientoCantidad);
                double cantidad = Double.parseDouble(tempVal.getText().toString());

                double resp =1/ objConversor.convertir(1, de, a, cantidad);
                Toast.makeText(getApplicationContext(),"Respuesta:" +
                        resp, Toast.LENGTH_LONG).show();

            }
        });
    }
}
class conversores{
    double[][] valores = {
            //LONGITUD
            {1, 100, 39.3701, 3.28084166667,1.193, 1.0936138888889999077, 0.001, 0.000621371, 0.001,0.000001, 0.000000001},

            //ALMACENAMIENTO
            {1, 8,1000*8, Math.pow(1000,2)*8, Math.pow(1000,3)*8, Math.pow(1000,4)*8, Math.pow(1000,5)*8, Math.pow(1000,6)*8, Math.pow(1000,7)*8,
                    1024*8, Math.pow(1024,2)*8, Math.pow(1024,3)*8, Math.pow(1024,4)*8, Math.pow(1024,5)*8, Math.pow(1024,6)*8, Math.pow(1024,7)*8 }

    };
    public double convertir(int opcion, int de, int a, double cantidad){
        return valores[opcion][a] / valores[opcion][de] * cantidad;
    }
}