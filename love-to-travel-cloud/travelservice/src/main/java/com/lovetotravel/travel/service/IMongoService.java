//package com.lovetotravel.travel.service;
//
//import com.lovetotravel.travel.entity.page.MongoPage;
//import com.lovetotravel.travel.entity.page.PageVo;
//
//import java.io.Serializable;
//import java.util.List;
//
///**
// * @author: 会飞的种花家
// * @date: 2021/5/29 21:21:08
// * @title mongodb服务接口类
// * @参数
// * @entity 实体对象
// * @collectionName 数据库中的集合名
// * @list   实体集合对象
// * @keys   键名数组
// * @values 键值数组
// * @fields 需要查询的列
// * @id     id值
// * @MyPage Page类，含有pageNum（当前页），pageSize（页大小）
// * @MongoPage MyPage的子类，多了total（总记录数）和list（实体集合）
// * @inputStr 模糊查询时输入的字符串
// */
//public interface IMongoService<T> {
//
//    /**
//     * 增
//     */
//    boolean save(T entity);
//    boolean save(T entity,String collectionName);
//    boolean saveList(List<T> list);
//    boolean saveList(List<T> list,String collectionName);
//
//    /**
//     * 删
//     */
//    boolean removeById(T entity);
//    boolean removeById(T entity,String collectionName);
//    /**
//     * 改,都是通过id
//     * @param keys 要修改的键名
//     * @param values 要修改的键值
//     * 相当于mysql中  update key1,key2,key3 set value1,value2,value3 where id=#{id} from collectionName
//     */
//    boolean updateById(String[] keys,Object[] values,Serializable id,Class<?> clazz);
//    boolean updateById(String[] keys,Object[] values,Serializable id,String collectionName);
//    boolean updateById(String[] keys,Object[] values,Serializable id,Class<?> clazz,String collectionName);
//
//    /**
//     * 查
//     * @finById 就是根据id查询单条记录
//     * @findByMap 相当于条件查询,查询出符合条件的所有内容
//     * @findAll 查询表中所有记录
//     * @fields 表示需要查询的字段---参考mysql中 select key1,key2,key3 from collectionName
//     */
//    T findById(Serializable id,Class<?> clazz);
//    T findById(Serializable id,Class<?> clazz,String collectionName);
//    T findById(Serializable id,String[] fields,Class<?> clazz);
//    T findById(Serializable id,String[] fields,Class<?> clazz,String collectionName);
//    List<T> findByMap(String[] keys,Object[] values,Class<?> clazz);
//    List<T> findByMap(String[] keys,Object[] values,Class<?> clazz,String collectionName);
//    List<T> findByMap(String[] keys,Object[] values,String[] fields,Class<?> clazz);
//    List<T> findByMap(String[] keys,Object[] values,String[] fields,Class<?> clazz,String collectionName);
//    List<T> findAll(Class<?> clazz);
//    List<T> findAll(Class<?> clazz,String collectionName);
//    List<T> findAll(String[] fields,Class<?> clazz);
//    List<T> findAll(String[] fields,Class<?> clazz,String collectionName);
//    /**
//     * 分页---无总记录数，只查一次
//     */
//    List<T> page(PageVo pageVo, Class<?> clazz);
//    List<T> page(PageVo pageVo, Class<?> clazz, String collectionName);
//    List<T> page(PageVo pageVo, String[] fields, Class<?> clazz);
//    List<T> page(PageVo pageVo, String[] fields, Class<?> clazz, String collectionName);
//    /**
//     * 分页--有总记录数，查两次
//     */
//    MongoPage page(MongoPage mongoPage, Class<?> clazz);
//    MongoPage page(MongoPage mongoPage, Class<?> clazz,String collectionName);
//    MongoPage page(MongoPage mongoPage,String[] fields,Class<?> clazz);
//    MongoPage page(MongoPage mongoPage,String[] fields, Class<?> clazz,String collectionName);
//
//
//    /**
//     * 模糊查询---分页
//     */
//    List<T> fuzzyPage(PageVo pageVo, Class<?> clazz, String inputStr, String[] keys);
//    List<T> fuzzyPage(PageVo pageVo, Class<?> clazz, String collectionName, String inputStr, String[] keys);
//    List<T> fuzzyPage(PageVo pageVo, Class<?> clazz, String inputStr, String[] keys, String[] fields);
//    List<T> fuzzyPage(PageVo pageVo, Class<?> clazz, String collectionName, String inputStr, String[] keys, String[] fields);
//
////    /**
////     * 模糊查询---分页--有总记录数，查两次
////     */
////    MongoPage fuzzyPage(FuzzyPage fuzzyPage, Class<?> clazz, String[] keys);
////    MongoPage fuzzyPage(FuzzyPage fuzzyPage, Class<?> clazz,String collectionName,String[] keys);
////    MongoPage fuzzyPage(FuzzyPage fuzzyPage,Class<?> clazz,String[] keys,String[] fields);
////    MongoPage fuzzyPage(FuzzyPage fuzzyPage, Class<?> clazz,String collectionName,String[] keys,String[] fields);
//}
//
//
