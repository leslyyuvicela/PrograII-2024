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

        tbh = findViewById(R.id.tbhParcial_I);
        tbh.setup();

        tbh.addTab(tbh.newTabSpec("AGUA").setContent(R.id.tabAgua).setIndicator("AGUA", null));
        tbh.addTab(tbh.newTabSpec("AREA").setContent(R.id.tabArea).setIndicator("AREA", null));



        btn=findViewById(R.id.btnCalculoConvertir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {try{
                double resp = objConversor.convertir(0,findViewById(R.id.spnCalculoDe),findViewById(R.id.spnCalculoA),
                        findViewById(R.id.txtCalculoCantidad));
                Toast.makeText(getApplicationContext(), "Respuesta: " + resp, Toast.LENGTH_LONG).show();
            }catch (Exception e){} }
        });
        btn=findViewById(R.id.btnAreaConvertir);///a
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    double resp = objConversor.convertir(1,findViewById(R.id.spnAreaA),findViewById(R.id.spnAreaDe),
                            findViewById(R.id.txtAreaCantidad));
                    Toast.makeText(getApplicationContext(), "Respuesta: " + resp, Toast.LENGTH_LONG).show();
                }catch (Exception e){} }
        });


    }
}
class conversores{
    double [][] valores = {

            //AREA
            {1, 10.7639, 1.4308, 1.19599, 0.001590,0.0001431,0.0001  },

    };
    public  double convertir(int opcion,Spinner spnDe,Spinner spnA,EditText txtCantidad){
        int de =spnDe.getSelectedItemPosition();
        int a =spnA.getSelectedItemPosition();
        double cantidad= Double.parseDouble(txtCantidad.getText().toString());
        return valores [opcion][a]/valores[opcion][de]*cantidad;
    }
}