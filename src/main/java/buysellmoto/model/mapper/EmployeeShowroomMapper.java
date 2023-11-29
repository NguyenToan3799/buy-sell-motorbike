package buysellmoto.model.mapper;

import buysellmoto.model.dto.EmployeeShowroomDto;
import buysellmoto.model.entity.EmployeeShowroomEntity;
import buysellmoto.model.filter.EmployeeShowroomFilter;
import buysellmoto.model.vo.EmployeeShowroomVo;
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

    @Named("dtoToVo")
    EmployeeShowroomVo dtoToVo(EmployeeShowroomDto dto);

    @Named("entityToVo")
    EmployeeShowroomVo entityToVo(EmployeeShowroomEntity entity);

    @Named("dtosToVos")
    @IterableMapping(qualifiedByName = "dtoToVo")
    List<EmployeeShowroomVo> dtoToVo(List<EmployeeShowroomDto> dtos);

    @Named("entitiesToVos")
    @IterableMapping(qualifiedByName = "entityToVo")
    List<EmployeeShowroomVo> entityToVo(List<EmployeeShowroomEntity> entities);

}
