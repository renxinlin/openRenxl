package club.renxl.www.blog.article.dao;

import club.renxl.www.blog.article.domain.Artitle;
import club.renxl.www.blog.article.domain.ArtitleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
 
public interface ArtitleMapper   {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_artitle
     *
     * @mbggenerated
     */
    int countByExample(ArtitleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_artitle
     *
     * @mbggenerated
     */
    int deleteByExample(ArtitleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_artitle
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_artitle
     *
     * @mbggenerated
     */
    int insert(Artitle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_artitle
     *
     * @mbggenerated
     */
    int insertSelective(Artitle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_artitle
     *
     * @mbggenerated
     */
    List<Artitle> selectByExampleWithBLOBs(ArtitleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_artitle
     *
     * @mbggenerated
     */
    List<Artitle> selectByExample(ArtitleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_artitle
     *
     * @mbggenerated
     */
    Artitle selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_artitle
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Artitle record, @Param("example") ArtitleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_artitle
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") Artitle record, @Param("example") ArtitleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_artitle
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Artitle record, @Param("example") ArtitleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_artitle
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Artitle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_artitle
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(Artitle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_artitle
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Artitle record);
}