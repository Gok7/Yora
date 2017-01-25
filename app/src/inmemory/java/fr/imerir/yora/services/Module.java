package fr.imerir.yora.services;

import fr.imerir.yora.infrastructure.YoraApplication;

public class Module {

    public static void register(YoraApplication application) {

        new InMemoryAccountService(application);
        new InMemoryContactsService(application);
    }
}