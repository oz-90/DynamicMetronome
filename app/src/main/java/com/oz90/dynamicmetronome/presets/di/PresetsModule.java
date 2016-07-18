package com.oz90.dynamicmetronome.presets.di;

import com.oz90.dynamicmetronome.entities.Section;
import com.oz90.dynamicmetronome.libs.base.EventBus;
import com.oz90.dynamicmetronome.presets.PresetsInteractor;
import com.oz90.dynamicmetronome.presets.PresetsInteractorImpl;
import com.oz90.dynamicmetronome.presets.PresetsPresenter;
import com.oz90.dynamicmetronome.presets.PresetsPresenterImpl;
import com.oz90.dynamicmetronome.presets.PresetsRepository;
import com.oz90.dynamicmetronome.presets.PresetsRepositoryImpl;
import com.oz90.dynamicmetronome.presets.ui.PresetsView;
import com.oz90.dynamicmetronome.presets.ui.adapters.OnItemClickListener;
import com.oz90.dynamicmetronome.presets.ui.adapters.PresetsAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Orlando on 16/07/2016.
 */
@Module
public class PresetsModule {
    PresetsView view;
    OnItemClickListener clickListener;

    public PresetsModule(PresetsView view, OnItemClickListener clickListener) {
        this.view = view;
        this.clickListener = clickListener;
    }

    @Provides
    @Singleton
    PresetsView providesPresetsView() {
        return this.view;
    }

    @Provides
    @Singleton
    PresetsPresenter providesPresetsPresenter(EventBus eventBus, PresetsView view, PresetsInteractor interactor) {
        return new PresetsPresenterImpl(eventBus, view, interactor);
    }

    @Provides
    @Singleton
    PresetsInteractor providesPresetsInteractor(PresetsRepository repository) {
        return new PresetsInteractorImpl(repository);
    }

    @Provides
    @Singleton
    PresetsRepository providesPresetsRepository(EventBus eventBus) {
        return new PresetsRepositoryImpl(eventBus);
    }

    @Provides
    @Singleton
    PresetsAdapter providesPresetsAdapter(List<Section> sectionList, OnItemClickListener clickListener) {
        return new PresetsAdapter(sectionList, clickListener);
    }

    @Provides
    @Singleton
    List<Section> providesEmptySectionList() {
        return new ArrayList<Section>();
    }

    @Provides
    @Singleton
    OnItemClickListener providesOnItemClickListener() {
        return this.clickListener;
    }
}
