package com.oz90.dynamicmetronome.presets.di;

import com.oz90.dynamicmetronome.libs.di.LibsModule;
import com.oz90.dynamicmetronome.metronome.di.MetronomeModule;
import com.oz90.dynamicmetronome.presets.ui.PresetsFragment;
import com.oz90.dynamicmetronome.presets.ui.adapters.PresetsAdapter;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;

/**
 * Created by Orlando on 16/07/2016.
 */
@Singleton
@Component(modules = {PresetsModule.class, LibsModule.class})
public interface PresetsComponent {
    void inject(PresetsFragment fragment);
    void inject(PresetsAdapter adapter);
}
