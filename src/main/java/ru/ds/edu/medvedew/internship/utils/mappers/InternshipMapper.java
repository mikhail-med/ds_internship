package ru.ds.edu.medvedew.internship.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ds.edu.medvedew.internship.dto.InternshipDto;
import ru.ds.edu.medvedew.internship.models.Internship;

/**
 * Mapper для стажировки и dto
 */
@Mapper
public interface InternshipMapper {
    InternshipMapper MAPPER = Mappers.getMapper(InternshipMapper.class);

    Internship toInternship(InternshipDto internshipDto);

    InternshipDto toInternshipDto(Internship internship);
}
