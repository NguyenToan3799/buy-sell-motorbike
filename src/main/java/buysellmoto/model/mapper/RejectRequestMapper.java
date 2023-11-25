package buysellmoto.model.mapper;

import buysellmoto.model.dto.RejectRequestDto;
import buysellmoto.model.entity.RejectRequestEntity;
import buysellmoto.model.filter.RejectRequestFilter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface RejectRequestMapper {

    RejectRequestMapper INSTANCE = Mappers.getMapper(RejectRequestMapper.class);

    @Named("toEntity")
    RejectRequestEntity toEntity(RejectRequestDto dto);

    @Named("toDto")
    RejectRequestDto toDto(RejectRequestEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<RejectRequestEntity> toEntity(List<RejectRequestDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<RejectRequestDto> toDto(List<RejectRequestEntity> entities);

}
