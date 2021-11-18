package service.TypeService;

import dao.DataAccess;
import model.Types;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class KeyService {
    public static int expire(DataAccess dao, String key, String time) {
        if (!dao.getDb().containsKey(key)) return 0;
        int expireTime = Integer.parseInt(time) * 1000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dao.getDb().remove(key);
            }
        }, expireTime);
        long startTime = System.currentTimeMillis() + expireTime;
        dao.getTimes().put(key, startTime);
        dao.getTimersPool().put(key, timer);
        return 1;
    }

    public String type(String key, HashMap<String, Types> types) {
        Types type = types.get(key);
        return type != null ? type.toString() : "none";
    }

    public int exists(String key, HashMap<String, Object> db) {
        return db.containsKey(key) ? 1 : 0;
    }

    public int del(String key, HashMap<String, Object> db) {
        return db.remove(key) != null ? 1 : 0;
    }

    public long total(String key, HashMap<String, Long> times) {
        return (times.get(key) - System.currentTimeMillis()) / 1000;
    }

    public int persist(String key, DataAccess dao) {
        if (!dao.getTimersPool().containsKey(key))
            return 0;
        dao.getTimersPool().get(key).cancel();
        dao.getTimes().remove(key);
        dao.getTimes().remove(key);
        return 1;
    }
}
