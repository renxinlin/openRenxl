package club.renxl.www.blog.httpapi;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import club.renxl.www.enums.UserResponse;
import club.renxl.www.imgs.ftp.utils.FtpUtil;
import club.renxl.www.response.BaseResponse;

@RestController
@RequestMapping("images")
public class ImageUploadController {
	@Value("${images.base.url}")
	private String BASEURL ;
	
	
	@RequestMapping("uploadBatch")
	public BaseResponse uploadBatch(@RequestParam("files")MultipartFile[] files){
		try {
			List<String> imagesUrls = new ArrayList<String>();
			for (MultipartFile file : files) {
				if(!file.isEmpty()) {
					 FtpUtil ftp =new  FtpUtil();
					 // 暂时支持jpg
					String fileName = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
					InputStream inputStream = file.getInputStream();
					 boolean success = ftp.uploadLocalFile(FtpUtil.BASE_PATH, fileName, inputStream);
					 if(success) {
						 imagesUrls.add(BASEURL+fileName);
					 }
				} else {
					return  BaseResponse.error(UserResponse.ImageEmptyError.getCode(), "沒有圖片...");
				}
			}
			return BaseResponse.success(imagesUrls);
		} catch (Exception e) {
			e.printStackTrace();
			return  BaseResponse.error(UserResponse.ImageEmptyError.getCode(), "");
		}
	}
	
	
	
	/**
	 * layui富文本上传
	 * @param files
	 * @return
	 */
	@RequestMapping("upload")
	public Object upload(@RequestParam("file")MultipartFile[] files){
		try {
			List<String> imagesUrls = new ArrayList<String>();
			for (MultipartFile file : files) {
				if(!file.isEmpty()) {
					 FtpUtil ftp =new  FtpUtil();
					 // 暂时支持jpg
					String fileName = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
					InputStream inputStream = file.getInputStream();
					 boolean success = ftp.uploadLocalFile(FtpUtil.BASE_PATH, fileName, inputStream);
					 if(success) {
						 imagesUrls.add(BASEURL+fileName);
					 }
				} else {
					return  BaseResponse.error(UserResponse.ImageEmptyError.getCode(), "沒有圖片...");
				}
			}
	       // string end = "{\"code\": 1,\"msg\": \"服务器故障\",\"data\": {\"src\": \"\"}}"; //返回的json
           // end = "{\"code\": 0,\"msg\": \"成功\",\"data\": {\"src\": \"" + serverPath + "\"}}";
            Map<String,Object> map = new  HashMap<String,Object>();
            Map<String,Object> url = new  HashMap<String,Object>();
            map.put("code", 0);
            map.put("msg", "成功");
            url.put("src", imagesUrls.get(0));
            map.put("data", url);

			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return  BaseResponse.error(UserResponse.ImageEmptyError.getCode(), "");
		}
	}
	
}
