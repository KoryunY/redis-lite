package service.parser;

import controller.RequestController;

public class RequestParser {
    public static Object parse(String inputLine, RequestController controller) {
        String[] query = inputLine.split(" ");
        return switch (query[0].toUpperCase()) {
            //Strings
            case "SET" -> controller.set(query);
            case "GET" -> controller.get(query);
            case "INCR", "DECR" -> controller.inOrDecrement(query);
            case "MSET" -> controller.multiSet(query);
            case "MGET" -> controller.multiGet(query);
            case "APPEND" -> controller.append(query);
            case "STRLEN" -> controller.strLen(query);
            //List
            case "LPUSH", "RPUSH" -> controller.listPush(query);
            case "LRANGE" -> controller.listRange(query);
            case "LPOP", "RPOP" -> controller.listPop(query);
            case "LTRIM" -> controller.listTrim(query);
            case "LINDEX" -> controller.listIndex(query);
            case "LLEN" -> controller.listLength(query);
            case "LSET" -> controller.listSet(query);
            //Hashes
            case "HSET" -> controller.hashSet(query);
            case "HGET" -> controller.hashGetOne(query);
            case "HMGET" -> controller.hashGetMany(query);
            case "HGETALL" -> controller.hashGetAll(query);
            case "HINCRBY", "HDECRBY" -> controller.hashInOrDecreaseBy(query);
            case "HLEN" -> controller.hashLength(query);
            case "HDEL" -> controller.hashDel(query);
            //Set
            case "SADD" -> controller.setADD(query);
            case "SMEMBERS" -> controller.setGetAll(query);
            case "SISMEMBER" -> controller.setContains(query);
            case "SCARD" -> controller.setCard(query);
            case "SPOP" -> controller.setPop(query);
            case "SRANDMEMBER" -> controller.setRandMember(query);
            case "SINTER" -> controller.setInter(query);
            case "SINTERSTORE" -> controller.setInterStore(query);
            case "SUNION" -> controller.setUnion(query);
            case "SUNIONSTORE" -> controller.setUnionStore(query);
            case "SREM" -> controller.setRemove(query);
            //ZSet
            case "ZADD" -> controller.zsetAdd(query);
            case "ZRANGE" -> controller.zsetGetKeysInRange(query);
            case "ZRANGEBYSCORE" -> controller.zsetGetKeysInRangeByScore(query);
            case "ZREMRANGEBYSCORE" -> controller.zsetRemoveRangeByScore(query);
            case "ZCARD" -> controller.zsetGetCard(query);
            case "ZRANK" -> controller.zsetGetRank(query);
            case "ZSCORE" -> controller.zsetGetScore(query);
            case "ZCOUNT" -> controller.zsetCountRange(query);
            case "ZPOPMAX" -> controller.zsetPopMax(query);
            case "ZPOPMIN" -> controller.zsetPopMin(query);
            case "ZREM" -> controller.zsetRemove(query);
            //Keys
            case "EXISTS" -> controller.exists(query);
            case "DEL" -> controller.del(query);
            case "EXPIRE" -> controller.expire(query);
            case "TTL" -> controller.total(query);
            case "PERSIST" -> controller.persist(query);
            case "TYPE" -> controller.type(query);
            default -> "invalid command";
        };
    }
}
