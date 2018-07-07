package net.tecgurus.app.tecgurusapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.tecgurus.app.tecgurusapp.R;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@SuppressWarnings("deprecation")
@RuntimePermissions
public class MyContactActionsActivity extends AppCompatActivity {

    //endregion
    //region StaticVariables
    public static final String KEY_TO_CONTACT_ID = "key_to_contact_id";
    public static final String KEY_TO_CONTACT_NAME = "key_to_contact_name";
    //endregion
    //endregion

    //region Activity Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_my_contact_actions);
        getInformation();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region Local Methods
    private void getInformation() {
        if (getIntent() != null &&
                getIntent().hasExtra(KEY_TO_CONTACT_ID)) {

            TextView textNumber = findViewById(R.id.actvity_my_contacts_action_phone);
            TextView textEmail = findViewById(R.id.actvity_my_contacts_action_email);
            Button call = findViewById(R.id.actvity_my_contacts_action_call);
            Button sendEmail = findViewById(R.id.actvity_my_contacts_action_send_email);

            String mContactName = getIntent().getStringExtra(KEY_TO_CONTACT_NAME);
            String mConctactID = getIntent().getStringExtra(KEY_TO_CONTACT_ID);
            setTitle(mContactName);
            if (mConctactID != null) {
                //region getPhone
                final List<String> phones = new ArrayList<>();
                Cursor cursor = managedQuery(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                        new String[]{mConctactID}, null);

                while (cursor.moveToNext()) {
                    phones.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                }

                if (!phones.isEmpty()) {
                    textNumber.setText(phones.get(0));
                }
                //endregion

                //region getEmail
                final List<String> emails = new ArrayList<>();

                Cursor cursorEmail = managedQuery(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?",
                        new String[]{mConctactID}, null);

                while (cursorEmail.moveToNext()) {
                    emails.add(cursorEmail.getString(cursorEmail.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)));
                }
                if (!emails.isEmpty()) {
                    textEmail.setText(emails.get(0));
                }

                sendEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!emails.isEmpty()) {
                            sendEmailTo(emails.get(0));
                        }
                    }
                });

                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!phones.isEmpty()) {
                            MyContactActionsActivityPermissionsDispatcher
                                    .doCallWithPermissionCheck(
                                            MyContactActionsActivity.this,
                                            phones.get(0));
                        }
                    }
                });

                //endregion
            }
        }
    }

    public void sendEmailTo(String destination){
        Intent share = new Intent(Intent.ACTION_SENDTO);
        share.setData(Uri.parse("mailto:" + destination));
        share.putExtra(Intent.EXTRA_SUBJECT, "Tec Gurus");
        share.putExtra(Intent.EXTRA_TEXT, "Prueba de envio de email");
        startActivity(Intent.createChooser(share, "Enviar email.."));
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    public void doCall(String number) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number));
        startActivity(callIntent);
    }
    //endregion

}

