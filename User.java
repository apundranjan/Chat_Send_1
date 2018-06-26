package com.example.dellpc.yef_chat;

public class User {

        String name,email,password ;
        int phone;


        public User(String name ,String email ,String password ,int phone){

            this.name = name;
            this.email = email;
            this.password = password;
            this.phone = phone;


        }


        public User(String name ,String password){

            this.name = name;
            this.password = password;
            this.phone = -1;
            this.email = "";


        }


}
