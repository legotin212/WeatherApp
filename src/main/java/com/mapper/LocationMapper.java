package com.mapper;

import com.dto.LocationDto;
import com.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(source = "latitude", target = "lat")
    @Mapping(source = "longitude", target = "lon")
    LocationDto toDto(Location location);

    @Mapping(source = "lat", target = "latitude")
    @Mapping(source = "lon", target = "longitude")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Location locationDtoToLocation(LocationDto dto);
}

