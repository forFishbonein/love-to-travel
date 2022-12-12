//package com.lovetotravel.travel.service;
//
//
//import com.lovetotravel.travel.entity.page.MongoPage;
//import com.lovetotravel.travel.entity.page.PageVo;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Field;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.stereotype.Service;
//
//import java.io.Serializable;
//import java.util.List;
//import java.util.regex.Pattern;
//
//@Service
//public class IMongoServiceImpl<T> implements IMongoService<T> {
//    private final MongoTemplate mongoTemplate;
//
//    public IMongoServiceImpl(MongoTemplate mongoTemplate) {
//        this.mongoTemplate = mongoTemplate;
//    }
//
//    @Override
//    public boolean save(T entity) {
//        try{
//            mongoTemplate.save(entity);
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean save(T entity, String collectionName) {
//        try{
//            mongoTemplate.save(entity,collectionName);
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean saveList(List<T> list) {
//        try{
//            mongoTemplate.insertAll(list);
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean saveList(List<T> list, String collectionName) {
//        try{
//            mongoTemplate.insert(list,collectionName);
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean removeById(T entity) {
//        try{
//            mongoTemplate.remove(entity);
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean removeById(T entity, String collectionName) {
//        try{
//            mongoTemplate.remove(entity,collectionName);
//
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean updateById(String[] keys,Object[] values,Serializable id,Class<?> clazz) {
//        Criteria criteria=Criteria.where("_id").is(id);
//        Query query=new Query(criteria);
//        Update update=new Update();
//        for (int i = 0; i < keys.length; i++) {
//            update.set(keys[i],values[i]);
//        }
//        try{
//            mongoTemplate.updateFirst(query,update,clazz);
//
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean updateById(String[] keys,Object[] values,Serializable id,String collectionName) {
//        Criteria criteria=Criteria.where("_id").is(id);
//        Query query=new Query(criteria);
//        Update update=new Update();
//        for (int i = 0; i < keys.length; i++) {
//            update.set(keys[i],values[i]);
//        }
//        try{
//            mongoTemplate.updateFirst(query,update,collectionName);
//
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean updateById(String[] keys,Object[] values,Serializable id,Class<?> clazz,String collectionName) {
//        Criteria criteria=Criteria.where("_id").is(id);
//        Query query=new Query(criteria);
//        Update update=new Update();
//        for (int i = 0; i < keys.length; i++) {
//            update.set(keys[i],values[i]);
//        }
//        try{
//            mongoTemplate.updateFirst(query,update,clazz,collectionName);
//
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public T findById(Serializable id,Class<?> clazz) {
//        T result;
//        try {
//            Criteria criteria = Criteria.where("_id").is(id);
//            Query query = Query.query(criteria);
//            result = (T) mongoTemplate.findOne(query, clazz);
//
//            return result;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public T findById(Serializable id, Class<?> clazz,String collectionName) {
//        T result;
//        try {
//            Criteria criteria = Criteria.where("_id").is(id);
//            Query query = Query.query(criteria);
//            result = (T) mongoTemplate.findOne(query, clazz,collectionName);
//
//            return result;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public T findById(Serializable id,String[] fields,Class<?> clazz) {
//        T result;
//        try {
//            Criteria criteria = Criteria.where("_id").is(id);
//            Query query = Query.query(criteria);
//            Field field=query.fields();
//            field.include(fields);
//            result = (T) mongoTemplate.findOne(query, clazz);
//
//            return result;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public T findById(Serializable id,String[] fields,Class<?> clazz,String collectionName) {
//        T result;
//        try {
//            Criteria criteria = Criteria.where("_id").is(id);
//            Query query = Query.query(criteria);
//            Field field=query.fields();
//            field.include(fields);
//            result = (T) mongoTemplate.findOne(query, clazz,collectionName);
//
//            return result;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public List<T> findByMap(String[] keys,Object[] values,Class<?> clazz) {
//        Criteria criteria = null;
//        List<T> list;
//        try {
//            for (int i = 0; i < keys.length; i++) {
//                if (i == 0) {
//                    criteria = Criteria.where(keys[i]).is(values[i]);
//                } else {
//                    criteria.and(keys[i]).is(values[i]);
//                }
//            }
//            Query query = Query.query(criteria);
//            list= (List<T>) mongoTemplate.find(query,clazz);
//
//            return list;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public List<T> findByMap(String[] keys,Object[] values,Class<?> clazz,String collectionName) {
//        Criteria criteria = null;
//        List<T> list;
//        try {
//            for (int i = 0; i < keys.length; i++) {
//                if (i == 0) {
//                    criteria = Criteria.where(keys[i]).is(values[i]);
//                } else {
//                    criteria.and(keys[i]).is(values[i]);
//                }
//            }
//            Query query = Query.query(criteria);
//            list= (List<T>) mongoTemplate.find(query,clazz,collectionName);
//
//            return list;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public List<T> findByMap(String[] keys,Object[] values,String[] fields,Class<?> clazz) {
//        Criteria criteria = null;
//        List<T> list;
//        try {
//            for (int i = 0; i < keys.length; i++) {
//                if (i == 0) {
//                    criteria = Criteria.where(keys[i]).is(values[i]);
//                } else {
//                    criteria.and(keys[i]).is(values[i]);
//                }
//            }
//            Query query = Query.query(criteria);
//            Field field=query.fields();
//            field.include(fields);
//            list= (List<T>) mongoTemplate.find(query,clazz);
//
//            return list;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public List<T> findByMap(String[] keys,Object[] values,String[] fields,Class<?> clazz,String collectionName) {
//        Criteria criteria = null;
//        List<T> list;
//        try {
//            for (int i = 0; i < keys.length; i++) {
//                if (i == 0) {
//                    criteria = Criteria.where(keys[i]).is(values[i]);
//                } else {
//                    criteria.and(keys[i]).is(values[i]);
//                }
//            }
//            Query query = Query.query(criteria);
//            Field field=query.fields();
//            field.include(fields);
//            list= (List<T>) mongoTemplate.find(query,clazz,collectionName);
//
//            return list;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public List<T> findAll(Class<?> clazz) {
//        List<T> list;
//        try{
//            list= (List<T>) mongoTemplate.findAll(clazz);
//
//            return list;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public List<T> findAll(Class<?> clazz,String collectionName) {
//        List<T> list;
//        try{
//            list= (List<T>) mongoTemplate.find(new Query(),clazz,collectionName);
//
//            return list;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public List<T> findAll(String[] fields,Class<?> clazz) {
//        List<T> list;
//        try{
//            Query query = new Query(new Criteria());
//            Field field=query.fields();
//            field.include(fields);
//            list= (List<T>) mongoTemplate.find(query,clazz);
//
//            return list;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public List<T> findAll(String[] fields,Class<?> clazz,String collectionName) {
//        List<T> list;
//        try{
//            Query query = new Query(new Criteria());
//            Field field=query.fields();
//            field.include(fields);
//            list= (List<T>) mongoTemplate.find(query,clazz,collectionName);
//
//            return list;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public List<T> page(PageVo pageVo, Class<?> clazz) {
//        Integer pageSize= pageVo.getPageSize();
//        Integer pageNum= pageVo.getPageNum();
//        List<T> list;
//        try{
//            Query query=new Query(new Criteria());
//            //默认值为5，
//            pageSize = pageSize < 0 ? 5 : pageSize;
//            query.limit(pageSize);
//            query.skip((pageNum - 1) * pageSize);
//            list= (List<T>) mongoTemplate.find(query,clazz);
//
//            return list;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public List<T> page(PageVo pageVo, Class<?> clazz, String collectionName) {
//        Integer pageSize= pageVo.getPageSize();
//        Integer pageNum= pageVo.getPageNum();
//        List<T> list;
//        try{
//            Query query=new Query(new Criteria());
//            //默认值为5，
//            pageSize = pageSize < 0 ? 5 : pageSize;
//            query.limit(pageSize);
//            query.skip((pageNum - 1) * pageSize);
//            list= (List<T>) mongoTemplate.find(query,clazz,collectionName);
//
//            return list;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public List<T> page(PageVo pageVo, String[] fields, Class<?> clazz) {
//        Integer pageSize= pageVo.getPageSize();
//        Integer pageNum= pageVo.getPageNum();
//        List<T> list;
//        try{
//            Query query=new Query(new Criteria());
//            //默认值为5，
//            pageSize = pageSize < 0 ? 5 : pageSize;
//            query.limit(pageSize);
//            query.skip((pageNum - 1) * pageSize);
//            Field field=query.fields();
//            field.include(fields);
//            list= (List<T>) mongoTemplate.find(query,clazz);
//
//            return list;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public List<T> page(PageVo pageVo, String[] fields, Class<?> clazz, String collectionName) {
//        Integer pageSize= pageVo.getPageSize();
//        Integer pageNum= pageVo.getPageNum();
//        List<T> list;
//        try{
//            Query query=new Query(new Criteria());
//            //默认值为5，
//            pageSize = pageSize < 0 ? 5 : pageSize;
//            query.limit(pageSize);
//            query.skip((pageNum - 1) * pageSize);
//            Field field=query.fields();
//            field.include(fields);
//            list= (List<T>) mongoTemplate.find(query,clazz,collectionName);
//
//            return list;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public MongoPage page(MongoPage mongoPage, Class<?> clazz) {
//        Integer pageSize=mongoPage.getPageSize();
//        Integer pageNum=mongoPage.getPageNum();
//        long total;
//        List<T> list;
//        try{
//            Query query=new Query(new Criteria());
//            //返回总记录数
//            total=mongoTemplate.count(query,clazz);
//            //默认值为5，
//            pageSize = pageSize < 0 ? 5 : pageSize;
//            query.limit(pageSize);
//            query.skip((pageNum - 1) * pageSize);
//            list= (List<T>) mongoTemplate.find(query,clazz);
//            mongoPage.setMongoPage(getTotalSum(total,pageSize),list);
//
//            return mongoPage;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//    @Override
//    public MongoPage page(MongoPage mongoPage, Class<?> clazz,String collectionName) {
//        Integer pageSize=mongoPage.getPageSize();
//        Integer pageNum=mongoPage.getPageNum();
//        long total;
//        List<T> list;
//        try{
//            Query query=new Query(new Criteria());
//            //返回总记录数
//            total=mongoTemplate.count(query,clazz);
//            //默认值为5，
//            pageSize = pageSize < 0 ? 5 : pageSize;
//            query.limit(pageSize);
//            query.skip((pageNum - 1) * pageSize);
//            list= (List<T>) mongoTemplate.find(query,clazz,collectionName);
//            mongoPage.setMongoPage(getTotalSum(total,pageSize),list);
//
//            return mongoPage;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//    @Override
//    public MongoPage page(MongoPage mongoPage,String[] fields, Class<?> clazz) {
//        Integer pageSize=mongoPage.getPageSize();
//        Integer pageNum=mongoPage.getPageNum();
//        long total;
//        List<T> list;
//        try{
//            Query query=new Query(new Criteria());
//            //返回总记录数
//            total=mongoTemplate.count(query,clazz);
//            //默认值为5，
//            pageSize = pageSize < 0 ? 5 : pageSize;
//            query.limit(pageSize);
//            query.skip((pageNum - 1) * pageSize);
//            Field field=query.fields();
//            field.include(fields);
//            list= (List<T>) mongoTemplate.find(query,clazz);
//            mongoPage.setMongoPage(getTotalSum(total,pageSize),list);
//
//            return mongoPage;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//    @Override
//    public MongoPage page(MongoPage mongoPage,String[] fields, Class<?> clazz,String collectionName) {
//        Integer pageSize=mongoPage.getPageSize();
//        Integer pageNum=mongoPage.getPageNum();
//        long total;
//        List<T> list;
//        try{
//            Query query=new Query(new Criteria());
//            //返回总记录数
//            total=mongoTemplate.count(query,clazz);
//            //默认值为5，
//            pageSize = pageSize < 0 ? 5 : pageSize;
//            query.limit(pageSize);
//            query.skip((pageNum - 1) * pageSize);
//            Field field=query.fields();
//            field.include(fields);
//            list= (List<T>) mongoTemplate.find(query,clazz,collectionName);
//            mongoPage.setMongoPage(getTotalSum(total,pageSize),list);
//
//            return mongoPage;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//    @Override
//    public List<T> fuzzyPage(PageVo pageVo, Class<?> clazz, String inputStr, String[] keys){
//        Integer pageSize= pageVo.getPageSize();
//        Integer pageNum= pageVo.getPageNum();
//        List<T> list;
//        try{
//            if(judgeLength(keys.length)){
//                return null;
//            }
//            Query query=new Query(getCriteria(inputStr,keys));
//            //默认值为5，
//            pageSize = pageSize < 0 ? 5 : pageSize;
//            query.limit(pageSize);
//            query.skip((pageNum - 1) * pageSize);
//            list= (List<T>) mongoTemplate.find(query,clazz);
//
//            return list;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//    @Override
//    public List<T> fuzzyPage(PageVo pageVo, Class<?> clazz, String collectionName, String inputStr, String[] keys){
//        Integer pageSize= pageVo.getPageSize();
//        Integer pageNum= pageVo.getPageNum();
//        List<T> list;
//        try{
//            if(judgeLength(keys.length)){
//                return null;
//            }
//            Query query=new Query(getCriteria(inputStr,keys));
//            //默认值为5，
//            pageSize = pageSize < 0 ? 5 : pageSize;
//            query.limit(pageSize);
//            query.skip((pageNum - 1) * pageSize);
//            list= (List<T>) mongoTemplate.find(query,clazz,collectionName);
//
//            return list;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//    @Override
//    public List<T> fuzzyPage(PageVo pageVo, Class<?> clazz, String inputStr, String[] keys, String[] fields){
//        Integer pageSize= pageVo.getPageSize();
//        Integer pageNum= pageVo.getPageNum();
//        List<T> list;
//        try{
//            if(judgeLength(keys.length)){
//                return null;
//            }
//            Query query=new Query(getCriteria(inputStr,keys));
//            //默认值为5，
//            pageSize = pageSize < 0 ? 5 : pageSize;
//            query.limit(pageSize);
//            query.skip((pageNum - 1) * pageSize);
//            Field field=query.fields();
//            field.include(fields);
//            list= (List<T>) mongoTemplate.find(query,clazz);
//
//            return list;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//    @Override
//    public List<T> fuzzyPage(PageVo pageVo, Class<?> clazz, String collectionName, String inputStr, String[] keys, String[] fields){
//        Integer pageSize= pageVo.getPageSize();
//        Integer pageNum= pageVo.getPageNum();
//        List<T> list;
//        try{
//            if(judgeLength(keys.length)){
//                return null;
//            }
//            Query query=new Query(getCriteria(inputStr,keys));
//            //默认值为5，
//            pageSize = pageSize < 0 ? 5 : pageSize;
//            query.limit(pageSize);
//            query.skip((pageNum - 1) * pageSize);
//            Field field=query.fields();
//            field.include(fields);
//            list= (List<T>) mongoTemplate.find(query,clazz,collectionName);
//
//            return list;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
////    @Override
////    public MongoPage fuzzyPage(FuzzyPage fuzzyPage, Class<?> clazz,String[] keys) {
////        String inputStr=fuzzyPage.getInputStr();
////        Integer pageSize=fuzzyPage.getPageSize();
////        Integer pageNum=fuzzyPage.getPageNum();
////        long total;
////        List<T> list;
////        MongoPage mongoPage=new MongoPage(pageNum,pageSize);
////        try{
////            if(judgeLength(keys.length)){
////                return null;
////            }
////            Query query=new Query(getCriteria(inputStr,keys));
////            //返回总记录数
////            total=mongoTemplate.count(query,clazz);
////            //默认值为5，
////            pageSize = pageSize < 0 ? 5 : pageSize;
////            query.limit(pageSize);
////            query.skip((pageNum - 1) * pageSize);
////            list= (List<T>) mongoTemplate.find(query,clazz);
////            mongoPage.setMongoPage(total,getTotalSum(total,pageSize),list);
////
////            return mongoPage;
////        }catch (Exception e){
////            e.printStackTrace();
////            return null;
////        }
////    }
////
////    @Override
////    public MongoPage fuzzyPage(FuzzyPage fuzzyPage,Class<?> clazz, String collectionName,String[] keys) {
////        String inputStr=fuzzyPage.getInputStr();
////        Integer pageSize=fuzzyPage.getPageSize();
////        Integer pageNum=fuzzyPage.getPageNum();
////        long total;
////        List<T> list;
////        MongoPage mongoPage=new MongoPage(pageNum,pageSize);
////        try{
////            if(judgeLength(keys.length)){
////                return null;
////            }
////            Query query=new Query(getCriteria(inputStr,keys));
////            //返回总记录数
////            total=mongoTemplate.count(query,clazz);
////            //默认值为5，
////            pageSize = pageSize < 0 ? 5 : pageSize;
////            query.limit(pageSize);
////            query.skip((pageNum - 1) * pageSize);
////            list= (List<T>) mongoTemplate.find(query,clazz,collectionName);
////            mongoPage.setMongoPage(total,getTotalSum(total,pageSize),list);
////
////            return mongoPage;
////        }catch (Exception e){
////            e.printStackTrace();
////            return null;
////        }
////    }
////
////    @Override
////    public MongoPage fuzzyPage(FuzzyPage fuzzyPage, Class<?> clazz, String[] keys, String[] fields) {
////        String inputStr=fuzzyPage.getInputStr();
////        Integer pageSize=fuzzyPage.getPageSize();
////        Integer pageNum=fuzzyPage.getPageNum();
////        long total;
////        List<T> list;
////        MongoPage mongoPage=new MongoPage(pageNum,pageSize);
////        try{
////            if(judgeLength(keys.length)){
////                return null;
////            }
////            Query query=new Query(getCriteria(inputStr,keys));
////            //返回总记录数
////            total=mongoTemplate.count(query,clazz);
////            //默认值为5，
////            pageSize = pageSize < 0 ? 5 : pageSize;
////            query.limit(pageSize);
////            query.skip((pageNum - 1) * pageSize);
////            Field field=query.fields();
////            field.include(fields);
////            list= (List<T>) mongoTemplate.find(query,clazz);
////            mongoPage.setMongoPage(total,getTotalSum(total,pageSize),list);
////
////            return mongoPage;
////        }catch (Exception e){
////            e.printStackTrace();
////            return null;
////        }
////    }
////
////    @Override
////    public MongoPage fuzzyPage(FuzzyPage fuzzyPage, Class<?> clazz, String collectionName,String[] keys, String[] fields) {
////        String inputStr=fuzzyPage.getInputStr();
////        Integer pageSize=fuzzyPage.getPageSize();
////        Integer pageNum=fuzzyPage.getPageNum();
////        long total;
////        List<T> list;
////        MongoPage mongoPage=new MongoPage(pageNum,pageSize);
////        try{
////            if(judgeLength(keys.length)){
////                return null;
////            }
////            Query query=new Query(getCriteria(inputStr,keys));
////            //返回总记录数
////            total=mongoTemplate.count(query,clazz);
////            //默认值为5，
////            pageSize = pageSize < 0 ? 5 : pageSize;
////            query.limit(pageSize);
////            query.skip((pageNum - 1) * pageSize);
////            Field field=query.fields();
////            field.include(fields);
////            list= (List<T>) mongoTemplate.find(query,clazz,collectionName);
////            mongoPage.setMongoPage(total,getTotalSum(total,pageSize),list);
////
////            return mongoPage;
////        }catch (Exception e){
////            e.printStackTrace();
////            return null;
////        }
////    }
////
////    /**
////     * 用于模糊查询忽略大小写
////     * @param str
////     * @return
////     */
//    private Pattern getPattern(String str) {
//        Pattern pattern = Pattern.compile("^.*" + str + ".*$", Pattern.CASE_INSENSITIVE);
//        return pattern;
//    }
////
//    /**
//     * 用于模糊查询
//     * @param keys 匹配的字段数组
//     * @param inputStr 输入的字符
//     * @return
//     */
//    private Criteria getCriteria(String inputStr,String[] keys){
//        Pattern pattern=getPattern(inputStr);
//        Criteria criteria=null;
//        switch (keys.length){
//            case 1:criteria = Criteria.where("").orOperator(
//                    Criteria.where(keys[0]).regex(pattern));
//                break;
//            case 2:criteria = Criteria.where("").orOperator(
//                    Criteria.where(keys[0]).regex(pattern),
//                    Criteria.where(keys[1]).regex(pattern)
//            );
//                break;
//            case 3:criteria = Criteria.where("").orOperator(
//                    Criteria.where(keys[0]).regex(pattern),
//                    Criteria.where(keys[1]).regex(pattern),
//                    Criteria.where(keys[2]).regex(pattern)
//            );
//                break;
//            case 4:criteria = Criteria.where("").orOperator(
//                    Criteria.where(keys[0]).regex(pattern),
//                    Criteria.where(keys[1]).regex(pattern),
//                    Criteria.where(keys[2]).regex(pattern),
//                    Criteria.where(keys[3]).regex(pattern)
//            );
//                break;
//            case 5:criteria = Criteria.where("").orOperator(
//                    Criteria.where(keys[0]).regex(pattern),
//                    Criteria.where(keys[1]).regex(pattern),
//                    Criteria.where(keys[2]).regex(pattern),
//                    Criteria.where(keys[3]).regex(pattern),
//                    Criteria.where(keys[4]).regex(pattern)
//            );
//                break;
//            case 6:criteria = Criteria.where("").orOperator(
//                    Criteria.where(keys[0]).regex(pattern),
//                    Criteria.where(keys[1]).regex(pattern),
//                    Criteria.where(keys[2]).regex(pattern),
//                    Criteria.where(keys[3]).regex(pattern),
//                    Criteria.where(keys[4]).regex(pattern),
//                    Criteria.where(keys[5]).regex(pattern)
//            );
//                break;
//            case 7:criteria = Criteria.where("").orOperator(
//                    Criteria.where(keys[0]).regex(pattern),
//                    Criteria.where(keys[1]).regex(pattern),
//                    Criteria.where(keys[2]).regex(pattern),
//                    Criteria.where(keys[3]).regex(pattern),
//                    Criteria.where(keys[4]).regex(pattern),
//                    Criteria.where(keys[5]).regex(pattern),
//                    Criteria.where(keys[6]).regex(pattern)
//            );
//                break;
//            case 8:criteria = Criteria.where("").orOperator(
//                    Criteria.where(keys[0]).regex(pattern),
//                    Criteria.where(keys[1]).regex(pattern),
//                    Criteria.where(keys[2]).regex(pattern),
//                    Criteria.where(keys[3]).regex(pattern),
//                    Criteria.where(keys[4]).regex(pattern),
//                    Criteria.where(keys[5]).regex(pattern),
//                    Criteria.where(keys[6]).regex(pattern),
//                    Criteria.where(keys[7]).regex(pattern)
//            );
//                break;
//            case 9:criteria = Criteria.where("").orOperator(
//                    Criteria.where(keys[0]).regex(pattern),
//                    Criteria.where(keys[1]).regex(pattern),
//                    Criteria.where(keys[2]).regex(pattern),
//                    Criteria.where(keys[3]).regex(pattern),
//                    Criteria.where(keys[4]).regex(pattern),
//                    Criteria.where(keys[5]).regex(pattern),
//                    Criteria.where(keys[6]).regex(pattern),
//                    Criteria.where(keys[7]).regex(pattern),
//                    Criteria.where(keys[8]).regex(pattern)
//            );
//                break;
//            case 10:criteria = Criteria.where("").orOperator(
//                    Criteria.where(keys[0]).regex(pattern),
//                    Criteria.where(keys[1]).regex(pattern),
//                    Criteria.where(keys[2]).regex(pattern),
//                    Criteria.where(keys[3]).regex(pattern),
//                    Criteria.where(keys[4]).regex(pattern),
//                    Criteria.where(keys[5]).regex(pattern),
//                    Criteria.where(keys[6]).regex(pattern),
//                    Criteria.where(keys[7]).regex(pattern),
//                    Criteria.where(keys[8]).regex(pattern),
//                    Criteria.where(keys[9]).regex(pattern)
//            );
//                break;
//        }
//        return criteria;
//    }
////
//    /**
//     * 用于判断keys长度
//     */
//    private boolean judgeLength(int length){
//        if(length>10||length==0){
//            return true;
//        }else{
//            return false;
//        }
//    }
//
//    /**
//     * 获取总页数
//     * @param total
//     * @param pageSize
//     * @return
//     */
//    private long getTotalSum(long total,Integer pageSize){
//        long totalSum=0;
//        if(total%pageSize==0){//能够整除
//            totalSum=total/pageSize;
//        }else{
//            totalSum=total/pageSize+1;
//        }
//        return totalSum;
//    }
//
//}
//
//
