//package uz.rootec.appjeweleryserver.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import uz.rootec.appjeweleryserver.entity.template.AbstractEntity;
//
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToOne;
//
//@EqualsAndHashCode(callSuper = true)
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//public class Expert extends AbstractEntity {
//    private String info;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private User creator;
//}
