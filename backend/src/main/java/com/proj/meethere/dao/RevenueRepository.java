package com.proj.meethere.dao;
import com.proj.meethere.entity.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Yiqing Tao, Tresaresa
 * @Date 2019-10-29
 * Revenue Repository
 */
@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Integer> {
    @Query(value = "select * from revenue order by id", nativeQuery = true)
    List<Revenue> getAllRvnInfo();

    @Query(value = "select rvn_name from revenue", nativeQuery = true)
    List<String> getRvnName();

    @Query(value = "select * from revenue where id = :id",nativeQuery = true)
    List<Revenue> getSpecificRvn(@Param("id") int id);

    @Query(value = "select id from revenue where rvn_name=:name", nativeQuery = true)
    int searchIdByName(@Param("name") String name);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "Update revenue set rvn_price = :rvn_price, rvn_intro = :rvn_intro where id = :id",nativeQuery = true)
    int updateRvnInfo(@Param("rvn_price") int rvn_price, @Param("rvn_intro") String rvn_intro, @Param("id") int id);

    @Modifying
    @Query(value = "insert into revenue (rvn_name, rvn_roomnum, rvn_price, rvn_info) " +
            "value (:rvn_name, :rvn_roomnum, :rvn_price, :rvn_intro)", nativeQuery = true)
    int insertNewRevenue(@Param("rvn_name") String rvn_name, @Param("rvn_roomnum") int rvn_roomnum,
                         @Param("rvn_price") int rvn_price, @Param("rvn_intro") String rvn_intro);

}
