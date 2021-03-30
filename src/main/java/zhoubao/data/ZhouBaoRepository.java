package zhoubao.data;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import zhoubao.domain.ZhouBao;
import zhoubao.domain.ZhouBaoUserInfo;

public interface ZhouBaoRepository extends JpaRepository<ZhouBao,Integer> {


	@Query(value = "SELECT * FROM zhoubao where userName=?1 order by id desc", nativeQuery = true)
	List<ZhouBao> findByUserName(String name);
	@Query(value = "SELECT * FROM zhoubao  order by id desc", nativeQuery = true)
	List<ZhouBao>findAllOrderByIdDesc();
	//返回结果映射到对象 并返回 ZhouBaoUserInfo继承了Serializable接口
	@Query(value = " SELECT new zhoubao.domain.ZhouBaoUserInfo(z.id,z.userName,z.filePath,z.time,z.week,z.pingjia,u.type,u.year)  "
			+ "FROM ZhouBao z, ZhouUser u where z.userName=u.username  order by z.week desc,u.type desc,u.year"
			)
	List<ZhouBaoUserInfo> findRecentWithUserInfo(Integer week);

	@Query(value = " SELECT  zhoubao.* ,user.type,user.year  FROM zhoubao Left "
			+ "JOIN user ON zhoubao.userName=user.username "
			+ "order by week desc,type desc,year;", nativeQuery = true)
	List<ZhouBaoUserInfo> findAllWithUserInfo();

}
