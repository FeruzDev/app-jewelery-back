package uz.rootec.appjeweleryserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.rootec.appjeweleryserver.entity.Jewelery;
import uz.rootec.appjeweleryserver.projection.CustomJewelery;

import java.util.List;
import java.util.UUID;

public interface JeweleryRepository extends JpaRepository<Jewelery, UUID> {
    Page<CustomJewelery> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<CustomJewelery> findAllByExpertIdOrderByCreatedAtDesc(UUID id, Pageable pageable);


    List<Jewelery> findAllBySerial(String serial);

    CustomJewelery findBySerial(String serial);
}
