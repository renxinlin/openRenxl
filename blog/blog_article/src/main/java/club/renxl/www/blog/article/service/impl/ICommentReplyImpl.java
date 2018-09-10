package club.renxl.www.blog.article.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;

import club.renxl.www.blog.article.dao.ArtitleMapper;
import club.renxl.www.blog.article.dao.CommentMapper;
import club.renxl.www.blog.article.domain.Artitle;
import club.renxl.www.blog.article.domain.ArtitleExample;
import club.renxl.www.blog.article.domain.ArtitleExample.Criteria;
import club.renxl.www.blog.article.domain.Comment;
import club.renxl.www.blog.article.service.ICommentReply;
import club.renxl.www.response.BaseResponse;

@Service
public class ICommentReplyImpl implements ICommentReply {
	// private static final Logger logger = LoggerFactory.getLogger(ICommentReplyImpl.class);
	private static final int FIRSTINDEX = 0;
	/**
	 * 回复
	 */
	private static final Byte isReply = (byte)1;
	/**
	 * 评论
	 */
	private static final Byte isNotReply = (byte)0; 
	
	
	@Autowired
	private CommentMapper commentMapper;
	
	
	@Autowired
	private  ArtitleMapper artitleMapper;
	
	
	@Autowired
	private  IdGenerator idGenerator;
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
		//TODO 采用自定义函数查询父子结构，则表的路由出错问题
		// comment = addOtherFieldsByBiz(comment);
		// 这是一颗待排序的树，sql取出的数据并无规则，只是按照发布时间排序
		List<Comment>comments = commentMapper.getCommentByArticleId(comment);
 		List<Comment> tree       		 = getTreeMapBySortComment(comments);
		return BaseResponse .success(tree);
	}
	private boolean validateParamsIsLegal(Comment comment) {
		if(comment == null) {
			return false;

		}
		
		if(comment.getTopicId() == null) {
			return false;

		}
	
		return true;
	}
	/**
	 * 组装业务属性
	 * @param comment
	 * @return
	 */
	@SuppressWarnings("unused")
	private Comment addOtherFieldsByBiz(Comment comment) {
		comment.setId(comment.getTopicId());
		return comment;
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
 		List<Comment> rootTree = new LinkedList<Comment>();
		// 获取所有根节点
		List<Comment> newDatas = new LinkedList<Comment>();

		for(Comment comment : comments) {
			if(comment.getPid() == null) {
				rootTree.add(comment);
			}else {
				newDatas.add(comment);
			}
		}
	    getSonByFatherWithAllData(rootTree,rootTree,newDatas);
		  
		return rootTree;
	}

	@Override
	public BaseResponse publishComment(Comment comment) {
		// 校验
		boolean legal 		  = validateComment(comment);
		
		if(!legal) {
			return BaseResponse.argsError("参数不全...");
		}
		
		boolean exist = isArticleExists(comment);
		if(!exist) {
			return BaseResponse.argsError("文章不存在...");
		}
		if(comment.getPid() !=null){
			Comment fatherComments = commentMapper.selectByPrimaryKey(comment.getPid());
			if(StringUtils.isEmpty(fatherComments)){
				return BaseResponse.argsError("回复失败...");
			}
		}
		
		
		
		// 业务参数补充
		comment 		   = addOtherFieldByBiz(comment);

		// 发布评论
		commentMapper                   .insertSelective(comment);
		return BaseResponse		 .success("评论成功...");  
	}
	 
	/**
	 * 校验文章是否存在
	 * @param comment 传入topicId
	 * @return
	 */
	private boolean isArticleExists(Comment comment) {
		ArtitleExample example = new ArtitleExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andIdEqualTo(comment.getTopicId());
		List<Artitle> existData = artitleMapper.selectByExample(example );
		if(existData != null && !existData.isEmpty()) {
			return true;
		}
		return false;
	}
	/**
	 * 发布评论的校验,为回复时需要携带pid
	 * @param comment
	 * @return
	 */
	private boolean validateComment(Comment comment) {
		if(StringUtils.isEmpty(comment) 
				|| StringUtils.isEmpty(comment.getTopicId())
				|| StringUtils.isEmpty(comment.getContent())
				)  {
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
		comment.setId(idGenerator.generateId().longValue());
		comment.setCreateTime(new Date());
		if(comment.getPid() == null) {
			// 评论
			comment.setIsReply(isReply);
		}else {
			// 回复
			comment.setIsReply(isNotReply);

		}
		return comment;
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
