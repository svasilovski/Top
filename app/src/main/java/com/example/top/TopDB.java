package com.example.top;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = TopDB.NAME, version = TopDB.VERSION)
public class TopDB {

    public static final String NAME = "TopDatabase";
    public static final int VERSION = 1;
}
