package org.nju.mycourses.web.controller;

import org.hibernate.service.spi.ServiceException;
import org.nju.mycourses.data.entity.Forum;
import org.nju.mycourses.logic.CourseService;
import org.nju.mycourses.logic.ForumService;
import org.nju.mycourses.logic.UserService;
import org.nju.mycourses.logic.exception.ExceptionNotValid;
import org.nju.mycourses.logic.util.EmailService;
import org.nju.mycourses.web.controller.dto.ForumDTO;
import org.nju.mycourses.web.controller.dto.RemarkDTO;
import org.nju.mycourses.web.controller.dto.UpHomeworkDTO;
import org.nju.mycourses.web.controller.vo.CourseVO;
import org.nju.mycourses.web.controller.vo.ForumOutlineVO;
import org.nju.mycourses.web.controller.vo.ForumVO;
import org.nju.mycourses.web.controller.vo.UpHomeworkVO;
import org.nju.mycourses.web.security.impl.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

import java.util.List;
import java.util.stream.Collectors;

import static org.nju.mycourses.web.security.WebSecurityConstants.ADMIN_ROLE;
import static org.nju.mycourses.web.security.WebSecurityConstants.STUDENT_ROLE;
import static org.nju.mycourses.web.security.WebSecurityConstants.TEACHER_ROLE;

/**
 * @ClassName ForumController
 * @PackageName org.nju.mycourses.web.controller
 * @Author sheen
 * @Date 2019/2/20
 * @Version 1.0
 * @Description //TODO
 **/
@RestController
@RequestMapping(value="/api")
public class ForumController {
    private final EmailService emailService;
    private final UserService userService;
    private final CourseService courseService;
    private final ForumService forumService;
    @Autowired
    public ForumController(EmailService emailService, UserService userService, CourseService courseService, ForumService forumService) {
        this.emailService = emailService;
        this.userService = userService;
        this.courseService = courseService;
        this.forumService = forumService;
    }

    @RolesAllowed({STUDENT_ROLE,TEACHER_ROLE,ADMIN_ROLE})
    @RequestMapping(value="/student/course/{id}/forum",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ForumVO postForum(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody ForumDTO forumDTO,
                             @PathVariable Integer id,  Errors errors) throws ServiceException {
        return new ForumVO(
                forumService.postForum(
                        forumDTO,
                        id,
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("要发帖的课程不存在"))
        );
    }

    @RolesAllowed({STUDENT_ROLE,TEACHER_ROLE,ADMIN_ROLE})
    @RequestMapping(value="/student/course/{id}/forum/{forumId}",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ForumVO postRemark(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody RemarkDTO remarkDTO,
                             @PathVariable Integer id,  @PathVariable Integer forumId, Errors errors) throws ServiceException {
        return new ForumVO(
                forumService.postRemark(
                        remarkDTO,
                        id,forumId,
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("要回复的帖子不存在"))
        );
    }

    @RolesAllowed({STUDENT_ROLE,TEACHER_ROLE,ADMIN_ROLE})
    @RequestMapping(value="/student/course/{id}/forum/{forumId}",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public ForumVO getForum(@AuthenticationPrincipal CustomUserDetails userDetails,@PathVariable Integer forumId,
                            @PathVariable Integer id) throws ServiceException {
        return new ForumVO(
                forumService.getForum(
                        forumId,
                        id,
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("课程或帖子不存在"))
        );
    }
    @RolesAllowed({STUDENT_ROLE,TEACHER_ROLE,ADMIN_ROLE})
    @RequestMapping(value="/student/course/{id}/forum",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public List<ForumOutlineVO> getForumOutline(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                @PathVariable Integer id) throws ServiceException {
        return forumService.getForumOutline(id,userDetails.getUsername())
                .stream()
                .map(ForumOutlineVO::new)
                .collect(Collectors.toList());

    }
}
