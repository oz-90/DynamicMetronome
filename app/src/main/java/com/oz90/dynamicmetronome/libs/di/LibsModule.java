package com.oz90.dynamicmetronome.libs.di;


import android.app.Activity;
import android.support.v4.app.Fragment;

import com.oz90.dynamicmetronome.libs.GreenRobotEventBus;
import com.oz90.dynamicmetronome.libs.base.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Orlando on 20/06/2016.
 */
@Module
public class LibsModule {
    /*private Fragment fragment;

    public LibsModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @Singleton
    Fragment providesFragment() {
        return this.fragment;
    }*/

    @Provides
    @Singleton
    EventBus providesEventBus(org.greenrobot.eventbus.EventBus eventBus) {
        return new GreenRobotEventBus(eventBus);
    }

    @Provides
    @Singleton
    org.greenrobot.eventbus.EventBus providesLibraryEventBus() {
        return org.greenrobot.eventbus.EventBus.getDefault();
    }
}
