 package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class auth4 extends AppCompatActivity{

    private Button messege;
    private Button back3;
    private Button next3;
    private EditText editText;
    private TextView text;
    String ranStr = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth4);


        Random ran = new Random();
        for(int i = 0; i <= 4; i ++){
            int ranInt = ran.nextInt(9);
            ranStr += ranInt;
        }

        Toast toast = Toast.makeText(getApplicationContext(), ranStr, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();


        Button back3 = findViewById(R.id.back_btn3);
        Button next3 = findViewById(R.id.next_btn3);
        EditText editText = findViewById(R.id.editTextNumber);
        Button messege = findViewById(R.id.messege);
        TextView text = findViewById(R.id.textView5);



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
                sThread.close();
                System.out.println(editText.getText() + "" +
                        "");
                System.out.println(ranStr);
            if((editText.getText() + "").equals(ranStr)) {
                Intent intent = new Intent(getApplicationContext(), Reg.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
            }
        });

        messege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 toast.show();
                 messege.setClickable(false);
                 new sThread("s", new In() {
                     @Override
                     public void act(String s) {
                         text.setText(s);
                     }
                     @Override
                     public void anact(String s){
                         messege.setClickable(true);
                         text.setText(s);
                     }
                 }).start();

            }
        });

    }


    interface In {
        void act(String s);
        void anact(String s);
    }

    static class sThread extends Thread{
        private static boolean isActive;

       static void close(){
            isActive = false;
        }

        private In in;
        public sThread(String name, In in){
            super(name);
            this.in = in;
        }

        @Override
        public void run() {
            isActive = true;
            int i = 60;
            String s;
            while (isActive == true){
                try{

                        if (i > 0) {
                            s = "отправить код повторно через " + i;
                            in.act(s);
                            Thread.sleep(1000);
                            i--;


                        } else {
                            in.anact("отправить код повторно");
                            break;

                    }
                }catch(Throwable t){
                    //text.setText("не повезло");
                }
            }
        }
    }
}


