package service.TypeService;

import dao.DataAccess;
import model.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashService {
    public int set(DataAccess dao, String key, String[] values) {
        HashMap<String, Object> db = dao.getDb();
        if (!db.containsKey(key)) {
            db.put(key, new HashMap<String, String>());
            dao.getTypes().put(key, Types.HASH);
        }
        int i = 0;
        while (i < values.length) {
            ((HashMap) db.get(key)).put(values[i++], values[i++]);
        }
        return ((HashMap) db.get(key)).size();
    }


    public Object get(HashMap<String, Object> db, String hashKey, String fieldKey) {
        return ((HashMap) db.get(hashKey)).get(fieldKey);
    }

    public Object getAll(HashMap<String, Object> db, String key) {
        return ((HashMap) db.get(key)).values();
    }

    public Object getMany(HashMap<String, Object> db, String key, String[] fields) {
        HashMap<String, String> hMap = (HashMap) db.get(key);
        List<String> required = new ArrayList<>();
        int i = 0;
        while (i < fields.length) {
            required.add(hMap.get(fields[i++]));
        }
        return required;
    }

    public int inOrDecreaseBy(HashMap<String, Object> db, String hashKey, String key, String method, int size) {
        HashMap<String, String> hMap = (HashMap) db.get(hashKey);
        int value = Integer.parseInt(hMap.get(key));
        if (method.equalsIgnoreCase("HINCRBY")) {
            value += size;
        } else value -= size;
        hMap.replace(key, String.valueOf(value));
        return value;
    }

    public int length(HashMap<String, Object> db, String key) {
        return ((HashMap) db.get(key)).size();
    }

    public int del(HashMap<String, Object> db, String hashKey, String[] keys) {
        HashMap<String, String> hMap = (HashMap) db.get(hashKey);
        int i = 0;
        while (i < keys.length) {
            hMap.remove(keys[i++]);
        }
        return hMap.size();
    }
}
