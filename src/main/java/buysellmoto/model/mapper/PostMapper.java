package buysellmoto.model.mapper;

import buysellmoto.model.dto.PostDto;
import buysellmoto.model.entity.PostEntity;
import buysellmoto.model.filter.PostFilter;
import buysellmoto.model.vo.PostVo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        uses = {})
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Named("toEntity")
    PostEntity toEntity(PostDto dto);

    @Named("toDto")
    PostDto toDto(PostEntity entity);

    @Named("toEntities")
    @IterableMapping(qualifiedByName = "toEntity")
    List<PostEntity> toEntity(List<PostDto> dtos);

    @Named("toDtos")
    @IterableMapping(qualifiedByName = "toDto")
    List<PostDto> toDto(List<PostEntity> entities);


    @Named("dtoToVo")
    PostVo dtoToVo(PostDto dto);

    @Named("entityToVo")
    PostVo entityToVo(PostEntity entity);

    @Named("dtosToVos")
    @IterableMapping(qualifiedByName = "dtoToVo")
    List<PostVo> dtoToVo(List<PostDto> dtos);

    @Named("entitiesToVos")
    @IterableMapping(qualifiedByName = "entityToVo")
    List<PostVo> entityToVo(List<PostEntity> entities);

}
