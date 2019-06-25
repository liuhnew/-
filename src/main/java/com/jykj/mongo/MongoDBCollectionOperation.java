package com.jykj.mongo;

import com.jykj.util.GsonUtils;
import com.jykj.util.TimeUtil;
import com.mongodb.*;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class MongoDBCollectionOperation {

    private Logger logger = LoggerFactory.getLogger(MongoDBCollectionOperation.class);

    private DB db;
    private DBCollection dbCollection;

    public MongoDBCollectionOperation(MongoClient mongoClient, String dbName, String collection) {
        this.db = mongoClient.getDB(dbName);
        this.dbCollection = this.db.getCollection(collection);
    }

    public DBCollection getDbCollection() {
        return this.dbCollection;
    }

    public static Map<String, Object> obj2map(DBObject dbObject) {
        return dbObject.toMap();
    }

    public static List<Map<String, Object>> objs2mapList(List<DBObject> dbObjects) {
        List<Map<String, Object>> mapList = new LinkedList<Map<String, Object>>();
        for (DBObject dbObject : dbObjects) {
            mapList.add(dbObject.toMap());
        }
        return mapList;
    }

    ;

    private DBObject map2Obj(Map<String, Object> map) {
        return new BasicDBObject(map);
    }

    public ObjectId insert(Map<String, Object> map) {
        DBObject dbObject = map2Obj(map);
        this.dbCollection.insert(new DBObject[]{dbObject});
        return (ObjectId) dbObject.get("_id");
    }

    public String insertBySelfId(Map<String, Object> map) {
        DBObject dbObject = map2Obj(map);
        this.dbCollection.insert(new DBObject[]{dbObject});
        return (String) dbObject.get("_id");
    }

    /**
     * 批量新增
     *
     * @param addDatas 新增记录
     */
    public void insertBatch(List<Map<String, Object>> addDatas) {
        if ((addDatas == null) || (addDatas.isEmpty())) {
            return;
        }

        List listDB = new ArrayList();
        for (int i = 0; i < addDatas.size(); i++) {
            DBObject dbObject = map2Obj((Map) addDatas.get(i));
            listDB.add(dbObject);
        }
        this.dbCollection.insert(listDB);
    }

    /**
     * 删除一条记录
     *
     * @param removeData
     */
    public void delete(Map<String, Object> removeData) {
        DBObject obj = map2Obj(removeData);
        this.dbCollection.remove(obj);
    }

    /**
     * 删除所有记录【慎用】
     */
    public void deleteAll() {
        List rs = findAll();
        if ((rs != null) && (!rs.isEmpty()))
            for (int i = 0; i < rs.size(); i++)
                this.dbCollection.remove((DBObject) rs.get(i));
    }

    /**
     * 批量删除
     *
     * @param removeList 删除的记录集合
     */
    public void deleteBatch(List<Map<String, Object>> removeList) {
        if ((removeList == null) || (removeList.isEmpty())) {
            return;
        }
        for (int i = 0; i < removeList.size(); i++)
            this.dbCollection.remove(map2Obj((Map) removeList.get(i)));
    }

    /**
     * 条件查询返回总记录数
     *
     * @param queryMap 条件map
     * @return
     */
    public long getCount(Map<String, Object> queryMap) {
        return this.dbCollection.getCount(map2Obj(queryMap));
    }

    /**
     * 返回总记录数
     *
     * @return
     */
    public long count() {
        return this.dbCollection.count();
    }

    /**
     * 更新查找到的多条记录
     *
     * @param queryMap  条件map
     * @param setFields 更新的字段
     */
    public void updateMulti(Map<String, Object> queryMap, Map<String, Object> setFields) {
        DBObject obj1 = map2Obj(queryMap);
        DBObject obj2 = map2Obj(setFields);

        this.dbCollection.updateMulti(obj1, obj2);
    }

    /**
     * 条件查询，更新指定字段
     *
     * @param queryMap  条件map
     * @param setFields 更新的字段
     */
    public void update(Map<String, Object> queryMap, Map<String, Object> setFields) {
        DBObject obj1 = map2Obj(queryMap);
        DBObject obj2 = map2Obj(setFields);
        this.dbCollection.update(obj1, obj2);
    }

    /**
     * 条件查询，更新指定字段；不存在记录时是否新增一条记录；是否更新查找到的多条记录
     *
     * @param queryMap  条件map
     * @param setFields 更新的字段
     * @param upsert    是否新增
     * @param multi     是否更新多条
     */
    public void update(Map<String, Object> queryMap, Map<String, Object> setFields, boolean upsert, boolean multi) {
        DBObject obj1 = map2Obj(queryMap);
        DBObject obj2 = map2Obj(setFields);
        this.dbCollection.update(obj1, obj2, upsert, multi);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    public DBObject findById(String id) {
        return this.dbCollection.findOne(new ObjectId(id));
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    public Map<String,Object> findOne(String id) {
        DBObject dbObject = this.dbCollection.findOne(new ObjectId(id));
        return obj2map(dbObject);
    }

    /**
     * 通过_id查询
     *
     * @param id
     * @return
     */
    public Map<String, Object> findBySelfId(String id) {
        DBObject dbObject = this.dbCollection.findOne(id);
        if (dbObject != null) {
            return obj2map(dbObject);
        } else {
            return null;
        }
    }

    /**
     * 通过objectId查询，返回指定字段
     *
     * @param id
     * @param selectFields 返回的自定字段
     * @return
     */
    public DBObject findById(String id, Map<String, Object> selectFields) {
        return this.dbCollection.findOne(new ObjectId(id), map2Obj(selectFields));
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    public List<DBObject> findAll() {
        return this.dbCollection.find().toArray();
    }

    /**
     * 查询所有记录，返回指定字段
     *
     * @param selectFields 返回的指定字段
     * @return
     */
    public List<DBObject> findAll(Map<String, Object> selectFields) {
        return this.dbCollection.find(new BasicDBObject(), map2Obj(selectFields)).toArray();
    }

    /**
     * 通过条件查询一条数据
     *
     * @param queryMap 条件map
     * @return
     */
    public DBObject findOne(Map<String, Object> queryMap) {
        DBCollection coll = this.dbCollection;
        return coll.findOne(map2Obj(queryMap));
    }

    /**
     * 通过条件查询一条数据，返回指定字段
     *
     * @param queryMap     条件map
     * @param selectFields 指定字段
     * @return
     */
    public DBObject findOne(Map<String, Object> queryMap, Map<String, Object> selectFields) {
        DBCollection coll = this.dbCollection;
        return coll.findOne(map2Obj(queryMap), map2Obj(selectFields));
    }

    /**
     * 通过条件排序查找
     *
     * @param queryMap 条件map
     * @param orderBy  排序条件
     * @return
     */
    public Map<String, Object> findOneBySort(Map<String, Object> queryMap, DBObject orderBy) {
        List<DBObject> dbObjects = this.dbCollection.find(map2Obj(queryMap)).sort(orderBy).limit(1).toArray();
        Map map = new HashMap();
        try {
            map = dbObjects.get(0).toMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 排序，并指定返回特定字段
     *
     * @param selectFields 指定返回的字段
     * @param orderBy      排序条件
     * @return
     */
    public Map<String, Object> findSelectFieldsBySort(BasicDBObject selectFields, DBObject orderBy) {
        List dbCursor = this.dbCollection.find(new BasicDBObject(), selectFields).limit(1).sort(orderBy).toArray();
        return ((DBObject) dbCursor.get(0)).toMap();
    }

    /**
     * 通过条件查询结果
     *
     * @param query
     * @return
     * @throws Exception
     */
    public List<DBObject> find(Map<String, Object> query) {
        DBCursor c = this.dbCollection.find(map2Obj(query));
        if (c != null) {
            return c.toArray();
        }
        return null;
    }

    /**
     * 分页排序查找
     *
     * @param query    条件
     * @param orderBy  排序
     * @param startRow 开始行
     * @param pageSize 每页返回记录数
     * @return
     */
    public List<DBObject> findInPageAndSort(Map<String, Object> query, DBObject orderBy, int startRow, int pageSize) {
        DBCursor c = this.dbCollection.find(map2Obj(query)).skip(startRow).limit(pageSize).sort(orderBy);
        if (c != null) {
            return c.toArray();
        }
        return null;
    }

    /**
     * 分页查找
     *
     * @param query    条件
     * @param startRow 开始行
     * @param pageSize 每页返回记录数
     * @return
     */
    public List<DBObject> findInPage(Map<String, Object> query, int startRow, int pageSize) {
        DBCursor c = this.dbCollection.find(map2Obj(query)).skip(startRow).limit(pageSize);
        if (c != null) {
            return c.toArray();
        }
        return null;
    }

    /**
     * 通过条件返回指定字段
     *
     * @param query        条件
     * @param selectFields 指定字段
     * @return
     * @throws Exception
     */
    public List<DBObject> find(Map<String, Object> query, Map<String, Object> selectFields)
            throws Exception {
        DBCursor c = this.dbCollection.find(map2Obj(query), map2Obj(selectFields));
        if (c != null) {
            return c.toArray();
        }
        return null;
    }

    /**
     * 通过条件分页查找
     *
     * @param query    条件
     * @param pageInfo 分页参数
     * @return
     */
    public List<DBObject> findByPage(Map<String, Object> query, PageInfo pageInfo) {
        DBCursor cursor = null;
        if ((pageInfo.getSortParm() != null) && (!pageInfo.getSortParm().isEmpty()))
            cursor = this.dbCollection.find(map2Obj(query))
                    .sort(toSort(pageInfo.getSortParm()))
                    .skip((pageInfo.getPage().intValue() - 1) * pageInfo.getPageSize().intValue())
                    .limit(pageInfo.getPageSize().intValue());
        else {
            cursor = this.dbCollection.find(map2Obj(query))
                    .skip((pageInfo.getPage().intValue() - 1) * pageInfo.getPageSize().intValue())
                    .limit(pageInfo.getPageSize().intValue());
        }

        if (cursor != null) {
            return cursor.toArray();
        }
        return null;
    }

    /**
     * 通过条件分页查询出指定返回字段
     *
     * @param query        条件
     * @param selectFields 指定返回字段
     * @param pageInfo     分页参数
     * @return
     */
    public List<DBObject> findByPage(Map<String, Object> query, Map<String, Object> selectFields, PageInfo pageInfo) {
        DBCursor cursor = null;
        if ((pageInfo.getSortParm() != null) && (!pageInfo.getSortParm().isEmpty()))
            cursor = this.dbCollection.find(map2Obj(query), map2Obj(selectFields))
                    .sort(toSort(pageInfo.getSortParm()))
                    .skip((pageInfo.getPage().intValue() - 1) * pageInfo.getPageSize().intValue())
                    .limit(pageInfo.getPageSize().intValue());
        else {
            cursor = this.dbCollection.find(map2Obj(query), map2Obj(selectFields))
                    .skip((pageInfo.getPage().intValue() - 1) * pageInfo.getPageSize().intValue())
                    .limit(pageInfo.getPageSize().intValue());
        }

        if (cursor != null) {
            return cursor.toArray();
        }
        return null;
    }

    private BasicDBObject toSort(List<SortParam> sortParm) {
        BasicDBObject bo = new BasicDBObject();
        for (SortParam sp : sortParm) {
            bo.append(sp.getSortName(), Integer.valueOf(sp.getSortOrder().getOrder()));
        }

        return bo;
    }

    public long countByIdRange(String startId, String endId) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new BasicDBObject("$gt", startId).append("$lte", endId));
        return this.dbCollection.count(query);
    }

    public List<Map<String, Object>> findByIdRange(String startId, String endId, int pageSize) {
        Map<String, Object> query = new LinkedHashMap<>();
        query.put("_id", new BasicDBObject("$gt", startId).append("$lte", endId));
        List<SortParam> sortParm = new ArrayList<SortParam>();
        sortParm.add(new SortParam("_id", new Order(Order.ASC)));
        PageInfo pageInfo = new PageInfo((int) 1, pageSize, sortParm);
        List<DBObject> dbObjects = findByPageOptimization(query, pageInfo);
        return objs2mapList(dbObjects);
    }

    public long countBy_Id1Range(String startId, String endId) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id1", new BasicDBObject("$gt", startId).append("$lte", endId));
        return this.dbCollection.count(query);
    }

    public List<Map<String, Object>> findBy_Id1_Range(String startId, String endId, int pageSize) {
        Map<String, Object> query = new LinkedHashMap<>();
        query.put("_id1", new BasicDBObject("$gt", startId).append("$lte", endId));
        List<SortParam> sortParm = new ArrayList<SortParam>();
        sortParm.add(new SortParam("_id1", new Order(Order.ASC)));
        PageInfo pageInfo = new PageInfo((int) 1, pageSize, sortParm);
        List<DBObject> dbObjects = findByPageOptimization(query, pageInfo);
        return objs2mapList(dbObjects);
    }

    public List<DBObject> findByPageOptimization(Map<String, Object> map, PageInfo pageInfo) {
        DBCursor cursor = null;
        if ((pageInfo.getSortParm() != null) && (!pageInfo.getSortParm().isEmpty()))
            cursor = this.dbCollection.find(map2Obj(map))
                    .sort(toSort(pageInfo.getSortParm()))
                    .limit(pageInfo.getPageSize().intValue());
        else {
            cursor = this.dbCollection.find(map2Obj(map)).limit(
                    pageInfo.getPageSize().intValue());
        }

        if (cursor != null) {
            return cursor.toArray();
        }
        return null;
    }

    public List<DBObject> find(Map<String, Object> map, long maxTimeMilliSeconds) {
        DBCursor c = this.dbCollection.find(map2Obj(map)).maxTime(
                maxTimeMilliSeconds, TimeUnit.MILLISECONDS);
        if (c != null) {
            return c.toArray();
        }
        return null;
    }

    public BulkWriteOperation initializeOrderedBulkOperation() {
        return this.dbCollection.initializeOrderedBulkOperation();
    }

    public BulkWriteOperation initializeUnOrderedBulkOperation() {
        return this.dbCollection.initializeUnorderedBulkOperation();
    }

    public List<Map<String,Object>> queryMenuIdByRoleIds(List<String> roleIds, String appId) {
        BasicDBObject query = new BasicDBObject();
        query.put("app", new ObjectId(appId));

        BasicDBList queryList = new BasicDBList();
        for (String objectId: roleIds) {
            queryList.add(new BasicDBObject("_id", new ObjectId(objectId)));
        }
        query.put("$or", queryList);
        logger.info("++++++++查询语句++++++++" + query);
        return objs2mapList(this.dbCollection.find(query).toArray());
    }

    public List<Map<String,Object>> queryMenuIdByMenuIds(Set<ObjectId> menuIds) {
        BasicDBObject query = new BasicDBObject();
        BasicDBList queryList = new BasicDBList();
        Iterator<ObjectId> it = menuIds.iterator();
        while(it.hasNext()) {
            queryList.add(new BasicDBObject("_id", it.next()));
        }
        query.put("$or", queryList);
        logger.info("" + query);
        return objs2mapList(this.dbCollection.find(query).toArray());
    }
}
