package uz.rootec.appjeweleryserver.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReqSignUp {
    private String phoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
}
