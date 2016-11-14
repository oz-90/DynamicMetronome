package com.oz90.dynamicmetronome.presets.events;

import com.oz90.dynamicmetronome.entities.Section;

import java.util.List;

/**
 * Created by Orlando on 16/07/2016.
 */
public class PresetsEvent {
    private int type;
    private List<Section> sectionList;
    private String error;

    public final static int READ_EVENT = 0;
    public final static int DELETE_EVENT = 1;
    public final static int UNDO_EVENT = 2;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
