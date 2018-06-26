package com.example.dellpc.yef_chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Register extends Activity implements View.OnClickListener {

    Button bt_register;
    EditText et_Name, et_email, et_phone, et_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bt_register = (Button) findViewById(R.id.bt_register);
        et_Name = (EditText) findViewById(R.id.et_Name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_pwd = (EditText) findViewById(R.id.et_pwd);

        bt_register.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bt_register:

                String name = et_Name.getText().toString();
                String email = et_email.getText().toString();
                int phone = Integer.parseInt(et_phone.getText().toString());
                String password = et_pwd.getText().toString();

                User user = new User(name, email, password, phone);

                registerUser(user);

                break;

        }

    }

    private void registerUser(User user) {

        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {

                startActivity(new Intent(Register.this, Login.class));

            }
        });


    }
}

