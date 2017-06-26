package com.example.sa.students_android.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.R;
import com.example.sa.students_android.SQLite.DatabaseHandler;

/**
 * Created by sa on 20.06.17.
 */

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "REGACTIVITY";
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        databaseHandler = new DatabaseHandler(this);

        final EditText newLogin = (EditText) findViewById(R.id.create_login);
        final EditText newPassword = (EditText) findViewById(R.id.create_password);
        final EditText confirmPassword = (EditText) findViewById(R.id.confirm_password);

        Button tryRegister = (Button) findViewById(R.id.try_register);
        tryRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String login = newLogin.getText().toString();
                String password = newPassword.getText().toString();
                String conPassword = confirmPassword.getText().toString();

                if(!password.equals(conPassword))
                    Toast.makeText(SignUpActivity.this, "Check passwords!", Toast.LENGTH_SHORT).show();
                if(password.equals(conPassword) && !password.equals("")) {

                    switch (signUpAttempt(login, password)) {
                        case -1:
                            Toast.makeText(SignUpActivity.this,
                                    getResources().getString(R.string.login_taken),
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case 0:
                            Toast.makeText(SignUpActivity.this,
                                    getResources().getString(R.string.log_or_pass_invalid),
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            databaseHandler.addUser(new User(login, password.hashCode(), null, null, null, null, null, null));
                            onBackPressed();
                            Toast.makeText(SignUpActivity.this,
                                    getResources().getString(R.string.sign_up_success),
                                    Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        });
    }

    Integer signUpAttempt(String login, String password) {
        if(databaseHandler.containsLogin("users", login)) {
            return -1;
        } else if(login.equals("") || password.length() < 6) {
            return 0;
        } else {
            return 1;
        }
    }
}