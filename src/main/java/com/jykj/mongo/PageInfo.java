package com.jykj.mongo;

import java.io.Serializable;
import java.util.List;

public class PageInfo implements Serializable {
    private static final long serialVersionUID = 9145503394060648885L;
    private Integer page = Integer.valueOf(1);

    private Integer pageSize = Integer.valueOf(10);
    private Long totalRecord;
    private Long totalPage;
    private List<SortParam> sortParm;

    public PageInfo(Integer page, Integer pageSize, List<SortParam> sortParm)
    {
        this.page = page;
        this.pageSize = pageSize;
        this.sortParm = sortParm;
    }

    public PageInfo(Integer page, Integer pageSize, Long totalRecord)
    {
        this.page = page;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
    }

    public PageInfo(Integer page, Integer pageSize)
    {
        this.page = page;
        this.pageSize = pageSize;
    }

    public PageInfo(Integer page, Integer pageSize, Long totalRecord, List<SortParam> sortParm)
    {
        this.page = page;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        this.sortParm = sortParm;
    }

    public Integer getPage()
    {
        return this.page;
    }

    public void setPage(Integer page)
    {
        this.page = page;
    }

    public Integer getPageSize()
    {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public Long getTotalRecord()
    {
        return this.totalRecord;
    }

    public void setTotalRecord(Long totalRecord)
    {
        this.totalRecord = totalRecord;

        Long totalPage = Long.valueOf(totalRecord.longValue() % this.pageSize.intValue() == 0L ? totalRecord.longValue() / this.pageSize.intValue() : totalRecord.longValue() / this.pageSize.intValue() + 1L);
        setTotalPage(totalPage);
    }

    public Long getTotalPage()
    {
        return this.totalPage;
    }

    public void setTotalPage(Long totalPage)
    {
        this.totalPage = totalPage;
    }

    public List<SortParam> getSortParm()
    {
        return this.sortParm;
    }

    public void setSortParm(List<SortParam> sortParm)
    {
        this.sortParm = sortParm;
    }

    public String toString()
    {
        return "PageInfo [page=" + this.page + ", pageSize=" + this.pageSize +
                ", totalRecord=" + this.totalRecord + ", totalPage=" + this.totalPage +
                ", sortParm=" + this.sortParm + "]";
    }
}
