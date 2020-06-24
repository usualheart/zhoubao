package zhoubao.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import zhoubao.domain.SectionItem;

public interface SectionItemRepository extends JpaRepository<SectionItem,Integer> {

	List<SectionItem> findByUserNameAndWeek(String userName, Integer week);


}
