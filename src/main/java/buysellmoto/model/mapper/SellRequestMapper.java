package buysellmoto.model.mapper;

import buysellmoto.model.dto.SellRequestDto;
import buysellmoto.model.entity.SellRequestEntity;
import buysellmoto.model.filter.SellRequestFilter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface SellRequestMapper {

    SellRequestMapper INSTANCE = Mappers.getMapper(SellRequestMapper.class);

    @Named("toEntity")
    SellRequestEntity toEntity(SellRequestDto dto);

    @Named("toDto")
    SellRequestDto toDto(SellRequestEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<SellRequestEntity> toEntity(List<SellRequestDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<SellRequestDto> toDto(List<SellRequestEntity> entities);

    @Named("filterToDto")
    SellRequestDto filterToDto(SellRequestFilter filter);

}
