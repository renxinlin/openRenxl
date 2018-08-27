package club.renxl.www.blog.article.service;

import club.renxl.www.blog.article.domain.Comment;
import club.renxl.www.blog.article.domain.Reply;
import club.renxl.www.response.BaseResponse;

/**
 * 评论回复服务
 * @author renxl
 * @date 20180823
 *
 */
public interface ICommentReply {
	
	/**
	 * 评论回复多次异步加载
	 */
    BaseResponse lookCommentAsync(Integer articleId);
    
    /**
   	 * 评论回复多次异步加载
   	 */
    BaseResponse lookReplyAsync(Integer commentId);
    
    /**
   	 * 评论回复多加载
   	 */
    BaseResponse lookCommentReply(Comment comment);
    
    /**
     * 发表评论
     * @param comment
     * @return
     */
    BaseResponse  publishComment(Comment comment);
    
    /**
     * 发表回复
     * @param comment
     * @return
     */
    BaseResponse  publishReply(Reply reply);
    
    
    /**
     * 删除回复 文章发布人员
     */
    BaseResponse  deleteReply(Reply reply);
      
       
    // 屏蔽；审核...后期做,采用自然语言处理，鉴别文章不黄不黑社会不反社会等等，借助python做图像的鉴黄鉴暴
	
}
