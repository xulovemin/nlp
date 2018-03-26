package com.jc.core.persistence.page;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * TODO: 扩展page接口 整合datatables分页
 *
 * @param <T>
 * @author 高研
 * @date 2015年9月25日 下午2:02:02
 */
public class JPage {

    public JPage() {
        super();
    }

    private int sEcho;

    private int iDisplayStart;

    private int iDisplayLength;

    private long iTotalRecords;

    private long iTotalDisplayRecords;

    private PageInfo pageInfo;


    public void setAaData(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List getAaData() {
        return pageInfo.getList();
    }

    public int getsEcho() {
        return sEcho;
    }

    public void setsEcho(int sEcho) {
        this.sEcho = sEcho;
    }

    public int getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public long getiTotalRecords() {
        return pageInfo.getTotal();
    }

    public void setiTotalRecords(long iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public long getiTotalDisplayRecords() {
        return pageInfo.getTotal();
    }

    public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

}
