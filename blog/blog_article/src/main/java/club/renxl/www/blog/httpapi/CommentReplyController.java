package club.renxl.www.blog.httpapi;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import club.renxl.www.blog.article.domain.Comment;
import club.renxl.www.response.BaseResponse;

/**
 * 评论回复contents
 * @author renxl
 * @date 20180823
 *
 */
@RestController
@RequestMapping("comment-reply")
public class CommentReplyController {
	
	
	
	@PostMapping("pages")
	public BaseResponse lookCommentsById(Comment comment) {
		return null;
		
	}
	

}
