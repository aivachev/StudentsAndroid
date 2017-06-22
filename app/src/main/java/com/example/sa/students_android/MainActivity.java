package com.example.sa.students_android;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sa.students_android.SQLDB.DatabaseHandler;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Main";

    public static HashMap<String, Integer> users = new HashMap<>();

    EditText loginText;
    EditText passwordText;

    Button loginButton;
    Button registrationButton;

    static DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        startActivity(new Intent(this, AfterAuthActivity.class));
/*
        loginText = (EditText) findViewById(R.id.login_field);
        passwordText = (EditText) findViewById(R.id.password_field);

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String login = loginText.getText().toString();
                        Integer password = passwordText.getText().toString().hashCode();

                        if (tryLogin(login, password))
                            startActivity(new Intent(MainActivity.this, AfterAuthActivity.class));
                        }
                    });

        registrationButton = (Button) findViewById(R.id.register_button);
        registrationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
                    }
                });

        databaseHandler = new DatabaseHandler(this);
        databaseHandler.createTable("users");
*/
    }

    private boolean tryLogin(String login, Integer password) {
        if(login.equals("") || password == 0)
            return false;
        if(databaseHandler.getValue(login) > 0)
            if(databaseHandler.getValue(login).compareTo(password) == 0)
                return true;
        return false;
    }
}
