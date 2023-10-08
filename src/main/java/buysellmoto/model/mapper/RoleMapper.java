package buysellmoto.model.mapper;

import buysellmoto.model.dto.RoleDto;
import buysellmoto.model.entity.RoleEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(source = "make", target = "manufacturer")
    RoleEntity toEntity(final RoleDto dto);

    @Mapping(source = "make", target = "manufacturer")
    RoleDto toDto(final RoleEntity entity);

    @Mapping(source = "make", target = "manufacturer")
    List<RoleEntity> toEntity(final List<RoleDto> dtos);

    @Mapping(source = "make", target = "manufacturer")
    List<RoleDto> toDto(final List<RoleEntity> entities);

}
