package com.oz90.dynamicmetronome.metronome.di;

import com.oz90.dynamicmetronome.libs.base.EventBus;
import com.oz90.dynamicmetronome.metronome.MetronomeInteractor;
import com.oz90.dynamicmetronome.metronome.MetronomeInteractorImpl;
import com.oz90.dynamicmetronome.metronome.MetronomePresenter;
import com.oz90.dynamicmetronome.metronome.MetronomePresenterImpl;
import com.oz90.dynamicmetronome.metronome.MetronomeRepository;
import com.oz90.dynamicmetronome.metronome.MetronomeRepositoryImpl;
import com.oz90.dynamicmetronome.metronome.ui.MetronomeView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Orlando on 05/07/2016.
 */
@Module
public class MetronomeModule {
    MetronomeView view;

    public MetronomeModule(MetronomeView view) {
        this.view = view;
    }

    @Provides @Singleton
    MetronomeView providesMetronomeView() {
        return this.view;
    }

    @Provides @Singleton
    MetronomePresenter providesMetronomePresenter(EventBus eventBus, MetronomeView view, MetronomeInteractor interactor) {
        return new MetronomePresenterImpl(eventBus, view, interactor);
    }

    @Provides @Singleton
    MetronomeInteractor providesMetronomeInteractor(MetronomeRepository repository) {
        return new MetronomeInteractorImpl(repository);
    }

    @Provides @Singleton
    MetronomeRepository providesMetronomeRepository(EventBus eventBus) {
        return new MetronomeRepositoryImpl(eventBus);
    }
}
