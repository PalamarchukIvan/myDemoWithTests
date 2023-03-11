package com.example.demowithtests.util.config.MapStruct;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.AddressDto;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeForPatchDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Set;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeReadDto employeeToEmployeeReadDto(Employee employee);
    List<EmployeeReadDto> employeeToEmployeeReadDto(List<Employee> employeeList);
    static Page<EmployeeReadDto> employeeToEmployeeReadDto(Page<Employee> employeePage){
        return employeePage.map(INSTANCE::employeeToEmployeeReadDto);
    }
    EmployeeDto employeeToEmployeeDto (Employee employee);
    EmployeeForPatchDto employeeToEmployeeForPatchDto(Employee employee);
    Employee employeeDtoToEmployee(EmployeeDto employeeDto);
    Employee employeeForPatchDtoToEmployee(EmployeeForPatchDto employee);
    Set<AddressDto> addressToAddressDto(Set<Address> address);
    Address addressDtoToAddress(AddressDto address);
}
