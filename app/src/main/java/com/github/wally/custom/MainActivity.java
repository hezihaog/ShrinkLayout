package com.github.wally.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.wally.custom.viewprovider.Msg;
import com.github.wally.custom.viewprovider.MsgViewBinder;

import java.util.ArrayList;

import me.drakeet.multitype.MultiTypeAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MultiTypeAdapter adapter = new MultiTypeAdapter();
        adapter.register(Msg.class, new MsgViewBinder());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        //设置数据
        ArrayList<Msg> datas = getData();
        adapter.setItems(datas);
        adapter.notifyDataSetChanged();
    }

    private ArrayList<Msg> getData() {
        ArrayList<Msg> datas = new ArrayList<Msg>();
        for (int i = 0; i < 10; i++) {
            Msg msg = new Msg();
            msg.setTitle("我是第" + i + "组的item");
            datas.add(msg);
        }
        return datas;
    }
}