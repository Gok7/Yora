package fr.imerir.yora.services;


import android.os.Handler;

import com.squareup.otto.Bus;

import java.util.Random;

import fr.imerir.yora.infrastructure.YoraApplication;

public abstract class BaseInMemoryService {

    protected final Bus bus;
    protected final YoraApplication application;
    protected final Handler handler;
    protected final Random random;

    protected BaseInMemoryService(YoraApplication application) {

        this.application = application;
        this.bus = application.getBus();
        handler = new Handler(); //convenient for multi-thread. synchronising access main UI thread.
        //// we use it to delay a message
        //run some code in the future on the main UI thread with the handler object.
        random = new Random();
        bus.register(this);
    }

    protected void invokeDelayed(Runnable runnable, long millisecondMin, long millisecondMax) {

        if (millisecondMax > millisecondMax)
            throw new IllegalArgumentException("min must be smaller than max");

        //get random integer between two integers.
        long delay = (long) (random.nextDouble() * (millisecondMax - millisecondMin)) + millisecondMin;
        handler.postDelayed(runnable, delay);   //run this runnable after this delay.
        //tells the handler to invoke the run method on the runnable object when the delay expires.
        //but don't block the UI thread. When you run runnable make sure it's running on UI thread.
    }

    //post event to event bus after delay
    protected void postDelayed(final Object event, long millisecondMin, long millisecondMax) {

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                bus.post(event);
            }
        }, millisecondMin, millisecondMax);
    }

    protected void postDelayed(Object event, long milliseconds) {

        postDelayed(event, milliseconds, milliseconds);
    }

    protected void postDelayed(Object event) {//two arbitrerly chosen delays

        postDelayed(event, 600, 1200);//for simulating network lag on a phone.
    }
}
