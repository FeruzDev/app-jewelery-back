package uz.rootec.appjeweleryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.rootec.appjeweleryserver.entity.template.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Diamond extends AbstractEntity {
    private Double diamond;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private Attachment photo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Jewelery jewelery;
}
