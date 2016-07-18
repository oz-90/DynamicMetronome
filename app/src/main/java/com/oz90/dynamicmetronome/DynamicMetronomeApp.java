package com.oz90.dynamicmetronome;

import android.app.Application;
import android.support.v4.app.Fragment;

import com.oz90.dynamicmetronome.libs.di.LibsModule;
import com.oz90.dynamicmetronome.metronome.ui.MetronomeView;
import com.oz90.dynamicmetronome.metronome.di.DaggerMetronomeComponent;
import com.oz90.dynamicmetronome.metronome.di.MetronomeComponent;
import com.oz90.dynamicmetronome.metronome.di.MetronomeModule;
import com.oz90.dynamicmetronome.presets.di.DaggerPresetsComponent;
import com.oz90.dynamicmetronome.presets.di.PresetsComponent;
import com.oz90.dynamicmetronome.presets.di.PresetsModule;
import com.oz90.dynamicmetronome.presets.ui.PresetsView;
import com.oz90.dynamicmetronome.presets.ui.adapters.OnItemClickListener;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Orlando on 05/07/2016.
 */
public class DynamicMetronomeApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initDB();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DBTearDown();
    }

    private void DBTearDown() {
        FlowManager.destroy();
    }

    private void initDB() {
        FlowManager.init(this);
    }

    public MetronomeComponent getMetronomeComponent(Fragment fragment, MetronomeView view) {
        return DaggerMetronomeComponent
                .builder()
                .libsModule(new LibsModule())
                .metronomeModule(new MetronomeModule(view))
                .build();
    }

    public PresetsComponent getPresetsComponent(Fragment fragment, PresetsView view, OnItemClickListener clickListener) {
        return DaggerPresetsComponent
                .builder()
                .libsModule(new LibsModule())
                .presetsModule(new PresetsModule(view, clickListener))
                .build();
    }
}
