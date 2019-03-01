package com.pole6lynn.primiarydemo.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.pole6lynn.primiarydemo.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class DataStoreDemoActivity extends BaseActivity {
    private static final String TAG = "DataStoreDemoActivity";

    private EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_store_demo);
        mEdit = findViewById(R.id.data_edit);
        String inputText = loadData();
        if (!TextUtils.isEmpty(inputText)) {
            mEdit.setText(inputText);
            mEdit.setSelection(inputText.length());
            Toast.makeText(this, "Restoring succeeded.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String data = mEdit.getText().toString();
        save(data);
    }

    public void save(String data) {
        FileOutputStream fileOutputStream = null;
        BufferedWriter writer = null;
        try {
            fileOutputStream = openFileOutput("pole", Context.MODE_APPEND);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            writer.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveDataBySharedPre() {
        SharedPreferences.Editor editor = getSharedPreferences("data",
                MODE_PRIVATE).edit();
        editor.putString("name","Tom");
        editor.putInt("age", 18);
        editor.putBoolean("married", false);
        editor.apply();

    }

    private void readData() {
        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        String name = preferences.getString("name", "");
        int age = preferences.getInt("age", 0);
        boolean married = preferences.getBoolean("married", false);
    }

    public String loadData() {
        FileInputStream inputStream = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            inputStream = openFileInput("pole");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            if ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return content.toString();
    }
}
