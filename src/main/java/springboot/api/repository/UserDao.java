package springboot.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.api.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
}
