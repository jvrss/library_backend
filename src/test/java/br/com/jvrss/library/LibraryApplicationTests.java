package br.com.jvrss.library;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LibraryApplicationTests {

    @Test
    void contextLoads() {
        LibraryApplication application = new LibraryApplication();
        assertNotNull(application);
    }
}