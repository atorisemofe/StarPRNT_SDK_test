package com.example.starprnt_sdk;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.starprnt_sdk.localizereceipts.ILocalizeReceipts;
import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;
import com.starmicronics.stario.StarPrinterStatus;
import com.starmicronics.starioextension.StarIoExt.Emulation;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button printButton = findViewById(R.id.button);

        printButton.setOnClickListener(v -> {
            StarIOPort port = null;
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);

            }

            try {
                port = StarIOPort.getPort("TCP:192.168.192.53","", 1000);
                StarPrinterStatus status = port.beginCheckedBlock();
                byte[] command = PrinterFunctions.createTextReceiptData(Emulation.StarPRNT, ILocalizeReceipts.createLocalizeReceipts(0,576),false);

                port.writePort(command, 0, command.length);
                status = port.endCheckedBlock();

                if (!status.offline){

                }
            } catch (StarIOPortException e) {
                e.printStackTrace();
            }

            finally {
                try{
                    StarIOPort.releasePort(port);
                }catch (StarIOPortException e){
                    e.printStackTrace();
                }
            }
        });



    }

    public void findPrinters(View view){
        Intent intent = new Intent(this, DisplayPrinters.class);

        startActivity(intent);

    }
}
