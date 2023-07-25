package com.thm.aiarena.communication;

public class DataObject {
    public static final int STOP = 0;
    public static final int ADD_OBJECT = 1;
    public static final int REMOVE_OBJECT = 2;
    public int type;
    public int x;
    public int y;
    public int z;

    public DataObject() {}

    public DataObject(int[] buf) {
        update(buf);
    }

    public DataObject update(int[] buf) {
        if (buf != null) {
            type = buf[0];
            x = buf[1];
            y = buf[2];
            z = buf[3];
        }
        return this;
    }

    public int[] array() {
        return new int[] {type, x, y, z};
    }
}
