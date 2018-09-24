package com.cmpl.web.core.page;

import com.cmpl.web.core.common.dto.BaseDTO;

public class RenderingPageDTO extends BaseDTO {

    private String name;

    private String menuTitle;

    private String href;

    private boolean indexed;

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isIndexed() {
        return indexed;
    }

    public void setIndexed(boolean indexed) {
        this.indexed = indexed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
