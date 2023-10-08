package buysellmoto.model.mapper;

import buysellmoto.model.dto.UserDto;
import buysellmoto.model.entity.UserEntity;
import buysellmoto.model.filter.UserFilter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Named("toEntity")
    UserEntity toEntity(UserDto dto);

    @Named("toDto")
    UserDto toDto(UserEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<UserEntity> toEntity(List<UserDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<UserDto> toDto(List<UserEntity> entities);

    @Named("filterToDto")
    UserDto filterToDto(final UserFilter filter);

}
