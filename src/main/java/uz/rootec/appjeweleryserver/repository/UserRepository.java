package uz.rootec.appjeweleryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.rootec.appjeweleryserver.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByPhoneNumber(String phoneNumber);

    Boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);

    List<User> findAllByDirector(UUID director);
}
