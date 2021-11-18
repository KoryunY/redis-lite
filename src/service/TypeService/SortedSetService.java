package service.TypeService;

import dao.DataAccess;
import model.SortedSet;
import model.Types;

import java.util.*;

public class SortedSetService {
    public int addItem(DataAccess dao, String setKey, String key, int score) {
        SortedSet sortedSet;
        if (dao.getDb().containsKey(setKey)) {
            sortedSet = (SortedSet) dao.getDb().get(setKey);
            remove(sortedSet, key);
        }
        else {
            sortedSet = new SortedSet();
            dao.getDb().put(setKey, sortedSet);
        }
        if (sortedSet.getScoreToKey().containsKey(score)) {
            sortedSet.getScoreToKey().get(score).add(key);
        } else {
            TreeSet<String> keys = new TreeSet<>();
            keys.add(key);
            sortedSet.getScoreToKey().put(score, keys);
            dao.getTypes().put(setKey, Types.ZSET);
        }
        sortedSet.getKeyToScore().put(key, score);
        return 1;
    }

    public int addItems(DataAccess dao, String keySet, String[] keyValues) {
        int i = 0;
        while (i < keyValues.length)
            addItem(dao, keySet, keyValues[i++], Integer.parseInt(keyValues[i++]));
        return keyValues.length / 2;
    }

    public Object getKeysInRange(SortedSet sortedSet, int startIndex, int endIndex, boolean withScores) {
        List<Integer> list = new ArrayList<>(sortedSet.getScoreToKey().keySet());
        startIndex = list.get(startIndex);
        endIndex = list.get(endIndex);
        return withScores ? sortedSet.getScoreToKey().subMap(startIndex, endIndex + 1) : sortedSet.getScoreToKey().subMap(startIndex, endIndex + 1).values();
    }

    public Collection<TreeSet<String>> getKeysInRangeByScore(SortedSet sortedSet, int startScore, int endScore) {
        return sortedSet.getScoreToKey().subMap(startScore, endScore + 1).values();
    }

    public int getRank(SortedSet sortedSet, String key) {
        int score = sortedSet.getKeyToScore().get(key);
        List<Integer> list = new ArrayList<>(sortedSet.getScoreToKey().keySet());
        return list.indexOf(score);
    }

    public int getScore(SortedSet sortedSet, String key) {
        return sortedSet.getKeyToScore().get(key);
    }

    public int getCard(SortedSet sortedSet) {
        return sortedSet.getKeyToScore().size();
    }

    public long countRange(SortedSet sortedSet, int startScore, int endScore) {
        return sortedSet.getKeyToScore().values().stream().filter(score -> score >= startScore && score <= endScore).count();
    }


    public int remRangeByScore(SortedSet sortedSet, int startScore, int endScore) {
        Set<String> keys;
        int count = 0;
        Set<Integer> scores = new TreeSet<>(sortedSet.getScoreToKey().subMap(startScore, endScore + 1).keySet());
        for (Integer score : scores) {
            keys = sortedSet.getScoreToKey().get(score);
            if (keys != null)
                for (String key : keys) {
                    sortedSet.getKeyToScore().remove(key);
                    count++;
                }
            sortedSet.getScoreToKey().remove(score);
        }
        return count;
    }

    public int remove(SortedSet sortedSet, String... keys) {
        int count = 0;
        for (String key : keys) {
            if (sortedSet.getKeyToScore().containsKey(key)) {
                int score = sortedSet.getKeyToScore().get(key);
                sortedSet.getKeyToScore().remove(key);
                if (sortedSet.getScoreToKey().get(score).size() == 1)
                    sortedSet.getScoreToKey().remove(score);
                else
                    sortedSet.getScoreToKey().get(score).remove(key);
                count++;
            }
        }
        return count;
    }

    public List<String> popMin(SortedSet sortedSet, int count) {
        List<String> remElems = new ArrayList<>();
        while (count != 0) {
            int minScore = sortedSet.getScoreToKey().firstKey();
            String key = sortedSet.getScoreToKey().get(minScore).first();
            if (remove(sortedSet, key) == 1) {
                remElems.add(key);
                remElems.add(String.valueOf(minScore));
            }
            count--;
        }
        return remElems;
    }

    public List<String> popMax(SortedSet sortedSet, int count) {
        List<String> remElems = new ArrayList<>();
        while (count != 0) {
            int maxScore = sortedSet.getScoreToKey().lastKey();
            String key = sortedSet.getScoreToKey().get(maxScore).last();
            if (remove(sortedSet, key) == 1) {
                remElems.add(key);
                remElems.add(String.valueOf(maxScore));
            }
            count--;
        }
        return remElems;
    }
}
