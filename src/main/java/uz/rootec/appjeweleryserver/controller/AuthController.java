package uz.rootec.appjeweleryserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.rootec.appjeweleryserver.entity.User;
import uz.rootec.appjeweleryserver.payload.ApiResponse;
import uz.rootec.appjeweleryserver.payload.JwtResponse;
import uz.rootec.appjeweleryserver.payload.ReqSignIn;
import uz.rootec.appjeweleryserver.payload.ReqSignUp;
import uz.rootec.appjeweleryserver.repository.UserRepository;
import uz.rootec.appjeweleryserver.security.AuthService;
import uz.rootec.appjeweleryserver.security.JwtTokenProvider;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userepository;

    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody ReqSignIn reqSignIn) {
        ApiResponse response = authService.login(reqSignIn);
        if (response.isSuccess()) {
            return getApiToken(reqSignIn.getPhoneNumber(), reqSignIn.getPassword());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @PostMapping("/register")
    public HttpEntity<?> register(@Valid @RequestBody ReqSignUp reqSignUp) {
        ApiResponse response = authService.register(reqSignUp);
        if (response.isSuccess()) {

            return getApiToken(reqSignUp.getPhoneNumber(), reqSignUp.getPassword());

        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    private HttpEntity<?> getApiToken(String phoneNumber, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(phoneNumber, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        User user = userepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UsernameNotFoundException("Telefon raqam topilmadi: " + phoneNumber));

        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
