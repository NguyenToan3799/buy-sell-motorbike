package buysellmoto.model.mapper;

import buysellmoto.model.dto.CheckedSellRequestDto;
import buysellmoto.model.entity.CheckedSellRequestEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface CheckedSellRequestMapper {

    CheckedSellRequestMapper INSTANCE = Mappers.getMapper(CheckedSellRequestMapper.class);

    @Named("toEntity")
    CheckedSellRequestEntity toEntity(CheckedSellRequestDto dto);

    @Named("toDto")
    CheckedSellRequestDto toDto(CheckedSellRequestEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<CheckedSellRequestEntity> toEntity(List<CheckedSellRequestDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<CheckedSellRequestDto> toDto(List<CheckedSellRequestEntity> entities);

}
