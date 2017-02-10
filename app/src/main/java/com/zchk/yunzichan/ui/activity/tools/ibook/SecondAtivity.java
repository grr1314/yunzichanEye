package com.zchk.yunzichan.ui.activity.tools.ibook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


import com.zchk.yunzichan.R;
import com.zchk.yunzichan.ui.activity.tools.bean.Cuns;
import com.zchk.yunzichan.ui.activity.tools.luoji.MyDataBase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SecondAtivity extends Activity {

    EditText ed1, ed2;
    Button bt1;
    MyDataBase myDatabase;
    Cuns cun;
    int ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ed1 = (EditText) findViewById(R.id.editText1);
        ed2 = (EditText) findViewById(R.id.editText2);
        bt1 = (Button) findViewById(R.id.button1);
        myDatabase = new MyDataBase(this);

        Intent intent = this.getIntent();
        ids = intent.getIntExtra("ids", 0);
        if (ids != 0) {
            cun = myDatabase.getTiandCon(ids);
            ed1.setText(cun.getTitle());
            ed2.setText(cun.getContent());
        }
        bt1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                isSave();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //super.onBackPressed();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String times = formatter.format(curDate);
        String title = ed1.getText().toString();
        String content = ed2.getText().toString();
        if (ids != 0) {
            cun = new Cuns(title, ids, content, times);
            myDatabase.toUpdate(cun);
            Intent intent = new Intent(SecondAtivity.this, Note_Activity.class);
            startActivity(intent);
            SecondAtivity.this.finish();
        } else {
            if (title.equals("") && content.equals("")) {
                Intent intent = new Intent(SecondAtivity.this, Note_Activity.class);
                startActivity(intent);
                SecondAtivity.this.finish();
            } else {
                cun = new Cuns(title, content, times);
                myDatabase.toInsert(cun);
                Intent intent = new Intent(SecondAtivity.this, Note_Activity.class);
                startActivity(intent);
                SecondAtivity.this.finish();
            }

        }
    }
    private void isSave() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String times = formatter.format(curDate);
        String title = ed1.getText().toString();
        String content = ed2.getText().toString();
        if (ids != 0) {
            cun = new Cuns(title, ids, content, times);
            myDatabase.toUpdate(cun);
            Intent intent = new Intent(SecondAtivity.this, Note_Activity.class);
            startActivity(intent);
            SecondAtivity.this.finish();
        } else {
            cun = new Cuns(title, content, times);
            myDatabase.toInsert(cun);
            Intent intent = new Intent(SecondAtivity.this, Note_Activity.class);
            startActivity(intent);
            SecondAtivity.this.finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {

            default:
                break;
        }
        return false;
    }


}
