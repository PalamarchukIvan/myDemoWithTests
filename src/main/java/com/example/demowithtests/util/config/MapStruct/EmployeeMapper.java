package com.example.demowithtests.util.config.MapStruct;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Photo;
import com.example.demowithtests.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
    default List<EmployeeReadDto> employeeToEmployeeReadDto(List<Employee> employeeList) {
        if ( employeeList == null ) {
            return null;
        }

        List<EmployeeReadDto> list = new ArrayList<EmployeeReadDto>( employeeList.size() );
        for ( Employee employee : employeeList ) {
            list.add( employeeToEmployeeReadDto( employee ) );
        }

        return list;
    }
    default EmployeeReadDto employeeToEmployeeReadDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeReadDto employeeReadDto = new EmployeeReadDto();

        employeeReadDto.name = employee.getName();
        employeeReadDto.country = employee.getCountry();
        employeeReadDto.email = employee.getEmail();
        employeeReadDto.phone = employee.getPhone();
        employeeReadDto.addresses = addressToAddressDto( employee.getAddresses() );
        employeeReadDto.photos =  photoToPhotoDto2 (employee.getPhotos().get(employee.getPhotos().size() - 1));
        employeeReadDto.gender = employee.getGender();

        return employeeReadDto;
    }

    default PhotoDto photoToPhotoDto2(Photo photo) {
        if ( photo == null ) {
            return null;
        }

        PhotoDto photoDto = new PhotoDto();

        photoDto.uploadDate = photo.getUploadDate();
        photoDto.description = photo.getDescription();
        photoDto.cameraType = photo.getCameraType();
        photoDto.photoUrl = photo.getPhotoUrl();
        photoDto.isPrivate = photo.getIsPrivate();

        return photoDto;
    }

    default Page<EmployeeReadDto> employeeToEmployeeReadDto(Page<Employee> employeePage){
        return employeePage.map(INSTANCE::employeeToEmployeeReadDto);
    }
    Employee employeeDtoToEmployee(EmployeeDto employeeDto);
    Employee employeeForPatchDtoToEmployee(EmployeeForPatchDto employee);
    Set<AddressDto> addressToAddressDto(Set<Address> address);
    Address addressDtoToAddress(AddressDto address);
    PhotoDto photoToPhotoDto(Photo photo);
    Photo photoDtoTpPhoto(PhotoDto photo);
}
