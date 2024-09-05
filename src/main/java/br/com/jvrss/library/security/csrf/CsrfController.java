package br.com.jvrss.library.security.csrf;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CsrfController {

    @GetMapping("/csrf-token")
    public ResponseEntity<CsrfToken> getCsrfToken(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return ResponseEntity.ok(csrfToken);
    }
}