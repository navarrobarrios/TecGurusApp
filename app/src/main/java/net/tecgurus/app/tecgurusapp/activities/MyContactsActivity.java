package net.tecgurus.app.tecgurusapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.tecgurus.app.tecgurusapp.R;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MyContactsActivity extends AppCompatActivity {

    //region Activity Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contacts);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MyContactsActivityPermissionsDispatcher.readContactsWithPermissionCheck(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region Local Methods
    @NeedsPermission(Manifest.permission.READ_CONTACTS)
    public void readContacts(){
        Cursor cursor = managedQuery(ContactsContract.Contacts.CONTENT_URI, null, null , null, null);

        String[] columnas = {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY};

        int[] vistas = {R.id.contactID, R.id.contactName};

        final SimpleCursorAdapter adapter =
                new SimpleCursorAdapter(
                        this,
                        R.layout.item_contacts,
                        cursor,
                        columnas,
                        vistas);

        final ListView listView = findViewById(R.id.activity_may_contacts_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView name = view.findViewById(R.id.contactName);
                TextView id = view.findViewById(R.id.contactID);
                Intent myContactActionsActivity = new Intent(getApplicationContext(), MyContactActionsActivity.class);
                myContactActionsActivity.putExtra(MyContactActionsActivity.KEY_TO_CONTACT_ID, id.getText().toString());
                myContactActionsActivity.putExtra(MyContactActionsActivity.KEY_TO_CONTACT_NAME, name.getText().toString());
                startActivity(myContactActionsActivity);
            }
        });
        listView.setAdapter(adapter);
    }

    @OnPermissionDenied(Manifest.permission.READ_CONTACTS)
    public void onContactsPermissionsDeined(){
        Toast.makeText(this, "Necesitas los permisos", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.READ_CONTACTS)
    public void onNeverAskAgain(){
        Toast.makeText(this, "Activa los permissos en confiración.", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MyContactsActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    //endregion
}
