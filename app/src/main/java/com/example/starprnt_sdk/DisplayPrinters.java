package com.example.starprnt_sdk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.starmicronics.stario.PortInfo;
import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;

import java.util.List;

public class DisplayPrinters extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_printers);

        TextView textView = findViewById(R.id.textView);
        String test;
        List<PortInfo> portList;
        try {
            portList = StarIOPort.searchPrinter("TCP:");

            for (PortInfo port : portList) {
                Log.i("LOG", "Port Name: " + port.getPortName());
                TextView text = new TextView(this);
                test = port.getModelName() + "\n" + port.getPortName() + "\n" + port.getMacAddress();
                textView.setText(test);
            }


            /*for (PortInfo port : portList) {
                Log.i("LOG", "Port Name: " + port.getPortName());
                Log.i("LOG", "MAC Address: " + port.getMacAddress());
                Log.i("LOG", "Model Name: " + port.getModelName());
                test = port.getModelName()+ "\n" + port.getPortName() + "\n" + port.getMacAddress();
                textView.setText(test);

            }*/


        } catch (StarIOPortException ignored) {

        }

        textView.setOnClickListener(v -> navigateUpTo(new Intent(getBaseContext(), MainActivity.class)));



    }
}