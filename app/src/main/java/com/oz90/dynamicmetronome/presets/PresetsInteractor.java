package com.oz90.dynamicmetronome.presets;

import com.oz90.dynamicmetronome.entities.Section;

/**
 * Created by Orlando on 16/07/2016.
 */
public interface PresetsInteractor {
    void executeGetPresets();
    void executeDeletePreset(Section section);
}
