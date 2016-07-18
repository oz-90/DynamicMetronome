package com.oz90.dynamicmetronome.metronome;

import com.oz90.dynamicmetronome.entities.Section;

/**
 * Created by Orlando on 04/07/2016.
 */
public interface MetronomeRepository {
    void getDefaultSection();
    void getSection(int sectionId);
    void updateSection(Section section);
    void createNewSection(String name);
}
