package club.renxl.www.blog.article.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import club.renxl.www.blog.article.dao.CommentMapper;
import club.renxl.www.blog.article.dao.ReplyMapper;
import club.renxl.www.blog.article.domain.Comment;
import club.renxl.www.blog.article.domain.Reply;
import club.renxl.www.blog.article.service.ICommentReply;
import club.renxl.www.response.BaseResponse;

@Service
public class ICommentReplyImpl implements ICommentReply {
	private static final Logger logger = LoggerFactory.getLogger(ICommentReplyImpl.class);
	@Autowired
	private ReplyMapper replyMapper;
	@Autowired
	private CommentMapper commentMapper;
	
	
	@Override
	public BaseResponse lookCommentAsync(Integer articleId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public BaseResponse lookReplyAsync(Integer commentId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public BaseResponse lookCommentReply(Integer articleId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public BaseResponse publishComment(Comment comment) {
		// 校验
		boolean legal 		  = validateComment(comment);
		
		if(!legal) {
			return BaseResponse.argsError("参数不全...");
		}
		// 业务参数补充
		comment 		   = addOtherFieldByBiz(comment);

		// 发布评论
		commentMapper                   .insert(comment);
		return BaseResponse		 .success("评论成功...");  
	}
	
	
	@Override
	public BaseResponse publishReply(Reply reply) {
		logger			  .info("json ==>publishReply");

		// 校验
		boolean legal 		  	  = validateReply(reply);
		if(!legal) {
			return BaseResponse.argsError("参数不全...");
		}
		// 业务参数补充
		reply 				 = addOtherFieldByBiz(reply);

		// 发布评论
		replyMapper                   	  .insert(reply);
		return BaseResponse		 .success("回复成功...");  
	}
	
	

	@Override
	public BaseResponse deleteReply(Reply reply) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * 发布评论的校验
	 * @param comment
	 * @return
	 */
	private boolean validateComment(Comment comment) {
		if(StringUtils.isEmpty(comment) 
				|| StringUtils.isEmpty(comment.getTopicId())
				|| StringUtils.isEmpty(comment.getContent())
				|| StringUtils.isEmpty(comment.getIsReply())
				) {
			return false;

		}
		
		return true;
	}
	
	/**
	 * 发布评论业务参数补充
	 * @param comment
	 * @return
	 */
	private Comment addOtherFieldByBiz(Comment comment) {
		 
		return comment;
	}
	
	/**
	 * 发布回复的校验
	 * @param reply
	 * @return
	 */
	private boolean validateReply(Reply reply) {
		if(StringUtils.isEmpty(reply)
				|| StringUtils.isEmpty(reply.getCommentId())
				||StringUtils.isEmpty(reply.getReplyType())
				||StringUtils.isEmpty(reply.getReplyId())
				||StringUtils.isEmpty(reply.getContent())){
			return false;

		}
		
		return true;
	}
	/**
	 * 发布回复的业务参数补充
	 * @param reply
	 * @return
	 */
	private Reply addOtherFieldByBiz(Reply reply) {
		return reply;
	}
}
