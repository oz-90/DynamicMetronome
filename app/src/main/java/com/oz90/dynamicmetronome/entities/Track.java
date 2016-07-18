package com.oz90.dynamicmetronome.entities;

import com.google.gson.annotations.SerializedName;
import com.oz90.dynamicmetronome.db.MetronomeDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

/**
 * Created by Orlando on 04/07/2016.
 */
@Table(database = MetronomeDataBase.class)
public class Track extends BaseModel {
    public final static int ID_DEFAULT = 1;
    public final static String NAME_DEFAULT = "Default track";

    public Track() {
    }

    public Track(int trackId, String title) {
        this.trackId = trackId;
        this.title = title;
    }

    @SerializedName("track_id")
    @PrimaryKey(autoincrement = true)
    private int trackId;

    @Column private String title;

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj instanceof Track) {
            Track track = (Track) obj;
            equal = this.trackId == track.trackId;
        }
        return equal;
    }
}
