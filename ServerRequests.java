package com.example.dellpc.yef_chat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Entity;
import android.os.AsyncTask;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class ServerRequests {


    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://lok8.hostingsiteforfree.com";

    public ServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please Wait...");

    }

    public void storeUserDataInBackground(User user, GetUserCallback userCallback) {
        progressDialog.show();
        new StoreUserDataAsyncTask(user, userCallback).execute();
    }

    public void fetchUserDataInBackground(User user, GetUserCallback callback) {
        progressDialog.show();
        new fetchUserDataAsyncTask(user, callback).execute();

    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {
        User user;
        GetUserCallback userCallback;

        publicStoreUserDataAsyncTask(User user, GetUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("name", user.name));
            dataToSend.add(new Basic NameValuePair("phone", user.phone + ""));
            dataToSend.add(new Basic NameValuePair("email", user.email));
            dataToSend.add(new Basic NameValuePair("password", user.password));



            /*possible alternative code found on stack overflow don't know exactly what to do from here.
ContentValues values= new ContentValues();
values.put("firstname", user.name);
values.put("lastname", user.email);
values.put("age", user.phone + "");
values.put("password",user.password);
            */

            HttpParams httpRequestParams = new Basic HttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new Default HttpClient(httpRequestParams);
            HttpClient post = new HttpPost(SERVER_ADDRESS + "Register.php");

            try {
                post.setEntity(new URLEncoder FormEntity(values));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallback.done(null);

            super.onPostExecute(aVoid);
        }
    }

    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallback;

        publicfetchUserDataAsyncTask(User user, GetUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }

        @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();

            dataToSend.add(new Basic NameValuePair("emailaddress", user.name));
            dataToSend.add(new BasicNameValuePair("password", user.password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpClient post = new HttpPost(SERVER_ADDRESS + "FetchUserData.php");

            User returnedUser = null;
            try {
                post.setEntity(new URLEncoder FormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = Entity.Utils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                if (jObject.length() == 0) {
                    returnedUser = null;
                } else {
                    String name1 = jObject.getString("firstname");
                    int phone1 = jObject.getInt("phone");

                    returnedUser = new User(name1, user.email);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallback.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }


}
