package club.renxl.www.blog.article.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import club.renxl.www.blog.article.domain.Reply;
import club.renxl.www.blog.article.domain.ReplyExample;

/**
 * 评论回复|回复的回复
 * @author renxl
 * @date 20180823
 *
 */
public interface ReplyMapper   {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_reply
     *
     * @mbggenerated
     */
    int countByExample(ReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_reply
     *
     * @mbggenerated
     */
    int deleteByExample(ReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_reply
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_reply
     *
     * @mbggenerated
     */
    int insert(Reply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_reply
     *
     * @mbggenerated
     */
    int insertSelective(Reply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_reply
     *
     * @mbggenerated
     */
    List<Reply> selectByExampleWithBLOBs(ReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_reply
     *
     * @mbggenerated
     */
    List<Reply> selectByExample(ReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_reply
     *
     * @mbggenerated
     */
    Reply selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_reply
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Reply record, @Param("example") ReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_reply
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") Reply record, @Param("example") ReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_reply
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Reply record, @Param("example") ReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_reply
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Reply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_reply
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(Reply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_reply
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Reply record);
}