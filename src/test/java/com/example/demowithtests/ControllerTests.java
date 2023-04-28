package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeDto.EmployeeForPatchDto;
import com.example.demowithtests.dto.EmployeeDto.EmployeeReadDto;
import com.example.demowithtests.service.EmployeeService.EmployeeServiceBean;
import com.example.demowithtests.service.PhotoService.PhotoServiceBean;
import com.example.demowithtests.web.EmployeeController.EmployeeControllerBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeControllerBean.class)
public class ControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private EmployeeServiceBean employeeService;
    @MockBean
    private PhotoServiceBean photoServiceBean;

    private static class Samples {
        static Employee employee = Employee.builder()
                .id(1)
                .name("Some Test Name")
                .isPrivate(Boolean.FALSE)
                .email("123@gmail.com")
                .build();
        static List<EmployeeReadDto> listEmployeesDto = List.of(
                EmployeeReadDto.builder()
                        .name("123")
                        .email("12@gmail.com")
                        .build(),
                EmployeeReadDto.builder()
                        .name("2")
                        .email("123@gmail.com")
                        .build()
                , EmployeeReadDto.builder()
                        .name("3")
                        .email("123@gmail.com")
                        .build()
        );

        static List<Employee> listEmployees = List.of(
                Employee.builder()
                        .id(1)
                        .name("123")
                        .email("12@gmail.com")
                        .build(),
                Employee.builder()
                        .id(2)
                        .name("2")
                        .email("123@gmail.com")
                        .build()
                , Employee.builder()
                        .id(3)
                        .name("3")
                        .email("123@gmail.com")
                        .build()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void saveEmployeeTest() throws Exception {

        EmployeeDto request = new EmployeeDto();
        request.id = Samples.employee.getId();
        request.name = Samples.employee.getName();
        request.email = Samples.employee.getEmail();
        request.isPrivate = Samples.employee.getIsPrivate();

        EmployeeReadDto response = EmployeeReadDto
                .builder()
                .name(request.name)
                .email(request.email)
                .build();

        when(employeeService.create(any(Employee.class))).thenReturn(Samples.employee);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(request));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(response.name)));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void readEmployeesTest() throws Exception {
        List<EmployeeReadDto> expected = List.copyOf(Samples.listEmployeesDto);

        when(employeeService.getAll()).thenReturn(List.copyOf(Samples.listEmployees));

        mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/employees")
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(expected.size())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateEmployeePutTest() throws Exception {
        EmployeeReadDto expected = Samples.listEmployeesDto.get(0);
        EmployeeForPatchDto request = new EmployeeForPatchDto();
        request.name = "123";
        request.email = "12@gmail.com";

        when(employeeService.repostById(anyInt(), any(Employee.class))).thenReturn(List.copyOf(Samples.listEmployees).get(0));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(expected.name)));
    }

}