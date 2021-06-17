 package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.core.DbxRequestUtil;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

 public class auth4 extends AppCompatActivity{

    private Button messege;
    private Button back3;
    private Button next3;
    private EditText editText;
    private TextView text;
    private String ranStr = "";
    private String number;
    String answerHTTP;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth4);

        setRanStr();

        back3 = findViewById(R.id.back_btn3);
        next3 = findViewById(R.id.next_btn3);
        editText = findViewById(R.id.editTextNumber);
        messege = findViewById(R.id.messege);
        text = findViewById(R.id.textView5);

        Intent intent = getIntent();
        number = (String) intent.getExtras().get("number");

        SQLiteDatabase lastuser = openOrCreateDatabase("lastuser", MODE_PRIVATE, null);
        lastuser.execSQL("create table if not exists lastuser\n" +
                "(\n" +
                "\tUserPhone varchar(10) \n" +
                ");");



        SQLiteDatabase usersDataBase = openOrCreateDatabase("users", MODE_PRIVATE, null);
        usersDataBase.execSQL("create table if not exists users\n" +
                "(\n" +
                "\tUserPhone varchar(10), \n" +
                "\tUserMiddlename text, \n" +
                "\tUserDopInfo text, \n" +
                "\tUserName text, \n" +
                "\tUserSurname text, \n" +
                "\tUserBirthday varchar(1000), \n" +
                "\tUserPolis varchar(1000) \n" +
                ");");


        Cursor cper = usersDataBase.rawQuery("select UserPhone from users where UserPhone=?", new String[]{number});



        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    sThread.close();
                    Intent intent = new Intent(getApplicationContext(), auth3.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
        });

        next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(ranStr);
                if((editText.getText().toString()).equals(ranStr)) {
                    if(cper.moveToFirst()){
                        sThread.close();
                        lastuser.execSQL("insert into lastuser (UserPhone) values ('"+number+"')");
                        Intent intent_dial = new Intent(getApplicationContext(), MainPage2.class);
                        intent_dial.putExtra("number", number);
                        intent_dial.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent_dial);
                        overridePendingTransition(0, 0);
                    }
                    else {
                        sThread.close();
                        Intent intent_reg = new Intent(getApplicationContext(), Reg.class);
                        intent_reg.putExtra("from", "auth4");
                        intent_reg.putExtra("number", number);
                        intent_reg.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent_reg);
                        overridePendingTransition(0, 0);
                    }
                }
            }
        });

        messege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                try {
//                    new SendPhone().execute("http://localhost:8000/");
//                } catch (Exception e){
//                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                }

                try {
                    DefaultHttpClient hc = new DefaultHttpClient();
                    ResponseHandler<String> res = new BasicResponseHandler();
                    HttpPost postMethod = new HttpPost("http://localhost:8000/");
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    // ключ - "json", параметр - json в виде строки
                    nameValuePairs.add(new BasicNameValuePair("json", getJSON()));
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
                    postMethod.setEntity(entity);
                    answerHTTP = hc.execute(postMethod, res);

                    Toast.makeText(getApplicationContext(), answerHTTP, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }



//                String [] strings = new String[]{getString(R.string.Время_на_подтверждение1),
//                                                 getString(R.string.Время_на_подтверждение2),
//                                                 getString(R.string.Отправить_повторно)};
//                setRanStr();
//                 snackBarView(v,editText);
//                 messege.setClickable(false);
//                 messege.setVisibility(View.GONE);
//
//                 new sThread("s", new In() {
//                     @Override
//                     public void act(String s) {
//                         text.setText(s);
//                     }
//                     @Override
//                     public void anact(String s){
//                         text.setText(s);
//                         text.setOnClickListener(new View.OnClickListener() {
//                             @Override
//                             public void onClick(View v) {
//
//                                 setRanStr();
//                                 snackBarView(v,editText);
//                                 text.setClickable(false);
//                                 new sThread("s", new In() {
//
//                                     @Override
//                                     public void act(String s) {
//                                         text.setText(s);
//                                     }
//                                     @Override
//                                     public void anact(String s){
//                                         text.setText(s);
//                                         text.setClickable(true);
//                                     }
//                                 }, strings).start();
//
//
//                             }
//                         });
//                     }
//                 }, strings).start();
////                sendSMSMessage();
            }
        });
    }

     public String getJSON() // получаем json объект в виде строки
     {
         JSONObject bot = new JSONObject();
         try {
             bot.put("phone", number);
         } catch (JSONException e) {
             e.printStackTrace();
         }
         return bot.toString();
     }

//    class SendPhone extends AsyncTask<String, String, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                DefaultHttpClient hc = new DefaultHttpClient();
//                ResponseHandler<String> res = new BasicResponseHandler();
//                HttpPost postMethod = new HttpPost(params[0]);
//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//                // ключ - "json", параметр - json в виде строки
//                nameValuePairs.add(new BasicNameValuePair("json", getJSON()));
//                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
//                postMethod.setEntity(entity);
//                return hc.execute(postMethod, res);
//            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            Toast.makeText(getApplicationContext(), answerHTTP, Toast.LENGTH_LONG).show();
//        }
//    }

    protected void sendSMSMessage() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, "+71234567890", ranStr, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }


    void setRanStr () {
        ranStr = "";
        Random ran = new Random();
        for(int i = 0; i <= 3; i ++){
            int ranInt = ran.nextInt(9);
            ranStr += ranInt;

        }
    }


    public void snackBarView (View view, EditText editText){
        Snackbar snackbar = Snackbar.make(view ,ranStr, Snackbar.LENGTH_LONG);
        snackbar.show();
        snackbar.setAction("Вставить", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(ranStr);
            }
        });
    }

    interface In {
        void act(String s);
        void anact(String s);
    }



    static class sThread extends Thread{
        private static boolean isActive;
        private static String [] strings;

       static void close(){
            isActive = false;
        }

        private In in;
        public sThread(String name, In in, String [] strings){
            super(name);
            this.in = in;
            this.strings = strings;
        }

        @Override
        public void run() {

            isActive = true;
            int i = 10;
            String s;
            while (isActive == true){
                try{

                        if (i > 0) {
                            String si = i + "";

                            s = strings[0] + " " + si + " " + strings[1];
                            in.act(s);
                            Thread.sleep(1000);
                            i--;


                        } else {
                            in.anact(strings[2]);
                            break;

                    }
                }catch(Throwable t){
                }
            }
        }
    }




    public void onBackPressed(){
        sThread.close();
        Intent intent = new Intent(getApplicationContext(), auth3.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}