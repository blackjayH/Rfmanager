package com.example.hong.myandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class BacodeActivity extends AppCompatActivity {
    private String str = "https://www.beepscan.com/barcode/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void onResume(){
        super.onResume();
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(BacodeScannerActivity.class);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d("onActivityResult", "onActivityResult: .");
        /*
        if (resultCode == -1) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            str += scanResult.getContents();
            Intent localIntent = new Intent();
            localIntent.putExtra("result", intent);
            setResult(-1, localIntent);
            finish();
        }
         */
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            str += scanResult.getContents();
            Intent localIntent = new Intent();
            localIntent.putExtra("result", intent);
            setResult(-1, localIntent);
            finish();

    }


}
