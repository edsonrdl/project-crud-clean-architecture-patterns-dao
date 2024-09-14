package com.crudpatternsdao.crudpatternsdao.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.crudpatternsdao.crudpatternsdao.application.usecase.clienteusecase.IClientMapperDTO;
import com.crudpatternsdao.crudpatternsdao.application.usecase.clienteusecase.IClientUseCase;
import com.crudpatternsdao.crudpatternsdao.application.usecase.clienteusecase.ClientUseCaseImpl;
import com.crudpatternsdao.crudpatternsdao.controllers.ClientController;
import com.crudpatternsdao.crudpatternsdao.infrastructure.dao.ClientDAOImpl;
import com.crudpatternsdao.crudpatternsdao.infrastructure.dao.IClientDAO;
import com.crudpatternsdao.crudpatternsdao.infrastructure.mapper.ClientMapperModel;
import com.crudpatternsdao.crudpatternsdao.infrastructure.mapper.IClientMapperModel;

@Configuration
public class CustomBeansConfig {

    @Bean
    public IClientDAO clientDAO() {
        return new ClientDAOImpl();
    }

    @Bean
    public IClientMapperModel iClientMapperModel() {
        return new ClientMapperModel();
    }

    @Bean
    public IClientUseCase iClientUseCase(IClientDAO clientDAO, IClientMapperModel clientMapperModel) {
        return new ClientUseCaseImpl(clientDAO, clientMapperModel);
    }

    @Bean
    public ClientController clientController(IClientMapperDTO iClientMapperDTO, IClientMapperModel iClientMapperModel,
            IClientUseCase iClientUseCase) {
        return new ClientController(iClientMapperDTO, iClientMapperModel, iClientUseCase);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);

        template.setValueSerializer(serializer);
        template.setKeySerializer(new StringRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }

}
