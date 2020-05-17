package com.pkfproject.buildingui;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


@Component
@Data
public class Constants {
    @Value("classpath:/test.fxml")
    private Resource FXML;
}
