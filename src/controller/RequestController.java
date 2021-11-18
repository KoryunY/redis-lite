package controller;

import dao.DataAccess;
import model.SortedSet;
import service.TypeService.*;
import service.parser.RequestParser;

import java.util.*;

public class RequestController {
    private final KeyService keyService;
    private final StringService stringService;
    private final ListService listService;
    private final HashService hashService;
    private final SetService setService;
    private final SortedSetService sortedSetService;
    private final DataAccess dao;

    public RequestController() {
        this.dao = new DataAccess();
        this.keyService = new KeyService();
        this.stringService = new StringService();
        this.listService = new ListService();
        this.hashService = new HashService();
        this.setService = new SetService();
        this.sortedSetService = new SortedSetService();
    }

    public Object parseInput(String inputLine) {
        return RequestParser.parse(inputLine, this);
    }

    /*String*/

    public String set(String[] query) {
        if (query.length < 3) throw new IllegalArgumentException("wrong query length:" + query.length);
        return stringService.set(dao, query);
    }

    public String get(String[] query) {
        if (query.length != 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        return stringService.get(dao.getDb(), query[1]);
    }

    public String inOrDecrement(String[] query) {
        if (query.length != 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        return stringService.inOrDecrement(dao.getDb(), query[1], query[0]);
    }

    public String multiSet(String[] query) {
        if (query.length < 3) throw new IllegalArgumentException("wrong query length:" + query.length);
        if (query.length == 3) return set(query);
        return stringService.multiSet(dao, Arrays.copyOfRange(query, 1, query.length));
    }

    public String multiGet(String[] query) {
        if (query.length < 3) throw new IllegalArgumentException("wrong query length:" + query.length);
        if (query.length == 3) return get(query);
        return stringService.multiGet(dao.getDb(), Arrays.copyOfRange(query, 1, query.length));

    }

    public int append(String[] query) {
        if (query.length != 3) throw new IllegalArgumentException("wrong query length:" + query.length);
        return stringService.append(dao, query[1], query[2]);
    }

    public int strLen(String[] query) {
        if (query.length != 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        return stringService.length(dao.getDb(), query[1]);
    }
    /* LISTS */

    public int listPush(String[] query) {
        if (query.length < 3) throw new IllegalArgumentException("wrong query length:" + query.length);
        return listService.push(dao, query[0], query[1], Arrays.copyOfRange(query, 2, query.length));
    }

    public String listRange(String[] query) {
        if (query.length < 4) throw new IllegalArgumentException("wrong query length:" + query.length);
        LinkedList<String> list = (LinkedList<String>) dao.getDb().get(query[1]);

        return listService.range(list, Integer.parseInt(query[2]), Integer.parseInt(query[3]));
    }

    public String listPop(String[] query) {
        if (query.length != 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        LinkedList list = (LinkedList) dao.getDb().get(query[1]);
        return listService.pop(list, query[0]);
    }

    public String listTrim(String[] query) {
        if (query.length < 4) throw new IllegalArgumentException("wrong query length:" + query.length);
        return listService.trim(dao.getDb(), query[1], Integer.parseInt(query[2]), Integer.parseInt(query[3]));
    }

    public String listIndex(String[] query) {
        if (query.length != 3) throw new IllegalArgumentException("wrong query length:" + query.length);
        return listService.indexOf((LinkedList<String>) dao.getDb().get(query[1]), Integer.parseInt(query[2]));
    }

    public int listLength(String[] query) {
        if (query.length != 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        return listService.length((LinkedList<String>) dao.getDb().get(query[1]));
    }

    public Object listSet(String[] query) {
        if (query.length != 4) throw new IllegalArgumentException("wrong query length:" + query.length);
        return listService.set((LinkedList<String>) dao.getDb().get(query[1]), Integer.parseInt(query[2]), query[3]);
    }
    /*Hash*/

    public int hashSet(String[] query) {
        if (query.length < 4) throw new IllegalArgumentException("wrong query length:" + query.length);
        return hashService.set(dao, query[1], Arrays.copyOfRange(query, 2, query.length));
    }

    public Object hashGetOne(String[] query) {
        if (query.length < 3) throw new IllegalArgumentException("wrong query length:" + query.length);
        return hashService.get(dao.getDb(), query[1], query[2]);
    }

    public Object hashGetAll(String[] query) {
        if (query.length != 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        return hashService.getAll(dao.getDb(), query[1]);
    }

    public Object hashGetMany(String[] query) {
        if (query.length < 3) throw new IllegalArgumentException("wrong query length:" + query.length);
        return hashService.getMany(dao.getDb(), query[1], Arrays.copyOfRange(query, 2, query.length));
    }

    public int hashInOrDecreaseBy(String[] query) {
        if (query.length < 4) throw new IllegalArgumentException("wrong query length:" + query.length);
        return hashService.inOrDecreaseBy(dao.getDb(), query[1], query[2], query[0], Integer.parseInt(query[3]));

    }

    public int hashLength(String[] query) {
        if (query.length != 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        return hashService.length(dao.getDb(), query[1]);
    }

    public int hashDel(String[] query) {
        if (query.length < 3) throw new IllegalArgumentException("wrong query length:" + query.length);
        return hashService.del(dao.getDb(), query[1], Arrays.copyOfRange(query, 2, query.length));
    }

    /* SET */

    public int setADD(String[] query) {
        if (query.length < 3) throw new IllegalArgumentException("wrong query length:" + query.length);
        return setService.add(dao, query[1], Arrays.copyOfRange(query, 2, query.length));
    }

    public Object setGetAll(String[] query) {
        if (query.length != 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        return setService.getAll(dao.getDb(), query[1]);
    }

    public int setContains(String[] query) {
        if (query.length != 3) throw new IllegalArgumentException("wrong query length:" + query.length);
        return setService.contains(dao.getDb(), query[1], query[2]);
    }

    public int setCard(String[] query) {
        if (query.length != 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        return setService.cardinality(dao.getDb(), query[1]);
    }

    public Object setPop(String[] query) {
        if (query.length < 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        int count = 1;
        if (query.length > 2)
            count = Integer.parseInt(query[2]);
        return setService.pop(dao.getDb(), query[1], count);
    }

    public Object setRandMember(String[] query) {
        if (query.length < 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        int count = 1;
        if (query.length > 2)
            count = Integer.parseInt(query[2]);
        return setService.randMember(dao.getDb(), query[1], count);
    }

    public Object setInter(String[] query) {
        if (query.length < 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        return setService.intersection(dao.getDb(), Arrays.copyOfRange(query, 1, query.length));
    }

    public int setInterStore(String[] query) {
        if (query.length < 3) throw new IllegalArgumentException("wrong query length:" + query.length);
        return setService.interStore(dao, query[1], Arrays.copyOfRange(query, 2, query.length));
    }

    public Object setUnion(String[] query) {
        if (query.length < 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        return setService.union(dao.getDb(), Arrays.copyOfRange(query, 1, query.length));
    }

    public int setUnionStore(String[] query) {
        if (query.length < 3) throw new IllegalArgumentException("wrong query length:" + query.length);
        return setService.unionStore(dao, query[1], Arrays.copyOfRange(query, 2, query.length));
    }

    public int setRemove(String[] query) {
        if (query.length != 3) throw new IllegalArgumentException("wrong query length:" + query.length);
        return setService.remove(dao.getDb(), query[1], query[2]);
    }

    /*ZSet*/

    public int zsetAdd(String[] query) {
        if (query.length < 4) throw new IllegalArgumentException("wrong query length:" + query.length);
        if (query.length == 4)
            return sortedSetService.addItem(dao, query[1], query[2], Integer.parseInt(query[3]));
        return sortedSetService.addItems(dao, query[1], Arrays.copyOfRange(query, 2, query.length));

    }

    public Object zsetGetKeysInRange(String[] query) {
        if (query.length < 4) throw new IllegalArgumentException("wrong query length:" + query.length);
        boolean withScores = false;
        if (query.length == 5 && query[4].equalsIgnoreCase("withscores"))
            withScores = true;
        return sortedSetService.getKeysInRange((SortedSet) dao.getDb().get(query[1]),
                Integer.parseInt(query[2]), Integer.parseInt(query[3]), withScores);
    }

    public Object zsetGetKeysInRangeByScore(String[] query) {
        if (query.length < 4) throw new IllegalArgumentException("wrong query length:" + query.length);
        return sortedSetService.getKeysInRangeByScore((SortedSet) dao.getDb().get(query[1])
                , Integer.parseInt(query[2]), Integer.parseInt(query[3]));
    }

    public int zsetGetRank(String[] query) {
        if (query.length < 3) throw new IllegalArgumentException("wrong query length:" + query.length);
        return sortedSetService.getRank((SortedSet) dao.getDb().get(query[1]), query[2]);
    }

    public int zsetGetScore(String[] query) {
        if (query.length < 3) throw new IllegalArgumentException("wrong query length:" + query.length);
        return sortedSetService.getScore((SortedSet) dao.getDb().get(query[1]), query[2]);
    }

    public int zsetGetCard(String[] query) {
        if (query.length < 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        return sortedSetService.getCard((SortedSet) dao.getDb().get(query[1]));
    }

    public long zsetCountRange(String[] query) {
        if (query.length < 4) throw new IllegalArgumentException("wrong query length:" + query.length);
        return sortedSetService.countRange((SortedSet) dao.getDb().get(query[1])
                , Integer.parseInt(query[2]), Integer.parseInt(query[3]));
    }

    public int zsetRemoveRangeByScore(String[] query) {
        if (query.length < 4) throw new IllegalArgumentException("wrong query length:" + query.length);
        return sortedSetService.remRangeByScore((SortedSet) dao.getDb().get(query[1])
                , Integer.parseInt(query[2]), Integer.parseInt(query[3]));
    }

    public int zsetRemove(String[] query) {
        if (query.length < 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        return sortedSetService.remove((SortedSet) dao.getDb().get(query[1]), Arrays.copyOfRange(query, 1, query.length));
    }

    public Object zsetPopMin(String[] query) {
        if (query.length < 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        int count = 1;
        if (query.length > 2)
            count = Integer.parseInt(query[2]);
        return sortedSetService.popMin((SortedSet) dao.getDb().get(query[1]), count);
    }

    public Object zsetPopMax(String[] query) {
        if (query.length < 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        int count = 1;
        if (query.length > 2)
            count = Integer.parseInt(query[2]);
        return sortedSetService.popMax((SortedSet) dao.getDb().get(query[1]), count);
    }

    /* key */
    public int del(String[] query) {
        if (query.length < 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        return keyService.del(query[1], dao.getDb());
    }

    public int expire(String[] query) {
        if (query.length < 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        return KeyService.expire(dao, query[1], query[2]);
    }

    public long total(String[] query) {
        if (query.length < 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        return keyService.total(query[1], dao.getTimes());
    }

    public int persist(String[] query) {
        if (query.length < 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        return keyService.persist(query[1], dao);
    }

    public int exists(String[] query) {
        if (query.length < 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        return keyService.exists(query[1], dao.getDb());
    }


    public String type(String[] query) {
        if (query.length < 2) throw new IllegalArgumentException("wrong query length:" + query.length);
        return keyService.type(query[1], dao.getTypes());
    }
}
