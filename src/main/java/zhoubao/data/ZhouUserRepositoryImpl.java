package zhoubao.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import zhoubao.domain.ZhouUser;

public class ZhouUserRepositoryImpl implements ZhouUserSweeper{

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<ZhouUser> findNameLike(String  xing) {
		String findNameLike="select s from ZhouUser s where s.username like '%"+xing+"%'";
		// TODO 自动生成的方法存根
		return (List<ZhouUser>)em.createQuery(findNameLike).getResultList();
	}
}
