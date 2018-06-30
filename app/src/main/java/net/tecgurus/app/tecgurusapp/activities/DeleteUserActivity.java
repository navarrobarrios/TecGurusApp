package net.tecgurus.app.tecgurusapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.tecgurus.app.tecgurusapp.R;
import net.tecgurus.app.tecgurusapp.db.helpers.UserHelper;
import net.tecgurus.app.tecgurusapp.utils.ValidationsUtils;

public class DeleteUserActivity extends AppCompatActivity {

    //region Variables
    private EditText mUsername;
    //endregion

    //region Activity Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUsername = findViewById(R.id.delete_user_activity_username);
        Button deleteButton = findViewById(R.id.delete_user_activity_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ValidationsUtils.isEmpty(mUsername, getApplicationContext())){
                    String username = mUsername.getText().toString();
                    if (existUser(username)){
                        deleteUser(username);
                    }else{
                        Toast.makeText(DeleteUserActivity.this,
                                String.format(getString(R.string.the_username_not_exists), username), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region Local Methods
    private boolean existUser(String username){
        return UserHelper.getInstance(getApplicationContext()).getUserByUsername(username) != null;
    }

    private void deleteUser(String username){
        UserHelper.getInstance(getApplicationContext()).deleteUserByUsername(username);
        Toast.makeText(this,
                String.format(getString(R.string.user_deleted_success), username),
                Toast.LENGTH_SHORT).show();
        DeleteUserActivity.this.finish();
    }
    //endregion

}
