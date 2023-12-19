package buysellmoto.model.mapper;

import buysellmoto.model.dto.BuyRequestDto;
import buysellmoto.model.entity.BuyRequestEntity;
import buysellmoto.model.filter.BuyRequestFilter;
import buysellmoto.model.vo.BuyRequestVo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface BuyRequestMapper {

    BuyRequestMapper INSTANCE = Mappers.getMapper(BuyRequestMapper.class);

    @Named("toEntity")
    BuyRequestEntity toEntity(BuyRequestDto dto);

    @Named("toDto")
    BuyRequestDto toDto(BuyRequestEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<BuyRequestEntity> toEntity(List<BuyRequestDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<BuyRequestDto> toDto(List<BuyRequestEntity> entities);

    @Named("dtoToVo")
    BuyRequestVo dtoToVo(BuyRequestDto dto);

    @Named("entityToVo")
    BuyRequestVo entityToVo(BuyRequestEntity entity);

    @Named("dtosToVos")
    @IterableMapping(qualifiedByName = "dtoToVo")
    List<BuyRequestVo> dtoToVo(List<BuyRequestDto> dtos);

    @Named("entitiesToVos")
    @IterableMapping(qualifiedByName = "entityToVo")
    List<BuyRequestVo> entityToVo(List<BuyRequestEntity> entities);

    @Named("voToEntity")
    BuyRequestEntity voToEntity(BuyRequestVo vos);

    @Named("vosToEntities")
    @IterableMapping(qualifiedByName = "voToEntity")
    List<BuyRequestEntity> voToEntity(List<BuyRequestVo> vos);
}
