package com.tharinduapps.jsreader.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.tharinduapps.jsreader.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView WebView;

    private int input1;
    private int input2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        ButterKnife.bind(this);

        getValues();
    }

    private void getValues(){
        input1 = getIntent().getExtras().getInt("Input1");
        input2 = getIntent().getExtras().getInt("Input2");
    }


    @OnClick(R.id.cancelBtn)
    public void cancel() {
        finish();
        overridePendingTransition(R.anim.keep_active, R.anim.slide_to_bottom);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.keep_active, R.anim.slide_to_bottom);
    }
}
