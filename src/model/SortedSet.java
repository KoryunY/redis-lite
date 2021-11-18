package model;

import java.util.TreeMap;
import java.util.TreeSet;

public class SortedSet {
    private final TreeMap<String, Integer> keyToScore;
    private final TreeMap<Integer, TreeSet<String>> scoreToKey;

    public SortedSet() {
        keyToScore = new TreeMap<>();
        scoreToKey = new TreeMap<>();
    }

    public TreeMap<String, Integer> getKeyToScore() {
        return keyToScore;
    }

    public TreeMap<Integer, TreeSet<String>> getScoreToKey() {
        return scoreToKey;
    }
}
