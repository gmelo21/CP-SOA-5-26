package br.com.fiap3esph.autoescola3esph.config.documentation;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracaoDoSwagger {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(
                                "bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Informe o token obtido em POST /login")
                        )
                )
                .info(new Info()
                        .title("Auto Escola 3ESPH")
                        .version("v5")
                        .description("""
                                API ReST para gerenciamento de instruções da auto-escola 3ESPH.

                                Recursos: instrutores, alunos, usuários e instruções
                                (agendamento e cancelamento), com autenticação via token JWT,
                                CORS configurado e consumo do WebService externo ViaCEP
                                para completar os endereços a partir do CEP.
                                """)
                        .contact(new Contact()
                                .name("Auto Escola 3ESPH")
                                .email("autoescola3esph@email.com.br")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")
                        )
                );
    }
}
