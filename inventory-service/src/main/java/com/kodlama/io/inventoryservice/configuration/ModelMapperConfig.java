package com.kodlama.io.inventoryservice.configuration;

import com.kodlama.io.commonpackage.utils.mappers.ModelMapperManager;
import com.kodlama.io.commonpackage.utils.mappers.ModelMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ModelMapperService getModelMapperService(ModelMapper mapper) {
        return new ModelMapperManager(mapper);
    }
}
