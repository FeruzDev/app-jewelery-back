package uz.rootec.appjeweleryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import uz.rootec.appjeweleryserver.entity.Role;

import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
public class ResUser {
    private UUID id;
    private String phoneNumber;
    private String lastName;
    private String firstName;
    private List<Role> roles;
    private String email;
//    private UUID photo;
}
