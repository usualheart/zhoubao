package zhoubao.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import zhoubao.domain.ZhouBaoUserInfo;
import zhoubao.modules.oss.OSSFactory;
import zhoubao.modules.oss.StorageService;

@RestController // 为该控制器的所有处理方法应用消息转换功能
@RequestMapping(value = "/zhoubao")
public class ZhouBaoRESTController {

	@Autowired
	private ZhouBaoRepository zhouBaoRepository;
	@Autowired
	Environment env;// 用于读取配置中的文件

	@Autowired
	StorageService storageService;// 用于存储周报文件

	// 处理路径参数 按照userName查找用户周报数据
	@RequestMapping(value = "/getSomeone/{userName}", method = RequestMethod.GET)
	public List<ZhouBao> spittleId(@PathVariable("userName") String userName) {
		return zhouBaoRepository.findByUserName(userName);

	}

	// 获取所有周报
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public List<ZhouBaoUserInfo> getAll() {
		return zhouBaoRepository.findAllWithUserInfo();
	}

	// 获取最近几周周报,连同用户信息一起返回
	@RequestMapping(value = { "/getRecentWeeks/", "/getRecentWeeks/{afterWeek}" }, method = RequestMethod.GET)
	public List<ZhouBaoUserInfo> getRecentAll(@PathVariable Optional<Integer> afterWeek) {

		Integer weekNow = WeekNo.getWeekCount();
		if (afterWeek.isPresent()) {
			return zhouBaoRepository.findRecentWithUserInfo(weekNow-afterWeek.get());//
		} else {
			return zhouBaoRepository.findRecentWithUserInfo(weekNow-5);
		}
	}

	// 上传pdf或md文件的处理器
	@RequestMapping(value = "/uploadPdfMd/{userName}", method = RequestMethod.POST)
	public void uploadPdfMd(
			@RequestPart(value = "pdfMdMultipartFile", required = true) MultipartFile pdfMdMultipartFile,
			@PathVariable String userName) throws IllegalStateException, IOException {

		String type = pdfMdMultipartFile.getContentType();
		// System.out.println(type);
		if (type.compareToIgnoreCase("application/pdf") == 0) {
			type = "pdf";
		} else {
			type = "MarkdownFile";
		}

		String path = "user/" + userName + "/" + type + '/'+ (int) (Math.random() * 1000) + "-" + pdfMdMultipartFile.getOriginalFilename();
		storageService.upload(pdfMdMultipartFile,path);
		ZhouBao newZhouBao = new ZhouBao(userName, path, new Date(), WeekNo.getWeekCount());
		zhouBaoRepository.save(newZhouBao);
	}

	// 删除指定pdf周报 所删除的周报id在json中
	@RequestMapping(value = "/delPdfMd/{Id}", method = RequestMethod.GET)
	public void delPdfMd(@PathVariable Integer Id) {
		String filePath = zhouBaoRepository.findById(Id).get().getFilePath();
		// 按照路径对文件进行删除
		File f = new File(env.getProperty("zhoubao.location") + filePath);

		System.out.println("删除文件路径:" + f.getAbsolutePath());
		if (f.delete())
			zhouBaoRepository.deleteById(Id);// 若文件删除成功 则删除在数据库中的记录
	}

	// 在线生成markdown周报
	@RequestMapping(value = "/saveMarkdown/{userName}", method = RequestMethod.POST)
	public void saveMarkdown(@PathVariable String userName, @RequestBody Map<String, String> mdData) {
		String parentPath = "user/" + userName + "/" + "MarkdownFile" + '/';
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String fileName, relativePath;
		File fileToSave;
		// 用来防止同名文件
		do {
			fileName = (int) (Math.random() * 1000) + "-" + sdf.format(new Date()) + "-" + userName + "的周报.md";
			relativePath = parentPath + fileName;
			fileToSave = new File(env.getProperty("zhoubao.location") + relativePath);
		} while (fileToSave.exists());
		fileToSave.getParentFile().mkdirs();// 创建文件目录

		/********************
		 * 写入文件操作
		 *****************/
		// 以字节流形式写入文件
		String mdContent = mdData.get("content");
		try {
			// 创建基于文件的输出流
			FileOutputStream fos = new FileOutputStream(fileToSave);
			// 把数据写入到输出流
			fos.write(mdContent.getBytes("utf-8"));
			// 关闭输出流
			fos.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(mdData.get("content"));

		ZhouBao newZhouBao = new ZhouBao(userName, relativePath, new Date(), WeekNo.getWeekCount());
		zhouBaoRepository.save(newZhouBao);
	}
	// 更新周报 用于保存评价
		@RequestMapping(value = "/updateZhoubao", method = RequestMethod.POST)
		public void updateZhoubao( @RequestBody ZhouBao newZhouBao) {
			zhouBaoRepository.save(newZhouBao);
		}

	// 插入html周报记录
	@RequestMapping(value = "/makeHtmlZhoubao/{userName}", method = RequestMethod.GET)
	public void makeHtmlZhoubao(@PathVariable String userName) {
		ZhouBao newZhouBao = new ZhouBao(userName, "-1", new Date(), WeekNo.getWeekCount());
		zhouBaoRepository.save(newZhouBao);
	}

}
