package com.proj.meethere.dao;
import com.proj.meethere.entity.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Yiqing Tao
 * @Date 2019-10-29
 * Revenue Repository
 */
@Repository
public interface RevenueRepository extends JpaRepository<Revenue,Integer> {
    @Query(value = "select * from revenue",nativeQuery = true)
    List<Revenue> getAllRvnInfo();

    @Query(value = "select * from revenue where id = :id",nativeQuery = true)
    List<Revenue> getSpecificRvn(@Param("id") int id);

    @Modifying
    @Query(value = "Update revenue set rvn_price = :rvnPrice and rvn_intro = :rvnIntro where id = :id",nativeQuery = true)
    int updateRvnInfo(@Param("rvnPrice") int rvnPrice, @Param("rvnIntro") String rvnIntro, @Param("id") int id);


}
