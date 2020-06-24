package zhoubao.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import zhoubao.domain.Spitter;

@Component
public interface SpitterRepository extends JpaRepository<Spitter, Long> {

	Spitter findByUsername(String username);
}
