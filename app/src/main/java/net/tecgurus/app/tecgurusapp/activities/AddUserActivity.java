package net.tecgurus.app.tecgurusapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.tecgurus.app.tecgurusapp.R;
import net.tecgurus.app.tecgurusapp.db.beans.UserBean;
import net.tecgurus.app.tecgurusapp.utils.ValidationsUtils;

import java.util.ArrayList;
import java.util.List;

public class AddUserActivity extends AppCompatActivity{

    //region Variables
    //region Views
    private EditText mUsername, mPassword, mConfirmPassword, mName, mLastName, mAddress;
    //endregion
    //endregion

    //region Activity Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        mUsername = findViewById(R.id.add_user_activity_username);
        mPassword = findViewById(R.id.add_user_activity_password);
        mConfirmPassword = findViewById(R.id.add_user_activity_confirm_password);
        mName = findViewById(R.id.add_user_activity_name);
        mLastName = findViewById(R.id.add_user_activity_lastname);
        mAddress = findViewById(R.id.add_user_activity_address);
        Button addButton = findViewById(R.id.add_user_activity_add_button);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()){
                    UserBean userBean = new UserBean();
                    userBean.setUsername(mUsername.getText().toString());
                    userBean.setPassword(mPassword.getText().toString());
                    userBean.setName(mName.getText().toString());
                    userBean.setLastname(mLastName.getText().toString());
                    userBean.setAddress(mAddress.getText().toString());
                    saveOnDatabase(userBean);
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

    //region LocalMethods
    private boolean validateFields(){
        boolean allFieldsAreFine = true;

        if (ValidationsUtils.isEmpty(mUsername, getApplicationContext()))
            allFieldsAreFine = false;

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

    private void saveOnDatabase(UserBean userBean){
        //TODO save to database
    }

    private List<UserBean> getUsers(){
        return new ArrayList<>();
    }
    //endregion

}
