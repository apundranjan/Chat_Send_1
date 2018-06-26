package com.example.dellpc.yef_chat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity implements View.OnClickListener{
        Button bt_login;
        EditText et_username;
        EditText et_password;
        TextView registerlink;
        UserLocalStore userLocalStore;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            bt_login = (Button)findViewById(R.id.bt_login);
            et_username=(EditText)findViewById(R.id.et_UserName);
            et_password=(EditText)findViewById(R.id.et_password);
            registerlink =(TextView)findViewById(R.id.registerLink);

            bt_login.setOnClickListener( this);

            registerlink.setOnClickListener(this);

            userLocalStore = new UserLocalStore(this);

        }


        @Override
        public void onClick(View v) {

            switch(v.getId()){

                case R.id.bt_login:
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                User user = new User(username,password);


                authenticate(user);
                break;

                case R.id.registerLink:

                startActivity(new Intent(this,Register.class));

                break;


            }

        }

        private void authenticate(User user) {

            ServerRequests serverrequests = new ServerRequests(this);
            serverrequests.fetchUserDataInBackground(user, new GetUserCallback() {
                @Override
                public void done(User returnedUser) {

                    if(returnedUser == null){

                        showErrorMessage();
                    }else{

                        LogUserIn(returnedUser);

                    }

                }
            });
        }

        private void showErrorMessage(){

            AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(Login.this);

            dialogbuilder.setMessage("Incorrect Details ");
            dialogbuilder.setPositiveButton("ok" , null);
            dialogbuilder.show();

        }

        private void LogUserIn(User returnedUser){

            userLocalStore.setUserLoggedIn(true);
            userLocalStore.storeUserData(returnedUser);

            startActivity(new Intent(this , MainActivity.class));

        }



    }



