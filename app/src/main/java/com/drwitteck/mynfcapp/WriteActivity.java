package com.drwitteck.mynfcapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WriteActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {
    PendingIntent pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Intent intent = new Intent(this, ReadActivity.class);
        pi = PendingIntent.getActivity(this, 0, intent, 0);

        findViewById(R.id.writeTag).setOnClickListener((View v) ->
                startActivity(new Intent(this, WriteActivity.class)));
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

            NdefRecord payloadRecord = NdefRecord.createTextRecord(null, payload);

            NdefRecord aarRecord = NdefRecord.createApplicationRecord(getPackageName());

            NdefMessage message = new NdefMessage(new NdefRecord[]{payloadRecord});

            /*Below can removed if you implement NFCAdapter.CreateNdefMesageCallback*/
//            Ndef ndef = Ndef.get(tag);
//
//            try {
//                ndef.connect();
//                ndef.writeNdefMessage(message);
//            } catch (IOException | FormatException e) {
//                e.printStackTrace();
//            }
        }

    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        String payload = ((TextView) findViewById(R.id.payloadText)).getText().toString();

        NdefRecord payloadRecord = NdefRecord.createTextRecord(null, payload);

        NdefRecord aarRecord = NdefRecord.createApplicationRecord(getPackageName());

        return new NdefMessage(new NdefRecord[]{payloadRecord, aarRecord});
    }
}
