package zhoubao.data;


import org.springframework.data.jpa.repository.JpaRepository;

import zhoubao.domain.ZhouUser;

public interface ZhouUserRepository extends JpaRepository<ZhouUser,Long>,ZhouUserSweeper {


	ZhouUser findByUsername(String name);

}
