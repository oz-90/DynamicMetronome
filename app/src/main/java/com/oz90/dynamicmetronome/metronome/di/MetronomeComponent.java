package com.oz90.dynamicmetronome.metronome.di;

import com.oz90.dynamicmetronome.libs.di.LibsModule;
import com.oz90.dynamicmetronome.metronome.MetronomePresenter;
import com.oz90.dynamicmetronome.metronome.ui.MetronomeFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Orlando on 05/07/2016.
 */
@Singleton
@Component(modules = {MetronomeModule.class, LibsModule.class})
public interface MetronomeComponent {
    void inject(MetronomeFragment fragment);
}
