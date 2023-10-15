package buysellmoto.model.mapper;

import buysellmoto.model.dto.MotoTypeDto;
import buysellmoto.model.entity.MotoTypeEntity;
import buysellmoto.model.filter.MotoTypeFilter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface MotoTypeMapper {

    MotoTypeMapper INSTANCE = Mappers.getMapper(MotoTypeMapper.class);

    @Named("toEntity")
    MotoTypeEntity toEntity(MotoTypeDto dto);

    @Named("toDto")
    MotoTypeDto toDto(MotoTypeEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<MotoTypeEntity> toEntity(List<MotoTypeDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<MotoTypeDto> toDto(List<MotoTypeEntity> entities);

    @Named("filterToDto")
    MotoTypeDto filterToDto(MotoTypeFilter filter);

}
