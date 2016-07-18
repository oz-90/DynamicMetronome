package com.oz90.dynamicmetronome.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Orlando on 04/07/2016.
 */
@Database(name = MetronomeDataBase.NAME, version = MetronomeDataBase.VERSION)
public class MetronomeDataBase {
    public static final int VERSION = 4;
    public static final String NAME = "Dynamic_Metronome";
}
