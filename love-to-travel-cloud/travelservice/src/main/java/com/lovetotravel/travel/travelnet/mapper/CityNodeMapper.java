//package com.lovetotravel.travel.travelnet.mapper;
//
//import com.lovetotravel.travel.travelnet.entity.City;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//
//import java.util.List;
//
//@Mapper
//public interface CityNodeMapper {
//    /**
//     * 通过ID查询单条数据
//     *
//     * @param sceneryId 主键
//     * @return 实例对象
//     */
//    City queryById(Integer sceneryId);
//
//    /**
//     * 通过实体作为筛选条件查询
//     *
//     * @return 对象列表
//     */
//    List<City> queryAll();
//
//    List<City> queryByTableId(@Param("sceneryId") Integer dataTableId);
//    /**
//     * 新增数据
//     *
//     * @param metaDataColumn 实例对象
//     * @return 影响行数
//     */
//    int insert(Scenery metaDataColumn);
//
//    /**
//     * 修改数据
//     *
//     * @param scenery 实例对象
//     * @return 影响行数
//     */
//    int update(Scenery scenery);
//
//    /**
//     * 通过主键删除数据
//     *
//     * @param sceneryId 主键
//     * @return 影响行数
//     */
//    int deleteById(Integer sceneryId);
//
//}
