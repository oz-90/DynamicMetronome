package com.oz90.dynamicmetronome.metronome.ui;

import com.oz90.dynamicmetronome.entities.Section;

/**
 * Created by Orlando on 04/07/2016.
 */
public interface MetronomeView {
    void setSection(Section section);
    void onError(String error);
    void createNewSection();
    int getPlayingSectionId();
    void getMainMetronome();
}
