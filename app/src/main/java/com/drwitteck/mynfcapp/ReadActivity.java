package com.drwitteck.mynfcapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReadActivity extends AppCompatActivity {
    PendingIntent pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pending intent to have tag delivered to this activity
        Intent intent = new Intent(this, ReadActivity.class);
        pi = PendingIntent.getActivity(this, 0, intent, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Intercept all NFC tags (filter is null)
        NfcAdapter.getDefaultAdapter(this).enableForegroundDispatch(this, pi, null, null);

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Stop intercepting NFC tags
        NfcAdapter.getDefaultAdapter(this).disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent){
        // Only processing NDEF formatted tags
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            readPayload(intent);
        }
    }

    void readPayload(Intent intent) {
        // If data is text/plain, a language code will be retrieved
        // boilerplate code can remove it
        String payload = new String(
                ((NdefMessage) intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)[0])
                        .getRecords()[0]
                        .getPayload());

        ((TextView) findViewById(R.id.payLoadDisplay))
                .setText(payload);
    }
}
