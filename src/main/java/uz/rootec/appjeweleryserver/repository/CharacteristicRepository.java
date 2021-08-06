package uz.rootec.appjeweleryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.rootec.appjeweleryserver.entity.Characteristic;
import uz.rootec.appjeweleryserver.projection.CustomCharacteristic;

import java.util.List;
import java.util.UUID;

/**
 * Created by Sherlock on 14.07.2021.
 */
public interface CharacteristicRepository extends JpaRepository<Characteristic, UUID> {
    List<CustomCharacteristic> findAllByDiamondId(UUID id);
}
