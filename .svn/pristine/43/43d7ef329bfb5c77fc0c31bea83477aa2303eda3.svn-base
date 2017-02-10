package com.zchk.yunzichan.ui.activity.tools.ibook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;


import com.zchk.yunzichan.R;
import com.zchk.yunzichan.ui.activity.tools.bean.Cuns;
import com.zchk.yunzichan.ui.activity.tools.luoji.MyAdapter;
import com.zchk.yunzichan.ui.activity.tools.luoji.MyDataBase;

import java.util.ArrayList;


public class Note_Activity extends Activity {

    Button bt;
    ListView lv;
    LayoutInflater inflater;
    ArrayList<Cuns> array;
    MyDataBase mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);

        lv = (ListView) findViewById(R.id.listView1);
        bt = (Button) findViewById(R.id.button1);
        inflater = getLayoutInflater();

        mdb = new MyDataBase(this);
        array = mdb.getArray();
        MyAdapter adapter = new MyAdapter(inflater, array);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), SecondAtivity.class);
                intent.putExtra("ids", array.get(position).getIds());
                startActivity(intent);
                Note_Activity.this.finish();
            }
        });

        lv.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                // TODO Auto-generated method stub
                new AlertDialog.Builder(Note_Activity.this)
                        .setTitle("提示")
                        .setMessage("是否删除本条日记")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                mdb.toDelete(array.get(position).getIds());
                                array = mdb.getArray();
                                MyAdapter adapter = new MyAdapter(inflater, array);
                                lv.setAdapter(adapter);
                            }
                        })
                        .create().show();
                return true;
            }
        });

        bt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), SecondAtivity.class);
                startActivity(intent);
                Note_Activity.this.finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
//		case R.id.item1:
//			Intent intent=new Intent(getApplicationContext(),SecondAtivity.class);
//			startActivity(intent);
//			this.finish();
//			break;
//		case R.id.item2:
//			this.finish();
//			break;
//		default:
//			break;
        }
        return true;

    }

}
