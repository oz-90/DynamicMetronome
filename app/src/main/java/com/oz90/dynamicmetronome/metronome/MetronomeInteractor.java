package com.oz90.dynamicmetronome.metronome;

import com.oz90.dynamicmetronome.entities.Section;

/**
 * Created by Orlando on 04/07/2016.
 */
public interface MetronomeInteractor {
    void executeGet();
    void executeGetSection(int sectionId);
    void executeUpdate(Section section);
    void executeCreate(String name);
}
