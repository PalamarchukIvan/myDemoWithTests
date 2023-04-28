package com.example.demowithtests.util.MapStruct;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Photo;
import com.example.demowithtests.dto.AddressDto.AddressDto;
import com.example.demowithtests.dto.EmployeeDto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeDto.EmployeeForPatchDto;
import com.example.demowithtests.dto.EmployeeDto.EmployeeReadDto;
import com.example.demowithtests.dto.PhotoDto.PhotoReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    List<EmployeeReadDto> employeeToEmployeeReadDto(List<Employee> employeeList);

    EmployeeReadDto employeeToEmployeeReadDto(Employee employee);

    default PhotoReadDto photoToPhotoReadDto(Photo photo) {
        if (photo == null || photo.getIsPrivate()) return null;

        PhotoReadDto photoDto = new PhotoReadDto();
        photoDto.uploadDate = photo.getUploadDate();
        photoDto.url = photo.getUrl();

        return photoDto;
    }

    default Page<EmployeeReadDto> employeeToEmployeeReadDto(Page<Employee> employeePage) {
        return employeePage.map(INSTANCE::employeeToEmployeeReadDto);
    }

    EmployeeDto employeeToEmployeeDto(Employee employee);

    Employee employeeDtoToEmployee(EmployeeDto employeeDto);

    Employee employeeForPatchDtoToEmployee(EmployeeForPatchDto employee);

    Set<AddressDto> addressToAddressDto(Set<Address> address);

    Address addressDtoToAddress(AddressDto address);

    Photo photoDtoTpPhoto(PhotoReadDto photo);
}
