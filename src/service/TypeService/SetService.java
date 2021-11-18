package service.TypeService;

import dao.DataAccess;
import model.Types;

import java.util.*;

public class SetService {
    public int add(DataAccess dao, String key, String[] items) {
        HashMap<String, Object> db = dao.getDb();
        if (!db.containsKey(key)) {
            db.put(key, new HashSet<String>());
            dao.getTypes().put(key, Types.SET);
        }
        int i = 0;
        while (i < items.length) {
            ((HashSet) db.get(key)).add(items[i++]);
        }
        return ((HashSet) db.get(key)).size();
    }

    public Object getAll(HashMap<String, Object> db, String key) {
        return (HashSet) db.get(key);
    }

    public int contains(HashMap<String, Object> db, String key, String value) {
        return ((HashSet) db.get(key)).contains(value) ? 1 : 0;
    }

    public int cardinality(HashMap<String, Object> db, String key) {
        if (!db.containsKey(key)) return 0;
        return ((HashSet) db.get(key)).size();
    }

    public Object randMember(HashMap<String, Object> db, String key, int count) {
        Iterator it = ((HashSet) db.get(key)).iterator();
        int j = 0;
        List<String> elems = new ArrayList<>();
        while (it.hasNext()) {
            if (j >= count) {
                break;
            }
            elems.add((String) it.next());
            j++;
        }
        return elems;
    }

    public Object intersection(HashMap<String, Object> db, String[] keys) {
        int i = 0;
        HashSet<String> hs;
        HashSet<String> interSet = (HashSet) db.get(keys[i++]);
        while (i < keys.length) {
            hs = (HashSet) db.get(keys[i++]);
            if (hs != null)
                interSet.retainAll(hs);
        }
        return interSet;
    }

    public int interStore(DataAccess dao, String key, String[] keys) {
        dao.getTypes().put(key, Types.SET);
        dao.getDb().put(key, intersection(dao.getDb(), keys));
        return 1;
    }

    public Object union(HashMap<String, Object> db, String[] keys) {
        HashSet<String> unionSet = new HashSet<>();
        int i = 0;
        HashSet<String> hs;
        while (i < keys.length) {
            hs = (HashSet) db.get(keys[i++]);
            if (hs != null)
                unionSet.addAll(hs);
        }
        return unionSet;
    }

    public int unionStore(DataAccess dao, String key, String[] keys) {
        dao.getTypes().put(key, Types.SET);
        dao.getDb().put(key, union(dao.getDb(), keys));
        return 1;
    }

    public int remove(HashMap<String, Object> db, String setKey, String key) {
        return ((HashSet) db.get(setKey)).remove(key) ? 1 : 0;
    }

    public Object pop(HashMap<String, Object> db, String key, int count) {
        Iterator it = ((HashSet) db.get(key)).iterator();
        int j = 0;
        List<String> elems = new ArrayList<>();
        while (it.hasNext()) {
            if (j == count) {
                break;
            }
            elems.add((String) it.next());
            it.remove();
            j++;
        }
        return elems;
    }

}
