package com.example.sa.students_android;

import android.icu.text.DateFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by sa on 20.06.17.
 */

public class RegistrationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                    Toast.makeText(RegistrationActivity.this, "Check passwords!", Toast.LENGTH_SHORT).show();
                //boolean success = attemptRegistering(login, password);
                if(password.equals(conPassword) && !password.equals("")) {
                    Boolean success = attemptRegistering(login, password);
                    //if(success)
                        //onBackPressed();
                }
            }
        });
    }

    boolean attemptRegistering(String login, String password) {

        if(MainActivity.databaseHandler.getValue(login) > 0) {
            Toast.makeText(RegistrationActivity.this, getResources().getString(R.string.loginTaken), Toast.LENGTH_SHORT).show();
        } else if(login.equals("") || password.length() < 6) {
            Toast.makeText(RegistrationActivity.this, getResources().getString(R.string.lOpInvalid), Toast.LENGTH_SHORT).show();
        } else {
            MainActivity.databaseHandler.addItem("users", login, password.hashCode());
            Toast.makeText(RegistrationActivity.this, getResources().getString(R.string.regSuccess), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
//        if(MainActivity.users.containsKey(login)) {
//            Toast.makeText(RegistrationActivity.this, getResources().getString(R.string.loginTaken), Toast.LENGTH_SHORT).show();
//        } else if(login.equals("") || password.length() < 6) {
//            Toast.makeText(RegistrationActivity.this, getResources().getString(R.string.lOpInvalid), Toast.LENGTH_SHORT).show();
//        } else {
//            MainActivity.users.put(login, password.hashCode());
//            Toast.makeText(RegistrationActivity.this, getResources().getString(R.string.regSuccess), Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        return false;
    }
}
