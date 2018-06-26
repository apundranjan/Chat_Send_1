package com.example.dellpc.yef_chat;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterAndLogin extends Activity implements View.OnClickListener {

    Button bt_logout;
    EditText et_email, et_Name, et_phone;

    UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_and_login);


        et_Name = (EditText) findViewById(R.id.et_Name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_phone = (EditText) findViewById(R.id.et_phone);
        bt_logout = (Button) findViewById(R.id.bt_logout);
        et_email = (EditText) findViewById(R.id.et_email);


        bt_logout.setOnClickListener(this);


        userLocalStore = new UserLocalStore(this);

    }
        @Override
        protected void onStart () {
            super.onStart();
            if (authenticate() == true) {

                DisplayUserDetails();

            }
            else {

                startActivity(new Intent(RegisterAndLogin.this, Login.class));

            }


        }

        private void DisplayUserDetails () {

            User user = userLocalStore.getLoggedInUser();

            et_Name.setText(user.name);
            et_email.setText(user.email);
            et_phone.setText(user.phone + "");


        }

        public boolean authenticate () {

            return userLocalStore.getUserLoggedIn();


        }


        @Override
        public void onClick (View v){

            switch (v.getId()) {

                case R.id.bt_logout:

                    userLocalStore.clearUserdata();
                    userLocalStore.setUserLoggedIn(false);

                    startActivity(new Intent(this, Login.class));

                    break;


            }


        }


    }
}
