package buysellmoto.model.mapper;

import buysellmoto.model.dto.MotorbikeDto;
import buysellmoto.model.entity.MotorbikeEntity;
import buysellmoto.model.filter.MotorbikeFilter;
import buysellmoto.model.vo.MotorbikeVo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface MotorbikeMapper {

    MotorbikeMapper INSTANCE = Mappers.getMapper(MotorbikeMapper.class);

    @Named("toEntity")
    MotorbikeEntity toEntity(MotorbikeDto dto);

    @Named("toDto")
    MotorbikeDto toDto(MotorbikeEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<MotorbikeEntity> toEntity(List<MotorbikeDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<MotorbikeDto> toDto(List<MotorbikeEntity> entities);

    @Named("voToDto")
    MotorbikeDto voToDto(MotorbikeVo vo);

    @Named("dtoToVo")
    MotorbikeVo dtoToVo(MotorbikeDto dto);

    @Named("voToEntity")
    MotorbikeEntity voToEntity(MotorbikeVo dto);


}
