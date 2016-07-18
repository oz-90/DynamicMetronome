package com.oz90.dynamicmetronome.presets.ui;

import com.oz90.dynamicmetronome.entities.Section;

import java.util.List;

/**
 * Created by Orlando on 04/07/2016.
 */
public interface PresetsView {
    void setSections(List<Section> sectionList);
    void sectionDeleted(Section section);
    void refreshSections();
    void onError(String error);
}
