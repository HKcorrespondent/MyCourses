package org.nju.mycourses.web.controller;

import org.hibernate.service.spi.ServiceException;
import org.nju.mycourses.data.entity.PublishedCourse;
import org.nju.mycourses.logic.CourseService;
import org.nju.mycourses.logic.UserService;
import org.nju.mycourses.logic.exception.ExceptionNotValid;
import org.nju.mycourses.logic.util.EmailService;
import org.nju.mycourses.web.controller.dto.*;
import org.nju.mycourses.web.controller.vo.*;
import org.nju.mycourses.web.security.impl.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.nju.mycourses.web.security.WebSecurityConstants.ADMIN_ROLE;
import static org.nju.mycourses.web.security.WebSecurityConstants.STUDENT_ROLE;
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
    @RequestMapping(value="/teacher/course",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public CourseVO createCourse(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @RequestBody Map course, Errors errors) throws ServiceException {
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
    public CourseVO passCourse(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @RequestParam Integer id) throws ServiceException {
        return new CourseVO(
                courseService.passCourse(
                        id,
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("要通过的课程不存在"))
        );

    }

    @RolesAllowed({ADMIN_ROLE})
    @RequestMapping(value="/course/admin",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public Map getCourse(@AuthenticationPrincipal CustomUserDetails userDetails ) throws ServiceException {
        return courseService.adminGetCourse();

    }

    @RolesAllowed({TEACHER_ROLE})
    @RequestMapping(value="/teacher/course/{id}/doc",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public CourseVO postDoc(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody DocDTO docs,
                            @PathVariable Integer id, Errors errors) throws ServiceException {
        return new CourseVO(
                courseService.postDoc(
                        docs.getDocs(),
                        id,
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("课程不存在"))
        );

    }

    @RolesAllowed({TEACHER_ROLE})
    @RequestMapping(value="/teacher/course/{id}/publish",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public PublishedCourseVO publishCourse(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody PublishedCourseDTO courseDTO,
                                           @PathVariable Integer id, Errors errors) throws ServiceException {
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
    public PublishedCourseVO passCourse(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam Integer publishId,
                                        @PathVariable Integer id) throws ServiceException {
        return new PublishedCourseVO(
                courseService.passPublishedCourse(
                        publishId,
                        id,
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("要通过的课程或发布课程不存在"))
        );
    }
    @RolesAllowed({TEACHER_ROLE})
    @RequestMapping(value="/teacher/course/{id}",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public TotalCourseVO teacherOneCourse(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @PathVariable Integer id) throws ServiceException {
        return new TotalCourseVO(
                courseService.getCourse(id,userDetails.getUsername()).orElseThrow(() -> new ExceptionNotValid("课程不存在")),
                courseService.getPublishedCourses(id,userDetails.getUsername())
        );
    }

    @RolesAllowed({TEACHER_ROLE})
    @RequestMapping(value="/teacher/course/{id}/publish/{publishId}",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public PublishedCourseDetailVO teacherOnePublishCourse(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                 @PathVariable Integer id, @PathVariable Integer publishId) throws ServiceException {


        final PublishedCourse publishedCourse = courseService.getPublishedCourse(id, publishId, userDetails.getUsername()).orElseThrow(() -> new ExceptionNotValid("课程不存在"));
        return new PublishedCourseDetailVO(
                publishedCourse
        );
    }

    @RolesAllowed({TEACHER_ROLE})
    @RequestMapping(value="/teacher/course",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public Map teacherCourses(@AuthenticationPrincipal CustomUserDetails userDetails) throws ServiceException {
        Map<String,Object> courseVOMap =new HashMap<>();
        List<CourseVO> courseVOs;
        courseVOs=courseService.getTeacherCourse(userDetails.getUsername())
                .stream().map(CourseVO::new).collect(Collectors.toList());
        courseVOMap.put("courses",courseVOs);
        return courseVOMap;
    }
    @RolesAllowed({STUDENT_ROLE})
    @RequestMapping(value="/student/couldSelectCourse",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public Map studentCouldSelectCourse(@AuthenticationPrincipal CustomUserDetails userDetails) throws ServiceException {
        Map<String,Object> publishedCourseVOMap =new HashMap<>();
        List<PublishedCourseVO> publishedCourseVOs;
        publishedCourseVOs=courseService.getCouldSelectCourse(userDetails.getUsername())
                .stream().map(PublishedCourseVO::new).collect(Collectors.toList());
        publishedCourseVOMap.put("publishedCourses",publishedCourseVOs);
        return publishedCourseVOMap;
    }
    @RolesAllowed({STUDENT_ROLE})
    @RequestMapping(value="/student/course",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public Map studentCourse(@AuthenticationPrincipal CustomUserDetails userDetails) throws ServiceException {
        Map<String,Object> publishedCourseVOMap =new HashMap<>();
        List<PublishedCourseDetailVO> publishedCourseVOs;
        publishedCourseVOs=courseService.getStudentCourse(userDetails.getUsername())
                .stream().map(PublishedCourseDetailVO::new).collect(Collectors.toList());
        publishedCourseVOMap.put("publishedCourses",publishedCourseVOs);
        return publishedCourseVOMap;
    }
    @RolesAllowed({TEACHER_ROLE})
    @RequestMapping(value="/teacher/course/{id}/publish/{publishId}/homework",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public HomeworkVO postHomework(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody HomeworkDTO homeworkDTO,
                                   @PathVariable Integer id, @PathVariable Integer publishId, Errors errors) throws ServiceException {
        return new HomeworkVO(
                courseService.postHomework(
                        homeworkDTO,
                        id,publishId,
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("要发布作业的课程不存在"))
        );
    }
    @RolesAllowed({TEACHER_ROLE})
    @RequestMapping(value="/teacher/course/{id}/publish/{publishId}/homework/{hwId}",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public HomeworkVO getHomework(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @PathVariable Integer id, @PathVariable Integer publishId, @PathVariable Integer hwId) throws ServiceException {
        return new HomeworkVO(
                courseService.getHomework(
                        hwId,id,publishId,
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("要发布作业的课程不存在"))
        );
    }
    @RolesAllowed({STUDENT_ROLE})
    @RequestMapping(value="/student/course/{id}/publish/{publishId}/homework/{homeworkId}",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public UpHomeworkVO upHomework(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UpHomeworkDTO upHomeworkDTO,
                                   @PathVariable Integer id, @PathVariable Integer publishId, @PathVariable Integer homeworkId, Errors errors) throws ServiceException {
        return new UpHomeworkVO(
                courseService.upHomework(
                        upHomeworkDTO,
                        id,publishId,homeworkId,
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("要提交的作业不存在"))
        );
    }
    @RolesAllowed({TEACHER_ROLE})
    @RequestMapping(value="/teacher/course/{id}/publish/{publishId}/email",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public Map email2All(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody EmailDTO emailDTO,
                                   @PathVariable Integer id, @PathVariable Integer publishId,  Errors errors) throws ServiceException {
        int sandNum=    courseService.email2All(
                    emailDTO,
                    id,publishId,
                    userDetails.getUsername()
            );
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("res","成功发送"+sandNum+"封邮件！");
        return hashMap;
    }

    @RolesAllowed({TEACHER_ROLE})
    @RequestMapping(value="/teacher/course/{id}/publish/{publishId}/homework/{homeworkId}/score",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public Map homeworkScore(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody HomeworkScoreDTO homeworkScoreDTO,
                                   @PathVariable Integer id, @PathVariable Integer publishId, @PathVariable Integer homeworkId, Errors errors) throws ServiceException {
        Map<String,Object> upHomeworkVOListMap=new HashMap<>();
        List<UpHomeworkVO> upHomeworkVOList;
        upHomeworkVOList=courseService.homeworkScore(
                homeworkScoreDTO,
                id,publishId,homeworkId,
                userDetails.getUsername()
        ).stream().map(UpHomeworkVO::new).collect(Collectors.toList());
        upHomeworkVOListMap.put("upHomework",upHomeworkVOList);
        return upHomeworkVOListMap;
    }

    @RolesAllowed({TEACHER_ROLE})
    @RequestMapping(value="/teacher/course/{id}/publish/{publishId}/exam",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public Object examScore(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody HomeworkScoreDTO examDTO,
                             @PathVariable Integer id, @PathVariable Integer publishId, Errors errors) throws ServiceException {
        Map<String,Object> upHomeworkVOListMap=new HashMap<>();
        courseService.examScore(
                examDTO,
                id,publishId,
                userDetails.getUsername()
        );
        return examDTO;
    }

    @RolesAllowed({STUDENT_ROLE})
    @RequestMapping(value="/student/course/{id}/publish/{publishId}/select",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public PublishedCourseVO selectCourse(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Map body,
                                   @PathVariable Integer id, @PathVariable Integer publishId,  Errors errors) throws ServiceException {
        return new PublishedCourseVO(
                courseService.selectCourse(
                        id,publishId,
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("课程人数已满"))
        );
    }
    @RolesAllowed({STUDENT_ROLE})
    @RequestMapping(value="/student/course/{id}/publish/{publishId}",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public PublishedCourseVO course(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Map body,
                                          @PathVariable Integer id, @PathVariable Integer publishId) throws ServiceException {
        return new PublishedCourseVO(
                courseService.checkCourse(
                        id,publishId,
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("未选此课程，无法查看"))
        );
    }
    @RolesAllowed({STUDENT_ROLE})
    @RequestMapping(value="/student/course/{id}/publish/{publishId}/unselect",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public PublishedCourseVO unSelectCourse(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Map body,
                                          @PathVariable Integer id, @PathVariable Integer publishId,  Errors errors) throws ServiceException {
        return new PublishedCourseVO(
                courseService.unSelectCourse(
                        id,publishId,
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("课程不存在或未选此课程"))
        );
    }
}
