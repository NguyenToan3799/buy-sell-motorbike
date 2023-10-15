package buysellmoto.model.mapper;

import buysellmoto.model.dto.MotoBrandDto;
import buysellmoto.model.entity.MotoBrandEntity;
import buysellmoto.model.filter.MotoBrandFilter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface MotoBrandMapper {

    MotoBrandMapper INSTANCE = Mappers.getMapper(MotoBrandMapper.class);

    @Named("toEntity")
    MotoBrandEntity toEntity(MotoBrandDto dto);

    @Named("toDto")
    MotoBrandDto toDto(MotoBrandEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<MotoBrandEntity> toEntity(List<MotoBrandDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<MotoBrandDto> toDto(List<MotoBrandEntity> entities);

    @Named("filterToDto")
    MotoBrandDto filterToDto(MotoBrandFilter filter);

}
