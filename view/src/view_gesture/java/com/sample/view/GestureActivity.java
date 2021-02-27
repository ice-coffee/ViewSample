package com.sample.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

/**
 * @author mzp
 * date : 2020/10/30
 * desc :
 */
public class GestureActivity extends AppCompatActivity {
    private GridView contentGv;
    private ArrayList<String> itemList;
    private static final String TAG = "MainActivity";
    public static String NOTAG = "____QWM_____";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        initGridView();
        Log.i(TAG, "onCreate: "+getWindowManager().getDefaultDisplay().getWidth());
    }

    public void initGridView() {
        contentGv = (GridView) findViewById(R.id.content_gv);
        itemList = new ArrayList<>();
        itemList.add("手势基本测试");
        itemList.add("双击测试");
        itemList.add("拖拽测试");
        itemList.add("缩放测试");
        //添加换行
        listAddNullTag();

        itemList.add("旋转测试(第三方库)");
        itemList.add("拖拽测试(第三方库)");
        itemList.add("shove(第三方库)");
        itemList.add("自定义TextView");

        //添加换行
        listAddNullTag();

        contentGv.setAdapter(new MyGridAdapter(this, itemList));
        contentGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemStr = itemList.get(position);
                if ("手势基本测试".equals(itemStr)) {
                    startActivity(new Intent(GestureActivity.this, BaseTestActivity.class));
                }else if ("双击测试".equals(itemStr)) {
                    startActivity(new Intent(GestureActivity.this, DoubleTapActivity.class));
                }else if ("拖拽测试".equals(itemStr)) {
                    startActivity(new Intent(GestureActivity.this, DragTestActivity.class));
                }else if ("缩放测试".equals(itemStr)) {
                    startActivity(new Intent(GestureActivity.this, ScaleGestureActivity.class));
                }else if ("旋转测试(第三方库)".equals(itemStr)) {
                    startActivity(new Intent(GestureActivity.this, RotateGestureActivity.class));
                }else if ("拖拽测试(第三方库)".equals(itemStr)) {
                    startActivity(new Intent(GestureActivity.this, MoveGestureActivity.class));
                }else if ("shove(第三方库)".equals(itemStr)) {
                    startActivity(new Intent(GestureActivity.this, ShoveGestureActivity.class));
                }else if ("自定义TextView".equals(itemStr)) {
                    startActivity(new Intent(GestureActivity.this, CustomGestureTextView.class));
                }
            }
        });

    }

    public void listAddNullTag(){
        int numColumns = contentGv.getNumColumns();
        if(itemList.size()%numColumns!=0){//没有填充满的时候，添加空的标记
            int count = ( (itemList.size()/numColumns +1) * numColumns ) - itemList.size();
            for (int i = 0; i < count; i++) {
                itemList.add(NOTAG);
            }
        }
    }
}
