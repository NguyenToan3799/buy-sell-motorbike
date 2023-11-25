package buysellmoto.model.mapper;

import buysellmoto.model.dto.TransactionDto;
import buysellmoto.model.entity.TransactionEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Named("toEntity")
    TransactionEntity toEntity(TransactionDto dto);

    @Named("toDto")
    TransactionDto toDto(TransactionEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<TransactionEntity> toEntity(List<TransactionDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<TransactionDto> toDto(List<TransactionEntity> entities);

}
