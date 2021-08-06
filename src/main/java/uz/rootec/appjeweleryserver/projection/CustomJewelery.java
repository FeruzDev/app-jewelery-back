package uz.rootec.appjeweleryserver.projection;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by Sherlock on 14.07.2021.
 */
public interface CustomJewelery {
    UUID getId();

    @Value("#{target.photo!=null?target.photo.id:null}")
    UUID getPhoto();

    Timestamp getDate();

    String getName();

    Double getTotalWeight();

    String getMetal();

    Double getHallMark();

    String getSerial();

    @Value("#{target.expert!=null?target.expert.firstName:null}")
    String getExpertFirstName();

    @Value("#{target.expert!=null?target.expert.lastName:null}")
    String getExpertLastName();

    @Value("#{@ValueHelper.getCustomDiamonds(target.id)}")
    List<CustomDiamond> getDiamonds();
}
