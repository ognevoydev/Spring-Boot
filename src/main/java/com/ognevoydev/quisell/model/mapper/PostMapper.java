package com.ognevoydev.quisell.model.mapper;

import com.ognevoydev.quisell.model.entity.Post;
import com.ognevoydev.quisell.model.dto.PostUpdateDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PostMapper {
    void updatePost(@MappingTarget Post post, PostUpdateDTO postUpdateDTO);
}
