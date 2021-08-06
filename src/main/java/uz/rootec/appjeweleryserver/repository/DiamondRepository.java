package uz.rootec.appjeweleryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.rootec.appjeweleryserver.entity.Diamond;
import uz.rootec.appjeweleryserver.projection.CustomDiamond;

import java.util.List;
import java.util.UUID;

public interface DiamondRepository extends JpaRepository<Diamond, UUID> {
    List<CustomDiamond> findAllByJeweleryId(UUID id);
}
