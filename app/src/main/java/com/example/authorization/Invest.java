package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Invest extends AppCompatActivity {

    private String number;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest);

        Intent intent = getIntent();
        number = (String) intent.getExtras().get("number");
        back = findViewById(R.id.back_btn_in_invest);

//        settings();


//        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.mainlayout);
//
//        // Добавляем новый ImageView
//        ImageView imageView = new ImageView(Invest.this);
//        imageView.setImageResource(R.drawable.ic_profile);
//        LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(100, 100);
//        imageView.setLayoutParams(imageViewLayoutParams);
//
//        mainLayout.addView(imageView);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Chat.class);
                intent.putExtra("number", number);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });


//        final ViewGroup parentView = findViewById(R.id.parentView);
//        final View childView = parentView.findViewById(R.id.childView);
//
//        parentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                float parentHeight = parentView.getHeight();
//
//                ViewGroup.LayoutParams params = childView.getLayoutParams();
//                params.height = (int)(parentHeight * 5);
//
//                childView.setLayoutParams(params);
//                //We want the listener to be called only the first time(in case it is initialized in onCreate())
//                parentView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//            }
//        });
    }

    private void settings(){

//        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.mainlayout);
//
//        // Добавляем новый ImageView
//        ImageView imageView = new ImageView(Invest.this);
//        imageView.setImageResource(R.drawable.ic_profile);
//        LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(100, 100);
//        imageView.setLayoutParams(imageViewLayoutParams);
//
//        mainLayout.addView(imageView);
    }
}