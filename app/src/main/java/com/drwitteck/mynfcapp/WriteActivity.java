package com.drwitteck.mynfcapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WriteActivity extends AppCompatActivity {
    PendingIntent pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Intent intent = new Intent(this, MainActivity.class);
        pi = PendingIntent.getActivity(this, 0, intent, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        NfcAdapter.getDefaultAdapter(this).enableForegroundDispatch(this, pi, null, null);

    }

    @Override
    protected void onPause() {
        super.onPause();
        NfcAdapter.getDefaultAdapter(this).disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent){
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())){
            String payload = ((TextView) findViewById(R.id.payloadText)).getText().toString();

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            

        }

    }
}
