package com.example.dellpc.yef_chat;


import android.content.Context;
import android.content.SharedPreferences;

class UserLocalStore {

        public static final String SP_NAME = "UserDetails";
        SharedPreferences localdatabase;

        public UserLocalStore(Context context) {

            localdatabase = context.getSharedPreferences(SP_NAME ,0);

        }


        public void storeUserData(User user){

            SharedPreferences.Editor spEditor  =localdatabase.edit();

            spEditor.putString("name",user.name);
            spEditor.putString("email",user.email);
            spEditor.putString("password",user.password);
            spEditor.putInt("phone", user.phone);


        }


        public User getLoggedInUser(){

            String name = localdatabase.getString("name", " ");
            String email = localdatabase.getString("email", " ");
            String password = localdatabase.getString("password", " ");
            int phone = localdatabase.getInt("phone",-1);
            User storeduser = new User(name,email,password,phone);
            return  storeduser;
        }


        public void setUserLoggedIn(boolean loggedIn){

            SharedPreferences.Editor spEditor  =localdatabase.edit();
            spEditor.putBoolean("loggedIn", loggedIn);
            spEditor.commit();


        }


        public boolean getUserLoggedIn(){

            if(localdatabase.getBoolean("LoggedIn",false)== true){

                return true;

            }
            else{

                return false;


            }

        }

        public void clearUserdata(){

            SharedPreferences.Editor spEditor = localdatabase.edit();
            spEditor.clear();
            spEditor.commit();


        }

    }

