package uz.rootec.appjeweleryserver.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.UUID;

/**
 * Created by Sherlock on 14.07.2021.
 */
public interface CustomDiamond {
    UUID getId();

    Double getDiamond();

    @Value("#{@ValueHelper.getCustomCharacteristics(target.id)}")
    List<CustomCharacteristic> getCharacteristics();
}
