package dao;

import model.Types;

import java.util.HashMap;
import java.util.Timer;

public class DataAccess {
    private final HashMap<String, Object> db;
    private final HashMap<String, Types> types;
    private final HashMap<String, Timer> timersPool;
    private final HashMap<String, Long> times;

    public DataAccess() {
        this.db = new HashMap<>();
        this.types = new HashMap<>();
        this.timersPool = new HashMap<>();
        this.times = new HashMap<>();
    }

    public HashMap<String, Object> getDb() {
        return db;
    }

    public HashMap<String, Types> getTypes() {
        return types;
    }

    public HashMap<String, Timer> getTimersPool() {
        return timersPool;
    }

    public HashMap<String, Long> getTimes() {
        return times;
    }
}
