package buysellmoto.model.mapper;

import buysellmoto.model.dto.RoleDto;
import buysellmoto.model.entity.RoleEntity;
import buysellmoto.model.filter.RoleFilter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Named("toEntity")
    RoleEntity toEntity(RoleDto dto);

    @Named("toDto")
    RoleDto toDto(RoleEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<RoleEntity> toEntity(List<RoleDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<RoleDto> toDto(List<RoleEntity> entities);

}
