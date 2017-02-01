package fr.imerir.yora.services;

import com.squareup.otto.Bus;

import fr.imerir.yora.infrastructure.YoraApplication;

public abstract class BaseLiveService {
    protected final Bus bus;
    protected final YoraWebService api;
    protected final YoraApplication application;

    protected BaseLiveService(YoraApplication application, YoraWebService api) {
        this.bus = application.getBus();
        this.api = api;
        this.application = application;
        bus.register(this);
    }
}
