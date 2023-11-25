package buysellmoto.model.mapper;

import buysellmoto.model.dto.PurchaseAppointmentDto;
import buysellmoto.model.entity.PurchaseAppointmentEntity;
import buysellmoto.model.filter.PurchaseAppointmentFilter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface PurchaseAppointmentMapper {

    PurchaseAppointmentMapper INSTANCE = Mappers.getMapper(PurchaseAppointmentMapper.class);

    @Named("toEntity")
    PurchaseAppointmentEntity toEntity(PurchaseAppointmentDto dto);

    @Named("toDto")
    PurchaseAppointmentDto toDto(PurchaseAppointmentEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<PurchaseAppointmentEntity> toEntity(List<PurchaseAppointmentDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<PurchaseAppointmentDto> toDto(List<PurchaseAppointmentEntity> entities);

}
