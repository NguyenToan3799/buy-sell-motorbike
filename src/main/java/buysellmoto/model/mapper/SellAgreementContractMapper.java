package buysellmoto.model.mapper;

import buysellmoto.model.dto.SellAgreementContractDto;
import buysellmoto.model.entity.SellAgreementContractEntity;
import buysellmoto.model.filter.SellAgreementContractFilter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface SellAgreementContractMapper {

    SellAgreementContractMapper INSTANCE = Mappers.getMapper(SellAgreementContractMapper.class);

    @Named("toEntity")
    SellAgreementContractEntity toEntity(SellAgreementContractDto dto);

    @Named("toDto")
    SellAgreementContractDto toDto(SellAgreementContractEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<SellAgreementContractEntity> toEntity(List<SellAgreementContractDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<SellAgreementContractDto> toDto(List<SellAgreementContractEntity> entities);

    @Named("filterToDto")
    SellAgreementContractDto filterToDto(SellAgreementContractFilter filter);

}
