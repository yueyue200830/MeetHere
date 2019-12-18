package com.proj.meethere.dao;
import com.proj.meethere.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author Yiqing Tao, Tresaresa
 * @Date 2019-10-29
 * News Repository
 */
@Repository
public interface NewsRepository extends JpaRepository<News,Integer>{
    @Query(value = "select * from news order by news_time desc",nativeQuery = true)
    List<News> selectAllNews();

    @Transactional
    @Modifying
    @Query(value = "delete from news where id = :id",nativeQuery = true)
    int deleteSpecificNews(@Param("id") int id);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "Update news set news_content = :newsContent, news_title = :newsTitle, news_photo = :newsPhoto where id = :id",nativeQuery = true)
    int updateSpeceficNews(@Param("newsContent") String newsContent, @Param("newsTitle") String newsTitle, @Param("newsPhoto") Blob newsPhoto, @Param("id") int id);

    @Query(value = "select * from news where id = :id", nativeQuery = true)
    List<News> selectSpecificNews(@Param("id") int id);

    @Modifying
    @Query(value = "insert into news (news_content, news_title, news_photo, news_time) " +
            "value (:newsContent, :newsTitle, :newsPhoto, now())", nativeQuery = true)
    int insertNewNews(@Param("newsContent") String newsContent, @Param("newsTitle") String newsTitle,
                      @Param("newsPhoto") Blob newsPhoto) throws SQLException;

    @Query(value = "select * form news where id>=:first and id<=:last order by news_time desc", nativeQuery = true)
    List<News> findNewsByScope(@Param("first") int first, @Param("last") int last);

    @Query(value = "select * from news where id = :id", nativeQuery = true)
    List<News> findNewsPhotoById(@Param("id") int id);

}
