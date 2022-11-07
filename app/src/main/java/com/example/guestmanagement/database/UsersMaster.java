package com.pansala.Database;

import android.provider.BaseColumns;

public final class UsersMaster {
    public UsersMaster() {
    }

    public static class Users implements BaseColumns{
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_FIRST = "first";
        public static final String COLUMN_LAST = "last";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_CONTACT = "contact";
        public static final String COLUMN_CONFIRM = "confirm";
        public static final String COLUMN_PARTICIPANT = "participant";
        public static final String COLUMN_NOTE = "note";
    }
}
