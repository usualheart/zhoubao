package zhoubao.data;

import java.util.List;

import zhoubao.domain.ZhouUser;

public interface ZhouUserSweeper {
	List <ZhouUser> findNameLike(String xing);
}
