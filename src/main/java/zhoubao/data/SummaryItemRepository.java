package zhoubao.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import zhoubao.domain.SectionItem;
import zhoubao.domain.SummaryItem;

public interface SummaryItemRepository extends JpaRepository<SummaryItem,Integer> {

	List<SummaryItem> findByUserNameAndWeek(String userName, Integer week);


}
