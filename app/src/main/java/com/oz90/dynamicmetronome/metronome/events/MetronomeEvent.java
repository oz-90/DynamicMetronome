package com.oz90.dynamicmetronome.metronome.events;

import com.oz90.dynamicmetronome.entities.Section;

/**
 * Created by Orlando on 04/07/2016.
 */
public class MetronomeEvent {
    private int type;
    private String error;
    private Section section;

    public final static int READ_EVENT = 0;
    public final static int UPDATE_EVENT = 1;
    public final static int CREATE_EVENT = 2;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}
