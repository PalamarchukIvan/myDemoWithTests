package com.example.demowithtests.util.config.MapStruct;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Photo;
import com.example.demowithtests.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
    List<EmployeeReadDto> employeeToEmployeeReadDto(List<Employee> employeeList);
    EmployeeReadDto employeeToEmployeeReadDto(Employee employee) ;

    default PhotoReadDto photoToPhotoDto(List<Photo> photos) {
        if ( photos == null || photos.size() == 0) {
            return null;
        }

        PhotoReadDto photoDto = new PhotoReadDto();
        Photo photo = photos.get(photos.size() - 1);

        photoDto.uploadDate = photo.getUploadDate();
        photoDto.url = photo.getUrl();

        return photoDto;
    }

    default Page<EmployeeReadDto> employeeToEmployeeReadDto(Page<Employee> employeePage){
        return employeePage.map(INSTANCE::employeeToEmployeeReadDto);
    }

    Employee employeeDtoToEmployee(EmployeeDto employeeDto);
    Employee employeeForPatchDtoToEmployee(EmployeeForPatchDto employee);
    Set<AddressDto> addressToAddressDto(Set<Address> address);
    Address addressDtoToAddress(AddressDto address);
    Photo photoDtoTpPhoto(PhotoReadDto photo);
}
