package net.tecgurus.app.tecgurusapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import net.tecgurus.app.tecgurusapp.R;
import net.tecgurus.app.tecgurusapp.db.beans.UserBean;
import net.tecgurus.app.tecgurusapp.utils.ValidationsUtils;

public class UpdateUserActivity extends AppCompatActivity {

    //region Variables
    //region Views
    private EditText mUsername, mPassword, mConfirmPassword, mName, mLastName, mAddress;
    private Button mSearchButton;
    private LinearLayout mContentLayout;
    //endregion
    //endregion

    //region Activity Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUsername = findViewById(R.id.update_user_activity_username);
        mPassword = findViewById(R.id.update_user_activity_password);
        mConfirmPassword = findViewById(R.id.update_user_activity_confirm_password);
        mName = findViewById(R.id.update_user_activity_name);
        mLastName = findViewById(R.id.update_user_activity_lastname);
        mAddress = findViewById(R.id.update_user_activity_address);
        mSearchButton = findViewById(R.id.update_user_activity_search_button);
        mContentLayout = findViewById(R.id.update_user_activity_content_layout);
        Button editButton = findViewById(R.id.update_user_activity_edit_button);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ValidationsUtils.isEmpty(mUsername, getApplicationContext())){
                    if (isOnDatabase()){
                        mContentLayout.setVisibility(View.VISIBLE);
                        mSearchButton.setVisibility(View.GONE);
                        mUsername.setEnabled(false);
                    }else{
                        mUsername.setEnabled(true);
                        mSearchButton.setVisibility(View.VISIBLE);
                        mContentLayout.setVisibility(View.GONE);
                    }
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()){
                    //TODO update on database
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

    @Override
    public void onBackPressed() {
        UpdateUserActivity.this.finish();
    }

    //endregion

    //region Local Methods
    private boolean isOnDatabase(){
        String username = mUsername.getText().toString();
        UserBean userBean = new UserBean();
        //TODO get from database
        return true;
    }


    private boolean validateFields(){
        boolean allFieldsAreFine = true;

        if (ValidationsUtils.isEmpty(mPassword, getApplicationContext()))
            allFieldsAreFine = false;

        if (ValidationsUtils.isEmpty(mConfirmPassword, getApplicationContext()))
            allFieldsAreFine = false;

        if (ValidationsUtils.isEmpty(mName, getApplicationContext()))
            allFieldsAreFine = false;

        if (ValidationsUtils.isEmpty(mLastName, getApplicationContext()))
            allFieldsAreFine = false;

        if (ValidationsUtils.isEmpty(mAddress, getApplicationContext()))
            allFieldsAreFine = false;


        if (allFieldsAreFine){
            if (!mPassword.getText().toString().equals(
                    mConfirmPassword.getText().toString())){
                mConfirmPassword.setError(getString(R.string.error_incorrect_confirmation));
                allFieldsAreFine = false;
            }
        }

        return allFieldsAreFine;
    }
    //endregion
}
