package zhoubao.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import zhoubao.data.ZhouBaoRepository;
import zhoubao.domain.ZhouBao;

@RestController // 为该控制器的所有处理方法应用消息转换功能
@RequestMapping(value = "/img", produces = "text/html;charset=UTF-8")
public class ImgRESTController {

	@Autowired
	Environment env;// 用于读取配置中的文件

	// 上传pdf或md文件的处理器
	@RequestMapping(value = "/uploadImg/{userName}", method = RequestMethod.POST)
	public String uploadImg(@RequestPart(value = "imgMultipartFile", required = true) MultipartFile imgMultipartFile,
			@PathVariable String userName) throws IllegalStateException, IOException {
		String parentPath = "img/" + userName + "/";

		String fileName, relativePath;
		File fileToSave;
		// 用来防止同名文件
		do {
			fileName = (int) (Math.random() * 1000) + "-" + imgMultipartFile.getOriginalFilename();
			relativePath = parentPath + fileName;
			fileToSave = new File(env.getProperty("zhoubao.location") + relativePath);
		} while (fileToSave.exists());
		fileToSave.getParentFile().mkdirs();// 创建文件目录

		// 存储文件到新路径 这里的相对路径 相对的是MultipartFile文件缓存的位置
		imgMultipartFile.transferTo(fileToSave);
		return relativePath;// 返回图片相对路径
	}

	// 删除指定路径的周报
	@RequestMapping(value = "/delImg", method = RequestMethod.POST)
	public void delImg(@RequestBody Map<String, String> imgData) {
		File f=new File(env.getProperty("zhoubao.location") + imgData.get("filePath"));
		System.out.println("删除图片:" + f.getAbsolutePath());// 若文件删除成功
		if (f.delete())
			System.out.println("成功删除图片:" + f.getAbsolutePath());// 若文件删除成功
	}
}
