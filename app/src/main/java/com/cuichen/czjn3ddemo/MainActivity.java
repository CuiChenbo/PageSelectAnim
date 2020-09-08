package com.cuichen.czjn3ddemo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

//import com.cuichen.czjn3ddemo.ui.Star3Activity;
//import com.cuichen.czjn3ddemo.ui.StarActivity;
//import com.cuichen.czjn3ddemo.ui.StarActivity;
import com.cuichen.czjn3ddemo.ui.VViewPagerAct;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void start( Class clazz){
        startActivity(new Intent(this , clazz));
    }

    public void web(View view) {
        start(VViewPagerAct.class);
    }
}
