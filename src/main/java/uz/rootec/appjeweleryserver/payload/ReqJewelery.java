package uz.rootec.appjeweleryserver.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqJewelery {
    private UUID id;

    private UUID photo;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Tashkent")
    private Timestamp date;

    private String name;

    private Double totalWeight;

    private String metal;

    private Double hallMark;

    private List<ReqDiamond> diamonds;
}
