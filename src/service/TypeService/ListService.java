package service.TypeService;

import dao.DataAccess;
import model.Types;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ListService {
    public int push(DataAccess dao, String method, String key, String[] values) {
        HashMap<String, Object> db = dao.getDb();
        if (!db.containsKey(key)) {
            db.put(key, new LinkedList<String>());
            dao.getTypes().put(key, Types.LIST);
        }
        LinkedList<String> list = (LinkedList<String>) db.get(key);
        int i = 0;
        if (method.equalsIgnoreCase("LPUSH"))
            while (i < values.length)
                list.addFirst(values[i++]);
        else
            while (i < values.length)
                list.addLast(values[i++]);
        return list.size();
    }

    public String range(LinkedList<String> list, int startIndex, int stopIndex) {
        if (startIndex <= 0 && (stopIndex == -1 || stopIndex == list.size() - 1)) return list.toString();
        List<String> elems = new LinkedList<>();
        if (startIndex > list.size() - 1) return elems.toString();
        if (startIndex < 0) startIndex = 0;
        if (stopIndex == -1 || stopIndex > list.size())
            stopIndex = list.size();
        elems = list.subList(startIndex, stopIndex);
        return elems.toString();
    }

    public String pop(LinkedList<String> list, String method) {
        return method.equalsIgnoreCase("LPOP") ? list.pollFirst() : list.pollLast();
    }

    public String trim(HashMap<String, Object> db, String key, int startIndex, int stopIndex) {
        LinkedList<String> list = (LinkedList<String>) db.get(key);
        if (startIndex <= 0 && (stopIndex == -1 || stopIndex >= list.size()))
            throw new IndexOutOfBoundsException("Out of List Bounds");
        if (startIndex > list.size() - 1) {
            db.replace(key, new LinkedList<String>());
            return "Ok";
        }
        if (startIndex < 0) startIndex = 0;
        if (stopIndex == -1 || stopIndex > list.size() - 1)
            stopIndex = list.size() - 1;
        int i = 0;
        int j = list.size() - 1;
        while (i != startIndex || j != stopIndex) {
            if (i != startIndex) {
                list.removeFirst();
                i++;
            }
            if (j != stopIndex) {
                list.removeLast();
                j--;
            }
        }
        return "OK";
    }

    public String indexOf(LinkedList<String> myList, int index) {
        if (index >= myList.size()) return null;
        if (index < 0) index = myList.size() + index;
        return myList.get(index);
    }

    public int length(LinkedList<String> list) {
        return list.size();
    }

    public Object set(LinkedList<String> list, int index, String newValue) {
        if (index >= list.size()) throw new IndexOutOfBoundsException("Out of List Bounds");
        if (index < 0) index = list.size() + index;
        list.set(index, newValue);
        return "OK";
    }

}
