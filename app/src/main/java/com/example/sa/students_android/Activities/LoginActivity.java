package com.example.sa.students_android.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sa.students_android.Managers.UsersManager;
import com.example.sa.students_android.Models.Role;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.R;
import com.example.sa.students_android.SQLite.DatabaseHandler;

import java.util.Date;


public class LoginActivity extends AppCompatActivity {

    EditText loginText;
    EditText passwordText;

    Button loginButton;
    Button registrationButton;

    static DatabaseHandler databaseHandler;

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

                if (tryLogin(login, password))
                    startActivity(new Intent(LoginActivity.this, GroupsListActivity.class));
            }
        });

        registrationButton = (Button) findViewById(R.id.register_button);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        databaseHandler = new DatabaseHandler(this);

//        databaseHandler.addUser(
//                new User("admin", "admin".hashCode(), "Admin", "aDmin", "adMin", new Date(1970, 01, 01), 1337L, Role.ADMIN));

        Button debugMode = (Button) findViewById(R.id.debug_mode);
        debugMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, DebugActivity.class));
            }
        });


    }

    private boolean tryLogin(String login, Integer password) {

        // Are Login/Password valid?
        // If so, is there such a login in database?
        // If yes then check it's password.
        return !(login.equals("") || password == 0)
                && databaseHandler.containsLogin("users", login)
                && databaseHandler.checkPassword(login, password.hashCode());
    }
}
