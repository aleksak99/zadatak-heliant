package rs.heliant.zadatak.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.LoginApi;
import org.openapitools.model.LoginRequest;
import org.openapitools.model.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestController;
import rs.heliant.zadatak.enums.ResponseCode;
import rs.heliant.zadatak.exception.BusinessValidationException;
import rs.heliant.zadatak.service.JwtService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginApiImpl extends BaseApi implements LoginApi {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        log.info("Login endpoint request: {}", loginRequest);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getKorisnickoIme(), loginRequest.getLozinka()));
        if (authentication.isAuthenticated()) {
            return createSuccessResponse(new LoginResponse().data(jwtService.generateToken(loginRequest.getKorisnickoIme())));
        }
        throw new BusinessValidationException(ResponseCode.LOGIN_FAILED);
    }
}
