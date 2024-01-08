package buysellmoto.model.mapper;

import buysellmoto.model.dto.RequestHistoryDto;
import buysellmoto.model.entity.RequestHistoryEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface RequestHistoryMapper {

    RequestHistoryMapper INSTANCE = Mappers.getMapper(RequestHistoryMapper.class);

    @Named("toEntity")
    RequestHistoryEntity toEntity(RequestHistoryDto dto);

    @Named("toDto")
    RequestHistoryDto toDto(RequestHistoryEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<RequestHistoryEntity> toEntity(List<RequestHistoryDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<RequestHistoryDto> toDto(List<RequestHistoryEntity> entities);


}
