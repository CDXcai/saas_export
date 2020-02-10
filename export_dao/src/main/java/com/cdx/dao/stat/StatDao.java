package com.cdx.dao.stat;

import java.util.List;
import java.util.Map;

public interface StatDao {
    //获取生产厂家的统计数据
    List<Map> findFactoryData(String companyId) ;

    //获取产品销量排行榜的数据
    List<Map> findSellData(String companyId) ;

    //系统访问压力图的数据
    List<Map> findOnlineData(String companyId) ;
}
