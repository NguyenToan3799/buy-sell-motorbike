package buysellmoto.model.mapper;

import buysellmoto.model.dto.NotificationDto;
import buysellmoto.model.entity.NotificationEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Named("toEntity")
    NotificationEntity toEntity(NotificationDto dto);

    @Named("toDto")
    NotificationDto toDto(NotificationEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<NotificationEntity> toEntity(List<NotificationDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<NotificationDto> toDto(List<NotificationEntity> entities);

}
