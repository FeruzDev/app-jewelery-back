package uz.rootec.appjeweleryserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.rootec.appjeweleryserver.entity.template.AbstractEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Jewelery extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Attachment photo;

    @Column(nullable = false)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Tashkent")
    private Timestamp date;

    @Column(nullable = false)
    private String name;

    private Double totalWeight;

    private String metal;

    private Double hallMark;

    @Column(nullable = false)
    private String serial;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User expert;
}
