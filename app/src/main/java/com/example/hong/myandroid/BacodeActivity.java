package com.example.hong.myandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class BacodeActivity extends AppCompatActivity {
    private String str = "https://www.beepscan.com/barcode/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void onResume() {
        super.onResume();
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(BacodeScannerActivity.class);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //Log.d("onActivityResult", "onActivityResult: .");
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        str += scanResult.getContents(); // 바코드 값 리턴 후 파싱
        Intent tempIntent = new Intent();
        tempIntent.putExtra("result", str);
        setResult(1, tempIntent);
        finish();
    }
}
