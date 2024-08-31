package br.com.jvrss.library.security.csrf;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling CSRF token requests.
 */
@RestController
public class CsrfController {

    /**
     * Retrieves the CSRF token.
     *
     * @param csrfToken the CSRF token
     * @return the CSRF token wrapped in a ResponseEntity
     */
    @GetMapping("/csrf-token")
    public ResponseEntity<CsrfToken> getCsrfToken(CsrfToken csrfToken) {
        return ResponseEntity.ok(csrfToken);
    }

}