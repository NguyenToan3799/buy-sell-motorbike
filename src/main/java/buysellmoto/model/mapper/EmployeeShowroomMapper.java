package buysellmoto.model.mapper;

import buysellmoto.model.dto.EmployeeShowroomDto;
import buysellmoto.model.entity.EmployeeShowroomEntity;
import buysellmoto.model.filter.EmployeeShowroomFilter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface EmployeeShowroomMapper {

    EmployeeShowroomMapper INSTANCE = Mappers.getMapper(EmployeeShowroomMapper.class);

    @Named("toEntity")
    EmployeeShowroomEntity toEntity(EmployeeShowroomDto dto);

    @Named("toDto")
    EmployeeShowroomDto toDto(EmployeeShowroomEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<EmployeeShowroomEntity> toEntity(List<EmployeeShowroomDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<EmployeeShowroomDto> toDto(List<EmployeeShowroomEntity> entities);

    @Named("filterToDto")
    EmployeeShowroomDto filterToDto(EmployeeShowroomFilter filter);

}
