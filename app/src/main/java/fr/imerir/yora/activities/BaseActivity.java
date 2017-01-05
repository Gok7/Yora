package fr.imerir.yora.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.imerir.yora.infrastructure.YoraApplication;

public abstract class BaseActivity extends AppCompatActivity{

    protected YoraApplication application;

    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);

        application = (YoraApplication) getApplication();
    }
}
