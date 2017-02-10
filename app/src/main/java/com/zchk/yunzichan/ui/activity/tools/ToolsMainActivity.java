package com.zchk.yunzichan.ui.activity.tools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.ui.activity.tools.ibook.Note_Activity;

/**
 * Created by admin on 2016/11/16.
 */
public class ToolsMainActivity extends AppCompatActivity {
    private Button btn_Light;
    private Button btn_Calculator;
    private Button btn_NoteBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tools_activity);
        initViews();
    }

    private void initViews() {
        btn_Light = (Button) findViewById(R.id.btn_Light);
        btn_Light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ToolsMainActivity.this, LightActivity.class);
                startActivity(intent);
            }
        });

        btn_Calculator = (Button) findViewById(R.id.btn_Calculator);
        btn_Calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.android.calculator2", "com.android.calculator2.Calculator");
                startActivity(intent);
            }
        });

        btn_NoteBook = (Button) findViewById(R.id.btn_NoteBook);
        btn_NoteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ToolsMainActivity.this, Note_Activity.class);
                startActivity(intent);
            }
        });
    }
}
