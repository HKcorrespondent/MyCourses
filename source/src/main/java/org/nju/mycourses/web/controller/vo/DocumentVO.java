package org.nju.mycourses.web.controller.vo;

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
public class DocumentVO {
    private String id;
    private String name;
    private String path;
    private String type;

    public DocumentVO(Document document) {
        this.id = document.getId();
        this.name = document.getName();
        this.path = document.getPath();
        this.type = document.getType();
    }
}
