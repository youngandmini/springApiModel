package goorm.apimodel.web.controller;


import goorm.apimodel.domain.Student;
import goorm.apimodel.repository.StudentRepository;
import goorm.apimodel.web.dto.StudentDto;
import goorm.apimodel.web.exception.CustomException;
import goorm.apimodel.web.exception.ErrorCode;
import goorm.apimodel.web.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/students")
public class StudentApiController {

    private final StudentRepository studentRepository;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String infoMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        CustomException customException = new CustomException(ErrorCode.BAD_REQUEST_JSON_ERROR, infoMessage);

        return handleCustomException(customException);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse> handleCustomException(CustomException ex) {

        int code = ex.getCode();
        String message = ex.getMessage();
        String moreInfoMessage = ex.getInfoMessage();

        HttpStatus httpStatus;
        if (code == ErrorCode.INTERNAL_SERVER_ERROR.getCode()) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        ApiResponse response = new ApiResponse(code, message, moreInfoMessage);

        return new ResponseEntity<>(response, httpStatus);
    }


    @PostMapping("/new")
    public ResponseEntity<ApiResponse<StudentDto>> saveStudent(
            @RequestBody @Validated StudentDto studentDto) {

//        if (bindingResult.hasErrors()) {
//            log.info("bindingResult = {}", bindingResult);
//
//            String rejectedValue = "";
//            if (bindingResult.getFieldError("grade") != null) {
//                rejectedValue += bindingResult.getFieldError("grade").getRejectedValue();
//            }
//            if (bindingResult.getFieldError("name") != null) {
//                rejectedValue += bindingResult.getFieldError("name").getRejectedValue();
//            }
//            throw new CustomException(ErrorCode.BAD_REQUEST_JSON_ERROR, rejectedValue);
//        }

        Student student = new Student(studentDto.getName(), studentDto.getGrade());
        studentRepository.save(student);

        StudentDto returnStudentDto = new StudentDto(student);
        ApiResponse<StudentDto> apiResponse = new ApiResponse<>(returnStudentDto);
        log.info("apiResponse = {}", apiResponse);

        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<StudentDto>> getAllStudent(
            @RequestParam(required = false) String grade) {
        List<Student> students;

        //파라미터가 없으면 전체 조회, 있으면 특정 성적으로 조회
        if (grade == null) {
            students = studentRepository.findAll();
        } else {
            try {
                int gradeInt = Integer.parseInt(grade);
                if (gradeInt <= 0 || gradeInt > 6) {
                    throw new CustomException(ErrorCode.DISALLOWED_GRADE_RANGE_ERROR);
                }
                students = studentRepository.findByGrade(gradeInt);
            } catch (NumberFormatException e) {
                throw new CustomException(ErrorCode.UNAVAILABLE_GRADE_FORMAT_ERROR);
            }
        }

        List<StudentDto> studentDtos = students.stream().map(StudentDto::new).collect(Collectors.toList());
        ApiResponse<StudentDto> apiResponse = new ApiResponse<>(studentDtos);
        log.info("apiResponse = {}", apiResponse);

        return ResponseEntity.ok().body(apiResponse);
    }

    @RequestMapping(value = "/**")
    public void unavailableUrl() {
        throw new CustomException(ErrorCode.NON_EXISTENT_URL_ERROR);
    }
}
