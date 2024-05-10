package ru.ds.edu.medvedew.internship.utils.mappers;

import org.junit.jupiter.api.Test;
import ru.ds.edu.medvedew.internship.dto.InternshipDto;
import ru.ds.edu.medvedew.internship.models.Internship;
import ru.ds.edu.medvedew.internship.models.statuses.InternshipStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InternshipMapperTest {
    private InternshipMapper internshipMapper = InternshipMapper.MAPPER;

    @Test
    void toInternship() {
        InternshipDto internshipDto = new InternshipDto();
        internshipDto.setId(1);
        internshipDto.setName("name");
        internshipDto.setStatus(InternshipStatus.IN_PROGRESS);

        Internship internship = internshipMapper.toInternship(internshipDto);

        assertEquals(1, internship.getId());
        assertEquals("name", internship.getName());
        assertEquals(InternshipStatus.IN_PROGRESS, internship.getStatus());
    }

    @Test
    void toInternshipDto() {
        Internship internship = new Internship();
        internship.setId(1);
        internship.setName("name");
        internship.setStatus(InternshipStatus.IN_PROGRESS);

        InternshipDto internshipDto = internshipMapper.toInternshipDto(internship);

        assertEquals(1, internshipDto.getId());
        assertEquals("name", internshipDto.getName());
        assertEquals(InternshipStatus.IN_PROGRESS, internshipDto.getStatus());
    }
}