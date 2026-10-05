package ru.hse.vyshkat.config;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import ru.hse.vyshkat.domain.DefaultServiceCenter;
import ru.hse.vyshkat.domain.ServiceCenter;
import ru.hse.vyshkat.service.InventoryService;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ServiceCenter.class).to(DefaultServiceCenter.class);
        bind(InventoryService.class).in(Singleton.class);
    }
}