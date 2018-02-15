package com.drwitteck.mynfcapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.Ndef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    PendingIntent pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            //Gives you an array, with first element a ndef message(array of ndef records)
            NdefMessage message = (NdefMessage) intent.getParcelableArrayExtra(NfcAdapter
                    .EXTRA_NDEF_MESSAGES)[0];
            //Pulls out the first record from the array
            NdefRecord record = message.getRecords()[0];

            //You need to know something about the data in the tag to format accordingly
            String payload = new String(record.getPayload());
            ((TextView) findViewById(R.id.payLoadDisplay)).setText(payload);

        }

    }
}
