package com.oz90.dynamicmetronome.presets;

import com.oz90.dynamicmetronome.entities.Section;
import com.oz90.dynamicmetronome.presets.events.PresetsEvent;

/**
 * Created by Orlando on 16/07/2016.
 */
public interface PresetsPresenter {
    void subscribeEventbus();
    void onResume();
    void onPause();
    void onDestroy();

    void getPresets();
    void deletePreset(Section section);
    void undoDelete(Section section);

    void onEventMainThread(PresetsEvent event);
}
