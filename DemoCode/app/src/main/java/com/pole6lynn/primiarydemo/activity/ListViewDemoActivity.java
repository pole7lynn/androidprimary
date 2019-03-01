package com.pole6lynn.primiarydemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pole6lynn.primiarydemo.R;
import com.pole6lynn.primiarydemo.util.Fruit;
import com.pole6lynn.primiarydemo.util.FruitAdapter;

import java.util.ArrayList;

public class ListViewDemoActivity extends BaseActivity {
    private ArrayList<Fruit> mFruitLists = new ArrayList<>();
    private FruitAdapter mFruitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_demo);

        initFruitData();
        mFruitAdapter = new FruitAdapter(this, R.layout.fruit_item, mFruitLists);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(mFruitAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = mFruitLists.get(position);
                Toast.makeText(ListViewDemoActivity.this,
                        fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initFruitData() {
        for (int i = 0; i < 2; i++) {
            Fruit apple = new Fruit("Apple", R.drawable.apple_pic);
            mFruitLists.add(apple);
            Fruit banana = new Fruit("Banana", R.drawable.banana_pic);
            mFruitLists.add(banana);
            Fruit orange = new Fruit("Orange", R.drawable.orange_pic);
            mFruitLists.add(orange);
            Fruit watermelon = new Fruit("Watermelon", R.drawable.watermelon_pic);
            mFruitLists.add(watermelon);
            Fruit pear = new Fruit("Pear", R.drawable.pear_pic);
            mFruitLists.add(pear);
            Fruit grape = new Fruit("Grape", R.drawable.grape_pic);
            mFruitLists.add(grape);
            Fruit pineapple = new Fruit("Pineapple", R.drawable.pineapple_pic);
            mFruitLists.add(pineapple);
            Fruit strawberry = new Fruit("Strawberry", R.drawable.strawberry_pic);
            mFruitLists.add(strawberry);
            Fruit cherry = new Fruit("Cherry", R.drawable.cherry_pic);
            mFruitLists.add(cherry);
            Fruit mango = new Fruit("Mango", R.drawable.mango_pic);
            mFruitLists.add(mango);
        }
    }
}
