package com.oz90.dynamicmetronome.entities;

import com.google.gson.annotations.SerializedName;
import com.oz90.dynamicmetronome.db.MetronomeDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Orlando on 04/07/2016.
 */
@Table(database = MetronomeDataBase.class)
public class Section extends BaseModel {
    public final static String NAME_DEFAULT = "Default section";
    public final static String NEW_NAME_DEFAULT = "New metronome";
    public final static int BEAT_DEFAULT = 120;
    public final static int BEATS_PER_BAR_DEFAULT = 4;
    public final static int REPETITION_DEFAULT = 1;
    public final static int TOP_BEATS_PER_BAR = 16;
    public static final int BEAT_MAX_DEFAULT = 350;

    public Section() {
    }

    public Section(int sectionId, int trackId, String name, int beat, long miliseconds, int beatsPerBar, int repetition) {
        this.sectionId = sectionId;
        this.trackId = trackId;
        this.name = name;
        this.beat = beat;
        this.miliseconds = miliseconds;
        this.beatsPerBar = beatsPerBar;
        this.repetition = repetition;
    }

    public Section(String name, int beat, long miliseconds, int beatsPerBar, int repetition) {
        this.name = name;
        this.beat = beat;
        this.miliseconds = miliseconds;
        this.beatsPerBar = beatsPerBar;
        this.repetition = repetition;
    }

    @SerializedName("section_id")
    @PrimaryKey(autoincrement = true)
    private int sectionId;

    @SerializedName("track_id")
    @ForeignKey(tableClass = Track.class)
    private int trackId;

    @Column private String name;

    @Column private int beat;

    @Column private long miliseconds;

    @SerializedName("beats_per_bar")
    @Column private int beatsPerBar;

    @Column private int repetition;

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBeat() {
        return beat;
    }

    public void setBeat(int beat) {
        this.beat = beat;
    }

    public long getMiliseconds() {
        return miliseconds;
    }

    public void setMiliseconds(long miliseconds) {
        this.miliseconds = miliseconds;
    }

    public int getBeatsPerBar() {
        return beatsPerBar;
    }

    public void setBeatsPerBar(int beatsPerBar) {
        this.beatsPerBar = beatsPerBar;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj instanceof Section) {
            Section section = (Section) obj;
            equal = this.getSectionId() == section.getSectionId();
        }
        return equal;
    }
}
