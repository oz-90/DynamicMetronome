package com.oz90.dynamicmetronome.presets;

import android.util.Log;

import com.oz90.dynamicmetronome.entities.Section;
import com.oz90.dynamicmetronome.libs.base.EventBus;
import com.oz90.dynamicmetronome.presets.events.PresetsEvent;
import com.oz90.dynamicmetronome.presets.ui.PresetsView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Orlando on 16/07/2016.
 */
public class PresetsPresenterImpl implements PresetsPresenter {
    EventBus eventBus;
    PresetsView view;
    PresetsInteractor interactor;

    public PresetsPresenterImpl(EventBus eventBus, PresetsView view, PresetsInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void subscribeEventbus() {
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    public void onResume() {
        subscribeEventbus();
    }

    @Override
    public void onPause() {
        eventBus.unregister(this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void getPresets() {
        interactor.executeGetPresets();
    }

    @Override
    public void deletePreset(Section section) {
        interactor.executeDeletePreset(section);
    }

    @Override
    @Subscribe
    public void onEventMainThread(PresetsEvent event) {
        if (this.view != null) {
            String errorMsg = event.getError();
            if (errorMsg.length() == 0) {
                switch (event.getType()) {
                    case PresetsEvent.READ_EVENT:
                        view.setSections(event.getSectionList());
                        break;
                    case PresetsEvent.DELETE_EVENT:
                        Section section = event.getSectionList().get(0);
                        view.sectionDeleted(section);
                }
            }
            else {
                view.onError(errorMsg);
            }
        }
    }
}
