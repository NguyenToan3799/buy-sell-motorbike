package buysellmoto.model.mapper;

import buysellmoto.model.dto.CustomerDto;
import buysellmoto.model.entity.CustomerEntity;
import buysellmoto.model.filter.CustomerFilter;
import buysellmoto.model.vo.CustomerVo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Named("toEntity")
    CustomerEntity toEntity(CustomerDto dto);

    @Named("toDto")
    CustomerDto toDto(CustomerEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<CustomerEntity> toEntity(List<CustomerDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<CustomerDto> toDto(List<CustomerEntity> entities);

    @Named("dtoToVo")
    CustomerVo dtoToVo(CustomerDto dto);

    @Named("entityToVo")
    CustomerVo entityToVo(CustomerEntity entity);

    @Named("dtosToVos")
    @IterableMapping(qualifiedByName = "dtoToVo")
    List<CustomerVo> dtoToVo(List<CustomerDto> dtos);

    @Named("entitiesToVos")
    @IterableMapping(qualifiedByName = "entityToVo")
    List<CustomerVo> entityToVo(List<CustomerEntity> entities);

}
