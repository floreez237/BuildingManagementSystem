package buildingProject;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"buildingProject.repositories"})
public class ServiceConfiguration {
}
