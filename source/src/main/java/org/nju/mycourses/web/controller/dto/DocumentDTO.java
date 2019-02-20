package org.nju.mycourses.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.nju.mycourses.data.entity.Document;

/**
 * @ClassName DocumentVO
 * @PackageName org.nju.mycourses.web.controller.vo
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentDTO {
    private String name;
    private String path;
    private String type;

}
