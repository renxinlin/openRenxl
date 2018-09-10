package club.renxl.www.blog.httpapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import club.renxl.www.blog.article.domain.Comment;
import club.renxl.www.blog.article.service.ICommentReply;
import club.renxl.www.response.BaseResponse;

/**
 * 评论回复contents
 * @author renxl
 * @date 20180823
 *
 */
@RestController
@RequestMapping("comment")
public class CommentReplyController {
	
	@Autowired
	private ICommentReply iCommentReply;
	
	/**
	 * 查看文章的所有评论
	 * @param comment topicId 必填
	 * @return
	 */
	@PostMapping("comments")
	public BaseResponse lookCommentsById(@RequestBody Comment comment) {
		return iCommentReply.lookCommentReply(comment); 
		
	}
	
	
	
	/**
	 * 	评论发布
	 * 
	 * @param comment topicId 必填 pid 可填可不填；content必填
	 * @return
	 */
	@PostMapping("publish")
	public BaseResponse publish(@RequestBody Comment comment) {
		return iCommentReply.publishComment(comment); 
		
	}
	
	// 审核 禁止显示 等等
	

}
