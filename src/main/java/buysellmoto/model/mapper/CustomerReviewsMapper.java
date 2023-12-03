package buysellmoto.model.mapper;

import buysellmoto.model.dto.CustomerReviewsDto;
import buysellmoto.model.entity.CustomerReviewsEntity;
import buysellmoto.model.filter.CustomerReviewsFilter;
import buysellmoto.model.vo.CustomerReviewsVo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface CustomerReviewsMapper {

    CustomerReviewsMapper INSTANCE = Mappers.getMapper(CustomerReviewsMapper.class);

    @Named("toEntity")
    CustomerReviewsEntity toEntity(CustomerReviewsDto dto);

    @Named("toDto")
    CustomerReviewsDto toDto(CustomerReviewsEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<CustomerReviewsEntity> toEntity(List<CustomerReviewsDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<CustomerReviewsDto> toDto(List<CustomerReviewsEntity> entities);

    @Named("entityToVo")
    CustomerReviewsVo entityToVo(CustomerReviewsEntity entity);

    @Named("entitiesToVos")
    @IterableMapping(qualifiedByName = "entityToVo")
    List<CustomerReviewsVo> entityToVo(List<CustomerReviewsEntity> entities);

}
