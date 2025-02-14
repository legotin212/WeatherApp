package com.mapper;

import com.dto.LocationDto;
import com.entity.Location;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "lat", target = "latitude")
    @Mapping(source = "lon", target = "longitude")
    @Mapping(target = "user", ignore = true)

    Location locationDtoToLocation(LocationDto locationDto);

    @InheritInverseConfiguration
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "state", ignore = true)
    LocationDto locationToLocationDto(Location location);

}
