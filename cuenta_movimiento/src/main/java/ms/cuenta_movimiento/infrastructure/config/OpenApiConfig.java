package ms.cuenta_movimiento.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cuenta-Movimiento Microservice API")
                        .version("1.0.0")
                        .description("API para gestión de cuentas, movimientos y reportes en el sistema bancario DEVSU")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("desarrollo@devsu.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}