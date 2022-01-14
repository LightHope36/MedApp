 package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();


    String url = "jdbc:mysql://server23.hosting.reg.ru/u0597423_medclick.kvantorium69";
    String username = "u0597423_medclic";
    String password = "kvantoriummagda";
    ResultSet cper;


    private String OpenTable = ("create table if not exists client (\n" +
            "\tclientid INT PRIMARY KEY AUTO_INCREMENT, \n" +
            "\tname varchar(15), \n" +
            "\tsurname varchar(15), \n" +
            "\tpatronymic varchar(15), \n" +
            "\tmedical_policy int, \n" +
            "\tPhone_number int, \n" +
            "\tsnils int, \n" +
            "\tEmail varchar(45), \n" +
            "\tdate_of_birth datetime, \n" +
            "\tmedical_history int, \n" +
            "\tcompanies_providing_medical_insurance int )\n");


    private String getUser="select UserPhone from users where Phone_number=" + number;

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
                    try {
                        new GetConnection().execute();
                        if (cper!=null){
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
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
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

                new SendPhone().execute();

                String [] strings = new String[]{getString(R.string.Время_на_подтверждение1),
                                                 getString(R.string.Время_на_подтверждение2),
                                                 getString(R.string.Отправить_повторно)};
                setRanStr();
                 snackBarView(v,editText);
                 messege.setClickable(false);
                 messege.setVisibility(View.GONE);

                 new sThread("s", new In() {
                     @Override
                     public void act(String s) {
                         text.setText(s);
                     }
                     @Override
                     public void anact(String s){
                         text.setText(s);
                         text.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {

                                 setRanStr();
                                 snackBarView(v,editText);
                                 text.setClickable(false);
                                 new sThread("s", new In() {

                                     @Override
                                     public void act(String s) {
                                         text.setText(s);
                                     }
                                     @Override
                                     public void anact(String s){
                                         text.setText(s);
                                         text.setClickable(true);
                                     }
                                 }, strings).start();


                             }
                         });
                     }
                 }, strings).start();
//                sendSMSMessage();
            }
        });
    }

//     public String getJSON() // получаем json объект в виде строки
//     {
//         JSONObject bot = new JSONObject();
//         try {
//             bot.put("phone", number);
//         } catch (JSONException e) {
//             e.printStackTrace();
//         }
//         return bot.toString();
//     }

    String post(String url, String json) throws IOException {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", number);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(JSON, json);
        System.out.println("gg");
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .post(body)
                .build();
        System.out.println("gg");
        try (Response response = client.newCall(request).execute()) {
            System.out.println("gg");
            return response.body().string();
        }
    }

    class SendPhone extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {

//                String json = "{\"phone\": \"" + number + "\"}";
//                String json = "{\"phone\": \"89806344281\"}";
                String json = "phone%99" + number + "";
//                String json = "{\"phone\": \"89806344281\"}";
                System.out.println(json);
                String response = post("http://89.223.30.250:8001/api/auth/phone/", json);
                System.out.println("final");
//                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                System.out.println(response);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            Toast.makeText(getApplicationContext(), answerHTTP, Toast.LENGTH_LONG).show();
        }
    }

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

    class GetConnection extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try{
                try (Connection conn = DriverManager.getConnection(url, username, password)){
                    Statement statement = conn.createStatement();
                    // создание таблицы
                    statement.executeUpdate(OpenTable);
                    cper = statement.executeQuery(getUser);
                    Log.e("Connection", "CONNECTED");

                }
                catch(Exception ex){
                    Log.e("error", ex.getMessage());
                }
            }
            catch(Exception e){
                Log.e("error", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            Toast.makeText(getApplicationContext(), answerHTTP, Toast.LENGTH_LONG).show();
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