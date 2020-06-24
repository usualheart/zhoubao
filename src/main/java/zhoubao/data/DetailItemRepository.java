package zhoubao.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import zhoubao.domain.DetailItem;

public interface DetailItemRepository extends JpaRepository<DetailItem,Integer> {

	List<DetailItem> findByUserNameAndWeek(String userName, Integer week);
	
	List<DetailItem> findByUserNameAndTimeAfterOrderByIdDesc(String userName, Date time);
	

}
