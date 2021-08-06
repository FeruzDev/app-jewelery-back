package uz.rootec.appjeweleryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.rootec.appjeweleryserver.entity.Role;
import uz.rootec.appjeweleryserver.entity.enums.RoleName;

import java.util.List;

//@RepositoryRestResource(path = "role",collectionResourceRel = "list",excerptProjection = CustomRole.class)
public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findAllByName(RoleName roleName);
    List<Role> findAllByNameIn(List<RoleName> names);
}
