package com.example.sa.students_android.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sa.students_android.Managers.UsersManager;
import com.example.sa.students_android.Models.Role;
import com.example.sa.students_android.R;


public class LoginActivity extends AppCompatActivity {

    EditText loginText;
    EditText passwordText;

    Button loginButton;
    Button registrationButton;

    static UsersManager usersManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        loginText = (EditText) findViewById(R.id.login_field);
        passwordText = (EditText) findViewById(R.id.password_field);

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String login = loginText.getText().toString();
                Integer password = passwordText.getText().toString().hashCode();

                Role result;
                if ((result = tryLogin(login, password)) != null)
                    switch (result) {
                        case ADMIN:
                            startActivity(new Intent(LoginActivity.this, AdminOverviewActivity.class));
                            break;
                        case STUDENT:
                            startActivity(new Intent(LoginActivity.this, StudentOverviewActivity.class));
                    }

            }
        });

        registrationButton = (Button) findViewById(R.id.register_button);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        usersManager = new UsersManager(this);

        Button debugMode = (Button) findViewById(R.id.debug_mode);
        debugMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, DebugActivity.class));
            }
        });


    }

    private Role tryLogin(String login, Integer password) {

        // Are Login/Password valid?
        // If so, is there such a login in database?
        // If yes then check it's password.
        if(!(login.equals("") || password == 0)
                && usersManager.containsLogin("users", login)
                && usersManager.checkPassword(login, password.hashCode()))
            return usersManager.getUserRole(login);
        return null;
    }
}
