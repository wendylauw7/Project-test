package com.project.test.assembler;


import com.project.test.model.dto.UserDto;
import com.project.test.model.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserAssembler {
	@Autowired
    private ModelMapper modelMapper;
	
	public UserDto convertToDto(UserEntity entity){
        if (entity == null){
            return null;
        }

        UserDto dto = modelMapper.map(entity, UserDto.class);

        return dto;
    }

    public List<UserDto> convertToDtos(List<UserEntity> userEntity){
        return userEntity.stream()
                .map(entity -> convertToDto(entity))
                .collect(Collectors.toList());
    }

    public UserEntity convertToEntity(UserDto dto){
    	UserEntity entity = modelMapper.map(dto, UserEntity.class);
        return entity;
    }
}
