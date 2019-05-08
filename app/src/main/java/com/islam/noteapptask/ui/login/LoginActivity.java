package com.islam.noteapptask.ui.login;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.islam.noteapptask.R;
import com.islam.noteapptask.ui.MainActivity;
import com.islam.noteapptask.utils.PreferenceUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.login_et_username);
        findViewById(R.id.login_btn_login).setOnClickListener(this);
    }

    /*
       called when login button clicked
     */
    @Override
    public void onClick(View v) {
        String username = usernameEditText.getText().toString();
        //save the username to the shared prefreneced to get it later
        if (!username.trim().isEmpty()) {
            username = username.toLowerCase();
            PreferenceUtils.saveUserName(this, username);
            MainActivity.launch(this);
        }
    }
}
