package uz.rootec.appjeweleryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.rootec.appjeweleryserver.entity.template.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Created by Sherlock on 14.07.2021.
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Characteristic extends AbstractEntity {
    private String name;

    private String valueOne;

    private String valueTwo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Diamond diamond;
}
