package com.pole6lynn.primiarydemo.broadcastdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.pole6lynn.primiarydemo.BroadcastDemoActivity;
import com.pole6lynn.primiarydemo.R;
import com.pole6lynn.primiarydemo.activity.BaseActivity;

public class LoginActivity extends BaseActivity {
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;

    private EditText mAccount;
    private EditText mPassword;
    private CheckBox mRememberPass;
    private Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mAccount = findViewById(R.id.account);
        mPassword = findViewById(R.id.password);
        mRememberPass = findViewById(R.id.remember_pass);
        boolean isRemember = mPreferences.getBoolean("remember_pass", false);
        if (isRemember) {
            String account = mPreferences.getString("account", "");
            String password = mPreferences.getString("password", "");
            boolean rememberPass = mPreferences.getBoolean("remember_password", false);
            mAccount.setText(account);
            mPassword.setText(password);
            mRememberPass.setChecked(rememberPass);
        }
        mLoginBtn = findViewById(R.id.login);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = mAccount.getText().toString();
                String password = mPassword.getText().toString();
                if (account.equals("admin") && password.equals("123456")) {
                    editor = mPreferences.edit();
                    if (mRememberPass.isChecked()) {
                        editor.putString("account", account);
                        editor.putString("password", password);
                        editor.putBoolean("remember_password", true);
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this,
                            BroadcastDemoActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,"Account or password is invalid.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
