package com.astimac.fb.popup.example;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.astimac.fb.popup.example.fragment.MainFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        if(savedInstanceState == null) {
            FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
            mTransaction.replace(R.id.fragment_container, MainFragment.getInstance());
            mTransaction.commit();
        }
    }
}
