package uk.gov.hmcts.case_management;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfiguration {
  @Bean
  public OpenAPI openAPI() {
    Server server = new Server();
    server.setUrl("http://localhost:8080");

    Contact contact = new Contact();

    contact.setEmail("ahmedysatti@gmail.com");
    contact.setName("Ahmed Mohamed");

    Info info =
        new Info()
            .contact(contact)
            .title("Task management API for caseworkers")
            .description(
                "This API allows creating, updating, deleting & retriving tasks for caseworkers at"
                    + " HMCTS to manage their tasks.");

    return new OpenAPI().info(info).servers(List.of(server));
  }
}
