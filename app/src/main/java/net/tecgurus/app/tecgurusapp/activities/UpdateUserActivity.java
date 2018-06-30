package net.tecgurus.app.tecgurusapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.tecgurus.app.tecgurusapp.R;
import net.tecgurus.app.tecgurusapp.db.beans.UserBean;
import net.tecgurus.app.tecgurusapp.db.helpers.UserHelper;
import net.tecgurus.app.tecgurusapp.utils.ValidationsUtils;

public class UpdateUserActivity extends AppCompatActivity {

    //region Variables
    //region Static Variables
    public static final String KEY_USERNAME = "key_to_user_bean";
    //endregion
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
                   validateUser();
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()){
                    UserBean userBean = new UserBean();
                    userBean.setUsername(mUsername.getText().toString());
                    userBean.setPassword(mPassword.getText().toString());
                    userBean.setName(mName.getText().toString());
                    userBean.setLastname(mLastName.getText().toString());
                    userBean.setAddress(mAddress.getText().toString());
                    UserHelper.getInstance(getApplicationContext()).update(userBean);
                    Toast.makeText(UpdateUserActivity.this,
                            String.format(getString(R.string.user_updated_success),
                            userBean.getUsername()), Toast.LENGTH_SHORT).show();
                    UpdateUserActivity.this.finish();
                }
            }
        });

        checkIfHasUsername();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getIntent() != null && getIntent().hasExtra(KEY_USERNAME)){
            this.finish();
        }else{
            if (mSearchButton.getVisibility() == View.VISIBLE){
                UpdateUserActivity.this.finish();
            }else{
                mUsername.setText(null);
                mUsername.setEnabled(true);
                mSearchButton.setVisibility(View.VISIBLE);
                mContentLayout.setVisibility(View.GONE);
            }
        }
    }

    //endregion

    //region Local Methods
    private void checkIfHasUsername(){
        if (getIntent() != null && getIntent().hasExtra(KEY_USERNAME)){
            String username = getIntent().getStringExtra(KEY_USERNAME);
            mUsername.setText(username);
            validateUser();
        }
    }

    private void validateUser(){
        if (isOnDatabase()){
            mContentLayout.setVisibility(View.VISIBLE);
            mSearchButton.setVisibility(View.GONE);
            mUsername.setEnabled(false);
        }else{
            Toast.makeText(UpdateUserActivity.this,
                    String.format(getString(R.string.the_username_not_exists), mUsername.getText().toString()),
                    Toast.LENGTH_SHORT).show();
            mUsername.setEnabled(true);
            mSearchButton.setVisibility(View.VISIBLE);
            mContentLayout.setVisibility(View.GONE);
        }
    }

    private boolean isOnDatabase(){
        String username = mUsername.getText().toString();
        UserBean userBean = UserHelper.getInstance(getApplicationContext()).getUserByUsername(username);
        if (userBean != null){
            mPassword.setText(userBean.getPassword());
            mConfirmPassword.setText(userBean.getPassword());
            mName.setText(userBean.getName());
            mLastName.setText(userBean.getLastname());
            mAddress.setText(userBean.getAddress());
            return true;
        }else{
            return false;
        }
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
