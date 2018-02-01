package com.yolocc.stepviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StepView stepView = findViewById(R.id.step_view);
        stepView.setStepText(new String[]{"第一步", "第二步", "第三步"}, 1);
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("ni");
        linkedList.add(null);
        linkedList.add("hehe");
        for (String s : linkedList) {
            
        }
    }
}
