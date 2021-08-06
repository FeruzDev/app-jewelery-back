package uz.rootec.appjeweleryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqDiamond {
    private UUID id;

    private Double diamond;

    private List<ReqCharacteristic> characteristics;
}
