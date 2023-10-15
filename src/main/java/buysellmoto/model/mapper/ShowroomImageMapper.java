package buysellmoto.model.mapper;

import buysellmoto.model.dto.ShowroomImageDto;
import buysellmoto.model.entity.ShowroomImageEntity;
import buysellmoto.model.filter.ShowroomImageFilter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface ShowroomImageMapper {

    ShowroomImageMapper INSTANCE = Mappers.getMapper(ShowroomImageMapper.class);

    @Named("toEntity")
    ShowroomImageEntity toEntity(ShowroomImageDto dto);

    @Named("toDto")
    ShowroomImageDto toDto(ShowroomImageEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<ShowroomImageEntity> toEntity(List<ShowroomImageDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<ShowroomImageDto> toDto(List<ShowroomImageEntity> entities);

    @Named("filterToDto")
    ShowroomImageDto filterToDto(ShowroomImageFilter filter);

}
