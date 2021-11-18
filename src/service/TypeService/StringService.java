package service.TypeService;

import dao.DataAccess;
import model.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StringService {
    public String multiSet(DataAccess dao, String[] keyValues) {
        int i = 0;
        while (i < keyValues.length) {
            dao.getTypes().put(keyValues[i], Types.STRING);
            dao.getDb().put(keyValues[i++], keyValues[i++]);
        }
        return "OK";
    }

    public String multiGet(HashMap<String, Object> db, String[] keys) {
        int i = 0;
        List<String> elems = new ArrayList<>();
        while (i < keys.length) {
            elems.add((String) db.get(keys[i++]));
        }
        return elems.toString();
    }

    public int append(DataAccess dao, String key, String text) {
        HashMap<String, Object> db = dao.getDb();
        if (!db.containsKey(key)) {
            db.put(key, text);
            dao.getTypes().put(key, Types.STRING);
            return text.length();
        }
        String append = db.get(key).toString() + text;
        db.put(key, append);
        return append.length();
    }

    public String set(DataAccess dao, String[] query) {
        dao.getDb().put(query[1], query[2]);
        dao.getTypes().put(query[1], Types.STRING);
        if (query.length > 4)
            if (query[3].equalsIgnoreCase("EX"))
                KeyService.expire(dao, query[1], query[4]);
        return "OK";
    }

    public String get(HashMap<String, Object> db, String key) {
        return db.get(key).toString();
    }

    public String inOrDecrement(HashMap<String, Object> db, String key, String method) {
        int num = Integer.parseInt(db.get(key).toString());
        if (method.equalsIgnoreCase("incr")) {
            num++;
        } else {
            num--;
        }
        db.replace(key, num);
        return "(integer)" + num;
    }

    public int length(HashMap<String, Object> db, String key) {
        return db.get(key).toString().length();
    }
}
