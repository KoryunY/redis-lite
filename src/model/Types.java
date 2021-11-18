package model;

public enum Types {
    STRING,
    LIST,
    SET,
    ZSET,
    HASH;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
