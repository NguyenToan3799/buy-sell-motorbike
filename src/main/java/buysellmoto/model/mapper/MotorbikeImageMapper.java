package buysellmoto.model.mapper;

import buysellmoto.model.dto.MotorbikeImageDto;
import buysellmoto.model.entity.MotorbikeImageEntity;
import buysellmoto.model.filter.MotorbikeImageFilter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface MotorbikeImageMapper {

    MotorbikeImageMapper INSTANCE = Mappers.getMapper(MotorbikeImageMapper.class);

    @Named("toEntity")
    MotorbikeImageEntity toEntity(MotorbikeImageDto dto);

    @Named("toDto")
    MotorbikeImageDto toDto(MotorbikeImageEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<MotorbikeImageEntity> toEntity(List<MotorbikeImageDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<MotorbikeImageDto> toDto(List<MotorbikeImageEntity> entities);

}
