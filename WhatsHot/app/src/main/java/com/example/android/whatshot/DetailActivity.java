package com.example.android.whatshot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

// TODO: Yany, Kerry
public class DetailActivity extends AppCompatActivity {
    private TextView mLocationNameTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mLocationNameTV=(TextView)findViewById(R.id.location_name);

    }
}
