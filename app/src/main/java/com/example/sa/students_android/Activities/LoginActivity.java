package com.example.sa.students_android.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sa.students_android.R;
import com.example.sa.students_android.SQLDB.DatabaseHandler;


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
                    startActivity(new Intent(LoginActivity.this, AfterAuthActivity.class));
            }
        });

        registrationButton = (Button) findViewById(R.id.register_button);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class).putExtra("dataBase", databaseHandler));
            }
        });

        databaseHandler = new DatabaseHandler(this);
        databaseHandler.createTable("users");

        databaseHandler.addItem("users", "admin", "admin".hashCode());
    }

    private boolean tryLogin(String login, Integer password) {
        if (login.equals("") || password == 0)
            return false;
        return databaseHandler.containsLogin("users", login)
                && databaseHandler.checkPassword("users", login, password.hashCode());
    }
}
