package buysellmoto.model.mapper;

import buysellmoto.model.dto.ShowroomDto;
import buysellmoto.model.entity.ShowroomEntity;
import buysellmoto.model.filter.ShowroomFilter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface ShowroomMapper {

    ShowroomMapper INSTANCE = Mappers.getMapper(ShowroomMapper.class);

    @Named("toEntity")
    ShowroomEntity toEntity(ShowroomDto dto);

    @Named("toDto")
    ShowroomDto toDto(ShowroomEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<ShowroomEntity> toEntity(List<ShowroomDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<ShowroomDto> toDto(List<ShowroomEntity> entities);

    @Named("filterToDto")
    ShowroomDto filterToDto(ShowroomFilter filter);

}