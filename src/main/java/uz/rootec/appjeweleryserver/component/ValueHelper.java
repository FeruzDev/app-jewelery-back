package uz.rootec.appjeweleryserver.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.rootec.appjeweleryserver.projection.CustomCharacteristic;
import uz.rootec.appjeweleryserver.projection.CustomDiamond;
import uz.rootec.appjeweleryserver.repository.CharacteristicRepository;
import uz.rootec.appjeweleryserver.repository.DiamondRepository;

import java.util.List;
import java.util.UUID;

@Component("ValueHelper")
public class ValueHelper {
    @Autowired
    DiamondRepository diamondRepository;

    @Autowired
    CharacteristicRepository characteristicRepository;


    public List<CustomDiamond> getCustomDiamonds(UUID id) {
        return diamondRepository.findAllByJeweleryId(id);
    }

    public List<CustomCharacteristic> getCustomCharacteristics(UUID id) {
        return characteristicRepository.findAllByDiamondId(id);
    }
}

