package org.nju.mycourses.web.controller;

import org.hibernate.service.spi.ServiceException;
import org.nju.mycourses.logic.CourseService;
import org.nju.mycourses.logic.UserService;
import org.nju.mycourses.logic.exception.ExceptionNotValid;
import org.nju.mycourses.logic.util.EmailService;
import org.nju.mycourses.web.controller.dto.DocDTO;
import org.nju.mycourses.web.controller.dto.PublishedCourseDTO;
import org.nju.mycourses.web.controller.vo.CourseVO;
import org.nju.mycourses.web.controller.vo.PublishedCourseVO;
import org.nju.mycourses.web.security.impl.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Map;

import static org.nju.mycourses.web.security.WebSecurityConstants.ADMIN_ROLE;
import static org.nju.mycourses.web.security.WebSecurityConstants.TEACHER_ROLE;

/**
 * @ClassName CourseController
 * @PackageName org.nju.mycourses.web.controller
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
@RestController
@RequestMapping(value="/api")
public class CourseController {
    private final EmailService emailService;
    private final UserService userService;
    private final CourseService courseService;
    @Autowired
    public CourseController(EmailService emailService, UserService userService, CourseService courseService) {
        this.emailService = emailService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @RolesAllowed({TEACHER_ROLE})
    @RequestMapping(value="/course",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public CourseVO createCourse(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Map course, Errors errors) throws ServiceException {
        if(course.size()==0||course.get("name")==null){
            throw new ExceptionNotValid("未填写课程名");
        }
        return new CourseVO(
                courseService.createCourse(
                        (String) course.get("name"),
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("课程名不符合规范"))
        );

    }

    @RolesAllowed({ADMIN_ROLE})
    @RequestMapping(value="/course/pass",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public CourseVO passCourse(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam Integer id, Errors errors) throws ServiceException {
        return new CourseVO(
                courseService.passCourse(
                        id,
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("要通过的课程不存在"))
        );

    }


    @RolesAllowed({TEACHER_ROLE})
    @RequestMapping(value="/course/{id}/doc",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public CourseVO postDoc(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody DocDTO docs, @PathVariable Integer id, Errors errors) throws ServiceException {
        return new CourseVO(
                courseService.postDoc(
                        docs.getDocs(),
                        id,
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("课程不存在"))
        );

    }

    @RolesAllowed({TEACHER_ROLE})
    @RequestMapping(value="/course/{id}/publish",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public PublishedCourseVO publishCourse(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody PublishedCourseDTO courseDTO, @PathVariable Integer id, Errors errors) throws ServiceException {
        return new PublishedCourseVO(
                courseService.publishCourse(
                        courseDTO,
                        id,
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("要发布的课程不存在"))
        );

    }

    @RolesAllowed({ADMIN_ROLE})
    @RequestMapping(value="/course/{id}/publish/pass",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public PublishedCourseVO passCourse(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam Integer publishId, @PathVariable Integer id,Errors errors) throws ServiceException {
        return new PublishedCourseVO(
                courseService.passPublishedCourse(
                        publishId,
                        id,
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("要通过的课程或发布课程不存在"))
        );

    }
}
