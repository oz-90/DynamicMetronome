package com.oz90.dynamicmetronome.metronome;

import com.oz90.dynamicmetronome.entities.Section;
import com.oz90.dynamicmetronome.metronome.events.MetronomeEvent;
import com.oz90.dynamicmetronome.metronome.ui.MetronomeView;

/**
 * Created by Orlando on 04/07/2016.
 */
public interface MetronomePresenter {
    void subscribeEventbus();
    void onResume();
    void onPause();
    void onDestroy();

    void getMainMetronome();
    void getSection(int sectionId);
    void updateSection(Section section);
    void createNewSection(String name);

    void onEventMainThread(MetronomeEvent event);

    MetronomeView getView();
}
