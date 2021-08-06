package uz.rootec.appjeweleryserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.rootec.appjeweleryserver.entity.User;
import uz.rootec.appjeweleryserver.entity.enums.RoleName;
import uz.rootec.appjeweleryserver.payload.ApiResponse;
import uz.rootec.appjeweleryserver.payload.ReqSignIn;
import uz.rootec.appjeweleryserver.payload.ReqSignUp;
import uz.rootec.appjeweleryserver.repository.RoleRepository;
import uz.rootec.appjeweleryserver.repository.UserRepository;


import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UsernameNotFoundException("Bunday telefon raqam topilmadi: " + phoneNumber));
    }

    public UserDetails loadUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("Bunday id topilmadi: " + userId));
    }


    public ApiResponse register(ReqSignUp reqSignUp) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(reqSignUp.getPhoneNumber());

        if (optionalUser.isPresent()) {
            return new ApiResponse(false, "Bunday foydalanuvchi ro'yxatdan o'tgan!");
        } else {
            userRepository.save(
                    new User(
                            reqSignUp.getPhoneNumber(),
                            passwordEncoder.encode(reqSignUp.getPassword()),
                            reqSignUp.getLastName(),
                            reqSignUp.getFirstName(),
                            roleRepository.findAllByName(RoleName.ROLE_WORKER),
                            reqSignUp.getEmail()
                    ));
            return new ApiResponse(true, "Tizimdan muvaffaqiyatli ro'yxatdan o'tdingiz.");
        }
    }

    public ApiResponse login(ReqSignIn reqSignIn) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(reqSignIn.getPhoneNumber());

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();
            if (passwordEncoder.matches(reqSignIn.getPassword(), user.getPassword())) {
                return new ApiResponse(true, "Ma'lumotlar to'g'ri kiritildi!");
            } else {
                return new ApiResponse(false, "Parol xato kiritildi");
            }
        } else {
            return new ApiResponse(false, "Bunday foydalanuvchi ro'yhatdan o'tmagan!");
        }
    }
}
