package com.oz90.dynamicmetronome;

import com.oz90.dynamicmetronome.entities.Section;

/**
 * Created by Orlando on 17/07/2016.
 */
public interface MainView {
    void refreshPresets();
    void getSelectedSection(Section section);
    int getPlayingSection();
    void getMainMetronome();
}
