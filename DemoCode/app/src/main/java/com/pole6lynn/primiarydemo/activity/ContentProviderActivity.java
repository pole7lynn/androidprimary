package com.pole6lynn.primiarydemo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.pole6lynn.primiarydemo.R;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderActivity extends BaseActivity {
    private Button mMakeCallBtn;
    private ListView mListView;

    private List<String> mContentsList = new ArrayList<>();
    private ArrayAdapter<String> mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_provider_demo);
        mListView = findViewById(R.id.contents_view);
        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                mContentsList);
        mListView.setAdapter(mArrayAdapter);
        if (ContextCompat.checkSelfPermission(ContentProviderActivity.this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ContentProviderActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            readContents();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
            int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    readContents();
                } else {
                    Toast.makeText(this, "You denied the permission.",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void readContents() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone
                            .CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract
                            .CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract
                            .CommonDataKinds.Phone.NUMBER));
                    mContentsList.add(name + "\n" + number);
                }
            }
            mArrayAdapter.notifyDataSetChanged();
        }catch (SecurityException e){
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


    }

    private void call() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
