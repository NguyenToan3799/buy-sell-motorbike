package buysellmoto.model.mapper;

import buysellmoto.model.dto.CheckingAppointmentDto;
import buysellmoto.model.entity.CheckingAppointmentEntity;
import buysellmoto.model.filter.CheckingAppointmentFilter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface CheckingAppointmentMapper {

    CheckingAppointmentMapper INSTANCE = Mappers.getMapper(CheckingAppointmentMapper.class);

    @Named("toEntity")
    CheckingAppointmentEntity toEntity(CheckingAppointmentDto dto);

    @Named("toDto")
    CheckingAppointmentDto toDto(CheckingAppointmentEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<CheckingAppointmentEntity> toEntity(List<CheckingAppointmentDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<CheckingAppointmentDto> toDto(List<CheckingAppointmentEntity> entities);

}
