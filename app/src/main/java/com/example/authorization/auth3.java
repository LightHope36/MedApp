package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Response;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.io.CharArrayWriter;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import android.os.AsyncTask;

public class auth3 extends AppCompatActivity  {

    private Button next2;
    private Button back2;
    private EditText reg;
    private TextView errortext;
    private EditText input;
    private StringWriter buffer;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth3);

        Button next2 = findViewById(R.id.next_btn2);
        Button back2 = findViewById(R.id.back_btn2);
        EditText reg = findViewById(R.id.region_input);
        EditText phone = findViewById(R.id.phone_input);
        phone.setText("9999999999");
        TextView errortext = findViewById(R.id.errortext);

        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone.getText().equals("") || reg.getText().equals("")) {
                    errortext.setText("Вы не ввели номер");
                }else {
                    if (reg.getText().length() <= 4 & reg.getText().charAt(0) == '+') {
                        if (phone.length() == 10) {
                            String number;
                            if(reg.equals("+7")){
                                number = "8"+phone.getText().toString();
                            } else {
                                number = reg.getText().toString() + phone.getText().toString();
                            }

//                            String data = sendHttpRequest(url, name);
//                            try {
//                                HttpURLConnection
//                                RequestQueue requestQueue = Volley.newRequestQueue(this);
//                                String URL = "http://...";
//                                JSONObject jsonBody = new JSONObject();
//                                jsonBody.put("phone", phone);
//                                final String requestBody = jsonBody.toString();
//
//                                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//                                        Log.i("VOLLEY", response);
//                                    }
//                                }, new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        Log.e("VOLLEY", error.toString());
//                                    }
//                                }) {
//                                    @Override
//                                    public String getBodyContentType() {
//                                        return "application/json; charset=utf-8";
//                                    }
//
//                                    @Override
//                                    public byte[] getBody() throws AuthFailureError {
//                                        try {
//                                            return requestBody == null ? null : requestBody.getBytes("utf-8");
//                                        } catch (UnsupportedEncodingException uee) {
//                                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
//                                            return null;
//                                        }
//                                    }
//
//                                    @Override
//                                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                                        String responseString = "";
//                                        if (response != null) {
//                                            responseString = String.valueOf(response.statusCode);
//                                            // can get more details such as response.headers
//                                        }
//                                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//                                    }
//                                };
//
//                                requestQueue.add(stringRequest);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }


                            Intent intent_dialogs = new Intent(getApplicationContext(), Dialogs.class);
                            intent_dialogs.putExtra("number", number);

                            Intent intent = new Intent(getApplicationContext(), auth4.class);
                            intent.putExtra("number", number);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        } else {
                            errortext.setText("Неверный номер телефона");
                        }
                    } else {
                        errortext.setText("Неверный код страны");
                    }
                }
            }
        });

        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), auth2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), auth2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

//    public class SendPostRequest extends AsyncTask<String, Void, String> {
//        protected void onPreExecute(){}
//        protected String doInBackground(String... arg0) {
//            try {
//                URL url = new URL("https://studytutorial.in/post.php");
//                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("phone", "abc");
//                postDataParams.put("email", "abc@gmail.com");
//                Log.e("params",postDataParams.toString());
//
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(15000 /* milliseconds */);
//                conn.setConnectTimeout(15000 /* milliseconds */);
//                conn.setRequestMethod("POST");
//                conn.setDoInput(true);
//                conn.setDoOutput(true);
//            }
//            catch(Exception e){
//                return new String("Exception: " + e.getMessage());
//            }
//
//        }
//
//        @Override
//        protected void onPostExecute(String result) {}
//    }


//    private class SendHttpRequestTask extends AsyncTask<String, Void, String>{
//
//        @Override
//        protected String doInBackground(String... params) {
//            String url = params[0];
//            String name = params[1];
//            String data = sendHttpRequest(url, name);
//            return data;
//        }
//
//        @Override
////        protected void onPostExecute(String result) {
////            edtResp.setText(result);
////            item.setActionView(null);
//        }
//    }

}

