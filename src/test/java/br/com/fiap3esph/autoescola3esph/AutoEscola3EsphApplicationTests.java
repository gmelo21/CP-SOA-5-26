package br.com.fiap3esph.autoescola3esph;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AutoEscola3EsphApplicationTests {

    @Test
    @DisplayName("O contexto da aplicação sobe com todos os beans configurados.")
    void contextLoads() {
    }
}
