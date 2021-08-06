package uz.rootec.appjeweleryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.rootec.appjeweleryserver.entity.template.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Attachment")
public class Attachment extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String originalName;

    private long size;

    @Column(nullable = false)
    private String contentType;
}


