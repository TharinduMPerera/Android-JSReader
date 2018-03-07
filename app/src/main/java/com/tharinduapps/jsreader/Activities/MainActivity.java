package com.tharinduapps.jsreader.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tharinduapps.jsreader.R;
import com.tharinduapps.jsreader.Utils.Utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.cancelBtn)
    RelativeLayout cancelBtn;

    @BindView(R.id.readJSBtn)
    Button readJSBtn;

    Invocable invocableEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        readJSBtn.setEnabled(false);
        cancelBtn.setVisibility(View.GONE);

        initJSReaderAsync();
    }

    private void initJSReaderAsync(){
        JSReaderAsync jsReaderAsync = new JSReaderAsync();
        jsReaderAsync.appContext = this;
        jsReaderAsync.execute();
    }

    private void setInvocableEngine(Invocable invocable){
        invocableEngine = invocable;
    }

    private void setFuncBtnEnabled(){
        readJSBtn.setEnabled(true);
    }

    @OnClick(R.id.loadJSToWebBtn)
    public void loadWebView(){
        Intent webViewIntent = new Intent(this, WebViewActivity.class);
        webViewIntent.putExtra("Input1", 12);
        webViewIntent.putExtra("Input2", 25);
        startActivity(webViewIntent);
        overridePendingTransition(R.anim.slide_to_top, R.anim.keep_active);
    }

    @OnClick(R.id.readJSBtn)
    public void readJsAndShowResult(){

        String result = null;
        try {
            if(invocableEngine!=null){
                org.mozilla.javascript.Context context = org.mozilla.javascript.Context.enter();
                context.setOptimizationLevel(-1);
                result = invocableEngine.invokeFunction("add",5,6)+"";
            } else{
                result = "Undefined InvocableEngine";
            }
        } catch (ScriptException e) {
            result = "ScriptException";
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            result = "NoSuchMethodException";
            e.printStackTrace();
        }
        Toast.makeText(this,"Result: "+result, Toast.LENGTH_SHORT).show();

    }

    private class JSReaderAsync extends AsyncTask<Void, Void, Void> {

        Context appContext;
        Invocable invocableEngine;

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                ScriptEngineManager manager = new ScriptEngineManager();
                org.mozilla.javascript.Context context = org.mozilla.javascript.Context.enter();
                context.setOptimizationLevel(-1);
                ScriptEngine engine = manager.getEngineByName("JavaScript");

                File file = Utils.getJSCacheFile(appContext);
                Reader reader = new FileReader(file);

                engine.put("out", System.out);
                engine.eval(reader);
                invocableEngine = (Invocable) engine;


//            if(manager.getEngineFactories()==null){
//                System.out.println("Null EngineFactories");
//            } else{
//                System.out.println(manager.getEngineFactories().size()+"Size");
//            }
//
//            for (ScriptEngineFactory se : new ScriptEngineManager().getEngineFactories()) {
//                System.out.println("se = " + se.getEngineName());
//                System.out.println("se = " + se.getEngineVersion());
//                System.out.println("se = " + se.getLanguageName());
//                System.out.println("se = " + se.getLanguageVersion());
//                System.out.println("se = " + se.getNames());
//            }
//            engine.eval("function hello(name) { print('Hello, ' + name) }");

//            engine.eval("print('HI');");
//        } catch (ScriptException e) {
//            e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch(Exception ex){
                Log.e("JSReader",ex.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setInvocableEngine(invocableEngine);
            setFuncBtnEnabled();
        }
    }
}
