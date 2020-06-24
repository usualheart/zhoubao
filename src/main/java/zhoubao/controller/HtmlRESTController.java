package zhoubao.controller;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import zhoubao.data.DetailItemRepository;
import zhoubao.data.SectionItemRepository;
import zhoubao.data.SummaryItemRepository;
import zhoubao.domain.DetailItem;
import zhoubao.domain.SectionItem;
import zhoubao.domain.SummaryItem;

@RestController // 为该控制器的所有处理方法应用消息转换功能
@RequestMapping(value = "/htmlZhoubao")
public class HtmlRESTController {

	@Autowired
	private SectionItemRepository sectionItemRepository;
	@Autowired
	private DetailItemRepository detailItemRepository;
	@Autowired
	private SummaryItemRepository summaryItemRepository;

	/***************************************
	 * 
	 * 表格相关操作
	 * 
	 *************************************/
	// 按照userName week查找用户表格数据
	@RequestMapping(value = "/getSection/{userName}/{week}", method = RequestMethod.GET)
	public List<SectionItem> getSection(@PathVariable("userName") String userName, @PathVariable Integer week) {
		if (week == -1)
			week = WeekNo.getWeekCount();// 为-1表示获取当周周报
		return sectionItemRepository.findByUserNameAndWeek(userName, week);
	}

	// 保存表格数据
	@RequestMapping(value = "/saveSection", method = RequestMethod.POST)
	public List<SectionItem> saveSection(@RequestBody List<SectionItem> sectionItems) {
		Integer week = WeekNo.getWeekCount();
		sectionItems.iterator().forEachRemaining(tmp -> {
			tmp.setTime(new Date());
			tmp.setWeek(week);
		});
		return sectionItemRepository.saveAll(sectionItems);

	}

	// 更新表格数据
	@RequestMapping(value = "/updateSection", method = RequestMethod.POST)
	public void updateSection(@RequestBody List<SectionItem> sectionItems) {

		sectionItemRepository.saveAll(sectionItems);
	}

	/***************************************
	 * 
	 * 片段相关操作
	 * 
	 *************************************/
	// 按照userName week查找用户片段数据
	@RequestMapping(value = "/getDetail/{userName}/{week}", method = RequestMethod.GET)
	public List<DetailItem> getDetail(@PathVariable("userName") String userName, @PathVariable Integer week) {
		if (week == -1)
			week = WeekNo.getWeekCount();// 为-1表示获取当周周报
		return detailItemRepository.findByUserNameAndWeek(userName, week);
	}
	// 按照userName week查找用户片段数据
		@RequestMapping(value = "/getAllDetail/{userName}/{time}", method = RequestMethod.GET)
		public List<DetailItem> getAllDetail(@PathVariable("userName") String userName, @PathVariable Date time) {
			System.out.println(time);
			return detailItemRepository.findByUserNameAndTimeAfterOrderByIdDesc(userName, time);
		}

	// 按照id删除相应片段
	@RequestMapping(value = "/delDetail/{id}", method = RequestMethod.GET)
	public void delDetail(@PathVariable Integer id) {
		detailItemRepository.deleteById(id);
	}

	// 保存多个片段数据
	@RequestMapping(value = "/saveDetail", method = RequestMethod.POST)
	public List<DetailItem> saveDetail(@RequestBody List<DetailItem> detailItems) {
		Integer week = WeekNo.getWeekCount();
		detailItems.iterator().forEachRemaining(tmp -> {
			tmp.setTime(new Date());
			tmp.setWeek(week);
		});
		return detailItemRepository.saveAll(detailItems);

	}

	// 更新片段数据
	@RequestMapping(value = "/updateDetail", method = RequestMethod.POST)
	public void updateDetail(@RequestBody List<DetailItem> detailItems) {

		detailItemRepository.saveAll(detailItems);
	}

	/***************************************
	 * 
	 * 总结相关操作
	 * 
	 *************************************/

	// 按照userName week查找用户总结数据
	@RequestMapping(value = "/getSummary/{userName}/{week}", method = RequestMethod.GET)
	public List<SummaryItem> getSummary(@PathVariable("userName") String userName, @PathVariable Integer week) {
		if (week == -1)
			week = WeekNo.getWeekCount();// 为-1表示获取当周周报
		return summaryItemRepository.findByUserNameAndWeek(userName, week);
	}

	// 保存总结数据
	@RequestMapping(value = "/saveSummary", method = RequestMethod.POST)
	public SummaryItem saveSummary(@RequestBody SummaryItem summaryItem) {
		Integer week = WeekNo.getWeekCount();
		summaryItem.setTime(new Date());
		summaryItem.setWeek(week);
		return summaryItemRepository.save(summaryItem);
	}

	// 更新总结数据
	@RequestMapping(value = "/updateSummary", method = RequestMethod.POST)
	public SummaryItem updateSummary(@RequestBody SummaryItem summaryItem) {

		return summaryItemRepository.save(summaryItem);
	}

}
