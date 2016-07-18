package com.oz90.dynamicmetronome.metronome;

import android.util.Log;

import com.oz90.dynamicmetronome.entities.Section;
import com.oz90.dynamicmetronome.libs.base.EventBus;
import com.oz90.dynamicmetronome.metronome.events.MetronomeEvent;
import com.oz90.dynamicmetronome.metronome.ui.MetronomeView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Orlando on 04/07/2016.
 */
public class MetronomePresenterImpl implements MetronomePresenter {
    private EventBus eventBus;
    private MetronomeView view;
    MetronomeInteractor interactor;

    public MetronomePresenterImpl(EventBus eventBus, MetronomeView view, MetronomeInteractor interactor) {
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
    public void getMainMetronome() {
        interactor.executeGet();
    }

    @Override
    public void getSection(int sectionId) {
        interactor.executeGetSection(sectionId);
    }

    @Override
    public void updateSection(Section section) {
        interactor.executeUpdate(section);
    }

    @Override
    public void createNewSection(String name) {
        interactor.executeCreate(name);
    }

    @Override
    @Subscribe
    public void onEventMainThread(MetronomeEvent event) {
        if (this.view != null) {
            String errorMsg = event.getError();
            if (errorMsg.length() == 0) {
                switch (event.getType()) {
                    case MetronomeEvent.READ_EVENT:
                    case MetronomeEvent.CREATE_EVENT:
                        view.setSection(event.getSection());
                        break;
                }
            }
            else {
                view.onError(errorMsg);
            }
        }
    }

    @Override
    public MetronomeView getView() {
        return this.view;
    }
}
