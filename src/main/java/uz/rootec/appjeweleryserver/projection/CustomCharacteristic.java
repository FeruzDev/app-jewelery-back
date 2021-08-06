package uz.rootec.appjeweleryserver.projection;

import java.util.UUID;

/**
 * Created by Sherlock on 14.07.2021.
 */
public interface CustomCharacteristic {
    UUID getId();

    String getName();

    String getValueOne();

    String getValueTwo();
}
