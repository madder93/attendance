package com.madder.attendance.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangqian
 * @Date 2019-04-30 11:00
 **/

public class Page implements Serializable {

    private static final long serialVersionUID = 8569827301085211379L;
    private int showCount;
    private int totalPage;
    private int totalResult;
    private int currentPage;
    private int currentResult;
    private boolean entityOrField;

    public Page() {
        try {
            this.showCount = 20;
        } catch (Exception var2) {
            this.showCount = 15;
        }

    }

    public Page(int currentPage, int showCount) {
        this.currentPage = currentPage;

        try {
            this.showCount = showCount;
        } catch (Exception var4) {
            this.showCount = 15;
        }

    }

    public int getTotalPage() {
        if (this.totalResult % this.showCount == 0) {
            this.totalPage = this.totalResult / this.showCount;
        } else {
            this.totalPage = this.totalResult / this.showCount + 1;
        }

        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalResult() {
        return this.totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public int getCurrentPage() {
        if (this.currentPage <= 0) {
            this.currentPage = 1;
        }

        if (this.currentPage > this.getTotalPage()) {
            this.currentPage = this.getTotalPage();
        }

        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getShowCount() {
        return this.showCount;
    }

    public void setShowCount(int showCount) {
        this.showCount = showCount;
    }

    public int getCurrentResult() {
        this.currentResult = (this.getCurrentPage() - 1) * this.getShowCount();
        if (this.currentResult < 0) {
            this.currentResult = 0;
        }

        return this.currentResult;
    }

    public void setCurrentResult(int currentResult) {
        this.currentResult = currentResult;
    }

    public boolean isEntityOrField() {
        return this.entityOrField;
    }

    public void setEntityOrField(boolean entityOrField) {
        this.entityOrField = entityOrField;
    }

    /*public static final Page fromRequestContent(RequestContent requestContent) {
        Page page = new Page();
        page.setCurrentPage(requestContent.getPageCurrentNum());
        page.setShowCount(requestContent.getPageShowCount());
        return page;
    }

    public final void toResponseContent(ResponseContent responseContent) {
        Map<String, Integer> page = new HashMap();
        page.put("currentNum", this.currentPage);
        page.put("showCount", this.showCount);
        page.put("totalPage", this.totalPage);
        page.put("totalCount", this.totalResult);
        responseContent.addResultObject("page", page);
    }*/
}
