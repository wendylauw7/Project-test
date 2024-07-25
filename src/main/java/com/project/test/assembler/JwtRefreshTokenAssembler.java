package com.project.test.assembler;



import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.test.model.dto.JwtRefreshToken;
import com.project.test.model.entity.JwtRefreshTokenEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtRefreshTokenAssembler {

    private ObjectMapper modelMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public JwtRefreshToken convertToDto(JwtRefreshTokenEntity jwtRefreshTokenEntity){
        if (jwtRefreshTokenEntity == null){
            return null;
        }
        JwtRefreshToken jwtRefreshToken = modelMapper.convertValue(jwtRefreshTokenEntity, JwtRefreshToken.class);

        return jwtRefreshToken;
    }

    public List<JwtRefreshToken> convertToDtos(List<JwtRefreshTokenEntity> jwtRefreshTokenEntities){
        return jwtRefreshTokenEntities.stream()
                .map(entity -> convertToDto(entity))
                .collect(Collectors.toList());
    }

    public JwtRefreshTokenEntity convertToEntity(JwtRefreshToken jwtRefreshToken){
        JwtRefreshTokenEntity jwtRefreshTokenEntity = modelMapper.convertValue(jwtRefreshToken, JwtRefreshTokenEntity.class);
        return jwtRefreshTokenEntity;
    }

}
