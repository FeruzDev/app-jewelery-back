package uz.rootec.appjeweleryserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.rootec.appjeweleryserver.entity.Role;
import uz.rootec.appjeweleryserver.entity.User;
import uz.rootec.appjeweleryserver.entity.enums.RoleName;
import uz.rootec.appjeweleryserver.payload.ApiResponse;
import uz.rootec.appjeweleryserver.payload.ReqSignUp;
import uz.rootec.appjeweleryserver.payload.ResUser;
import uz.rootec.appjeweleryserver.repository.RoleRepository;
import uz.rootec.appjeweleryserver.repository.UserRepository;
import uz.rootec.appjeweleryserver.security.CurrentUser;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by Sherlock on 14.07.2021.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@CurrentUser User currentUser) {
        try {
            return ResponseEntity.ok(new ResUser(
                    currentUser.getId(),
                    currentUser.getPhoneNumber(),
                    currentUser.getLastName(),
                    currentUser.getFirstName(),
                    currentUser.getRoles(),
                    currentUser.getEmail())
            );
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }

//    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/getUsers/{type}")
    public ResponseEntity<?> getUsers(@PathVariable String type, @CurrentUser User currentUser){
        try {

//            List<Role> admin = roleRepository.findAllByName(RoleName.ROLE_ADMIN);
//
//            if (currentUser.getRoles().contains(admin.get(0))){
//                List<User> all = userRepository.findAll();
//                return ResponseEntity.ok(new ApiResponse(true, "success", all));
//            } else {
//                List<User> allByDirector = userRepository.findAllByDirector(currentUser);
//                return ResponseEntity.ok(new ApiResponse(true, "success", allByDirector));
//            }

            if (type.equals("worker")){
                List<User> allByDirector = userRepository.findAllByDirector(currentUser.getId());
                return ResponseEntity.ok(new ApiResponse(true, "success", allByDirector));
            } else if (type.equals("all")){
                List<User> all = userRepository.findAll();
                return ResponseEntity.ok(new ApiResponse(true, "success", all));
            } else {
                return ResponseEntity.ok(new ApiResponse(false, "Bad Request"));
            }
        } catch (Exception e){
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }

//    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    @PostMapping("/createWorker")
    public ResponseEntity<?> createWorker(@Valid @RequestBody ReqSignUp reqSignUp, @CurrentUser User currentUser){
        try {
            Optional<User> optionalUser = userRepository.findByPhoneNumber(reqSignUp.getPhoneNumber());

            if (optionalUser.isPresent()) {
                return ResponseEntity.ok(new ApiResponse(false, "Bunday foydalanuvchi ro'yxatdan o'tgan!"));
            } else {
                userRepository.save(
                        new User(
                                reqSignUp.getPhoneNumber(),
                                passwordEncoder.encode(reqSignUp.getPassword()),
                                reqSignUp.getLastName(),
                                reqSignUp.getFirstName(),
                                roleRepository.findAllByName(RoleName.ROLE_WORKER),
                                reqSignUp.getEmail(),
                                currentUser.getId()
                        ));
                return ResponseEntity.ok(new ApiResponse(true, "Foydalanuvchi muvaffaqqiyatli saqlandi"));
            }

        } catch (Exception e){
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }

//    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/createDirector")
    public ResponseEntity<?> createDirector(@Valid @RequestBody ReqSignUp reqSignUp, @CurrentUser User currentUser){
        try {
            Optional<User> optionalUser = userRepository.findByPhoneNumber(reqSignUp.getPhoneNumber());

            if (optionalUser.isPresent()) {
                return ResponseEntity.ok(new ApiResponse(false, "Bunday foydalanuvchi ro'yxatdan o'tgan!"));
            } else {
                userRepository.save(
                        new User(
                                reqSignUp.getPhoneNumber(),
                                passwordEncoder.encode(reqSignUp.getPassword()),
                                reqSignUp.getLastName(),
                                reqSignUp.getFirstName(),
                                roleRepository.findAllByName(RoleName.ROLE_DIRECTOR),
                                reqSignUp.getEmail()
                        ));
                return ResponseEntity.ok(new ApiResponse(true, "Foydalanuvchi muvaffaqqiyatli saqlandi"));
            }
        } catch (Exception e){
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }

}