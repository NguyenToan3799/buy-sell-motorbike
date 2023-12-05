package buysellmoto.model.mapper;

import buysellmoto.model.dto.CommentReviewsDto;
import buysellmoto.model.entity.CommentReviewsEntity;
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
public interface CommentReviewsMapper {

    CommentReviewsMapper INSTANCE = Mappers.getMapper(CommentReviewsMapper.class);

    @Named("toEntity")
    CommentReviewsEntity toEntity(CommentReviewsDto dto);

    @Named("toDto")
    CommentReviewsDto toDto(CommentReviewsEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<CommentReviewsEntity> toEntity(List<CommentReviewsDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<CommentReviewsDto> toDto(List<CommentReviewsEntity> entities);

}
