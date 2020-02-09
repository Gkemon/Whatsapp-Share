package com.gk.emon.whatsappshare;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText etMessage=findViewById(R.id.et_msg);
        final EditText etPhone=findViewById(R.id.et_phone);

        final CountryCodePicker ccp;
        ccp = (CountryCodePicker) findViewById(R.id.ccp);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber=ccp.getFullNumber()+
                        etPhone.getText().toString();
                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
                String url="";
                try {
                    url = "https://api.whatsapp.com/send?phone="+ phoneNumber +"&text=" +
                            URLEncoder.encode(etMessage.getText().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Log.e("GK",e.getLocalizedMessage());
                    e.printStackTrace();
                }

                sendIntent.setData(Uri.parse(url));
                sendIntent.setAction(Intent.ACTION_SENDTO);

                sendIntent.putExtra("jid",
                        PhoneNumberUtils.stripSeparators(phoneNumber)+"@s.whatsapp.net");
                startActivity(sendIntent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
