package club.renxl.www.blog.article.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	private static final int FIRSTINDEX = 0;
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
	/**
	 * 需要传入文章id，根节点id
	 * 默认逻辑评论的id同文章id
	 */
	@Override
	public BaseResponse lookCommentReply(Comment comment) {
		boolean legal 	                   = validateParamsIsLegal(comment);
		if(!legal) {
			return BaseResponse					 .argsError("参数不合法...");
		}
		List<Comment>comments = commentMapper.getCommentByArticleId(comment);
 		List<Comment> tree       		 = getTreeMapBySortComment(comments);
		return BaseResponse .success(tree);
	}
	
	/**
	 * 获取树结构
	 * @param comments
	 * @return
	 */
	private List<Comment> getTreeMapBySortComment(List<Comment> comments) {
		if(comments == null || comments.isEmpty()) {
			return new LinkedList<Comment>();
		}
		Long rootId = comments.get(FIRSTINDEX).getPid();
		List<Comment> rootTree = new LinkedList<Comment>();
		// 获取所有根节点
		List<Comment> newDatas = new LinkedList<Comment>();

		for(Comment comment : comments) {
			if(comment.getPid().longValue() ==  rootId.longValue()) {
				rootTree.add(comment);
			}else {
				newDatas.add(comment);
			}
		}
	    getSonByFatherWithAllData(rootTree,rootTree,newDatas);
		  
		return rootTree;
	}
	private boolean validateParamsIsLegal(Comment comment) {
		// TODO Auto-generated method stub
		return false;
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
	
	/////////////////////////////////////////////////递归开始///////////////////////////////////////////////////////////
	
	/**
	 * 递归获取下一节子节点（不获取三代以及以上节点）
	 * @param rootTree
	 * @param currentRoots
	 * @param newDatas
	 */
	private void getSonByFatherWithAllData(List<Comment> rootTree,List<Comment> currentRoots, List<Comment> newDatas) {
		// 总剩余未分配数据
		List<Comment> currentNewDatas = new LinkedList<Comment>();
		
		// 当前所有子节点节点（下次迭代根节点集合）
		List<Comment> newCurrentRoot = new LinkedList<Comment>();
		// step1: 1当前层每个元素寻找子元素，获取当前层所有的子元素
		for(Comment commentRootEveryOne : currentRoots) {
			List<Comment> children = new LinkedList<Comment>();
			for(Comment comment : newDatas) {
				if(commentRootEveryOne.getId().longValue() ==  comment.getPid().longValue()) {
					children.add(comment);
					newCurrentRoot.add(comment);
				} 
			}
			
			// step2: 给当前层每个元素绑定子元素
			// 将当前的子节点加入的所在父节点
			// 在root中获取到commentRootEveryOne并且添加改
			if(!children.isEmpty()) {
				//commentRootEveryOne.setChildren(children);
				boolean setSuccess = false;
				for(Comment isAddChildcomment:rootTree) {
					// 子元素找到要待添加树的节点
					if(setSuccess) {
						break;
					}
					if(isAddChildcomment.getId().longValue() == commentRootEveryOne.getId().longValue()) {
						isAddChildcomment.setChildren(children);
						setSuccess = true;
						break;
					}else {
						// 找到commentRootEveryOne，并设置子节点children
						setChildWithAllTreeAndChild(isAddChildcomment,children,commentRootEveryOne,setSuccess);
					}
				}
			}
		}
		
		// step3:  获取新的所有的未处理数据
		// newDatas 1，2，3，4，5
		for(Comment comment : newDatas) {
			// currentRoots 1，2，3
			// 4属于1，2，3的子节点，被处理，则剩下5需要处理
			// 获取5作为未处理的数据,即新的newdatas
			if(!newCurrentRoot.contains(comment)) {
				currentNewDatas.add(comment);
			}
		}
		if(currentNewDatas == null || currentNewDatas.isEmpty()) {
			return;
		}
		
		// 递归处理树
		getSonByFatherWithAllData(rootTree, newCurrentRoot, currentNewDatas);
 	}
	
	/**
	 * 递归添加元素
	 * @param isAddChildcomment
	 * @param children
	 * @param setSuccess
	 */
	private void setChildWithAllTreeAndChild(Comment isAddChildcomment, List<Comment> children,Comment commentRootEveryOne, boolean setSuccess) {
		List<Comment> sons = isAddChildcomment.getChildren();
		if(sons == null || sons.isEmpty()) {
			// 某一个根节点开始递归到子节点都没找到要添加子节点的元素
			//  	     1
			//		 2       3
			//    4     5  6   7
			//                8 
			// 递归逻辑，假设1包含23  2包含45  5包含67   7包含8
			// 现在要给7添加一个节点9
			// 递归按照左子树逻辑
			// 1-2-4(没找到) 
			// 1-2-5
			// 1-3-6
			// 1-3-7找到
			// 添加元素 新树如下
			// 	     	 1
			//		 2       3
			//    4     5  6   7
			//                8  9 
			
			return;
		}
		for(Comment isAddChild:sons) {
			// isAddChild 子元素找到了节点;递归中断上层for循环，也可抛出一个异常;直接中断递归程序
			if(setSuccess) {
				break;
			}
			if(isAddChild.getId().longValue() == commentRootEveryOne.getId().longValue()) {
				isAddChild.setChildren(children);
				setSuccess = true;
				break;
			}else {
				setChildWithAllTreeAndChild(isAddChild,children,commentRootEveryOne, setSuccess);
			}
			
		}
		
	}
	
	/////////////////////////////////////////////////递归结束///////////////////////////////////////////////////////////
	
	

}
