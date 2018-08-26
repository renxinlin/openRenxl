package club.renxl.www.blog.article.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import club.renxl.www.blog.article.domain.Comment;
import club.renxl.www.blog.article.domain.CommentExample;


/**
 * 
 * 文章评论
 * @author renxl
 * @date 20180823
 *
 */
public interface CommentMapper   {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_comment
     *
     * @mbggenerated
     */
    int countByExample(CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_comment
     *
     * @mbggenerated
     */
    int deleteByExample(CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_comment
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_comment
     *
     * @mbggenerated
     */
    int insert(Comment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_comment
     *
     * @mbggenerated
     */
    int insertSelective(Comment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_comment
     *
     * @mbggenerated
     */
    List<Comment> selectByExampleWithBLOBs(CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_comment
     *
     * @mbggenerated
     */
    List<Comment> selectByExample(CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_comment
     *
     * @mbggenerated
     */
    Comment selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_comment
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_comment
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") Comment record, @Param("example") CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_comment
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_comment
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Comment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_comment
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(Comment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table axin_blogs_comment
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Comment record);
}