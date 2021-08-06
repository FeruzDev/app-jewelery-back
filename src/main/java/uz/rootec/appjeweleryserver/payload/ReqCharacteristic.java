package uz.rootec.appjeweleryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqCharacteristic {
    private UUID id;

    private String name;

    private String valueOne;

    private String valueTwo;
}
