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

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class auth4 extends AppCompatActivity{

    private Button messege;
    private Button back3;
    private Button next3;
    private EditText editText;
    private TextView text;
    String ranStr = "";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth4);

        setRanStr();

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

                System.out.println(ranStr);
            if((editText.getText() + "").equals(ranStr)) {
                sThread.close();
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
                                 }).start();


                             }
                         });
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
            int i = 10;
            String s;
            while (isActive == true){
                try{

                        if (i > 0) {
                            String si = i + "";
                            s = "Отправить код повторно через " + si + " сек";
                            in.act(s);
                            Thread.sleep(1000);
                            i--;


                        } else {
                            in.anact("Нажмите для повторного кода");
                            break;

                    }
                }catch(Throwable t){
                    //text.setText("не повезло");
                }
            }
        }
    }
}