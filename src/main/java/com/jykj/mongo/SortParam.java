package com.jykj.mongo;

public class SortParam {
    private static final long serialVersionUID = 3951795461119443446L;
    private String sortName;
    private Order sortOrder;

    public SortParam()
    {
    }

    public SortParam(String sortName, Order sortOrder)
    {
        this.sortName = sortName;
        this.sortOrder = sortOrder;
    }

    public String getSortName()
    {
        return this.sortName;
    }

    public Order getSortOrder()
    {
        return this.sortOrder;
    }

    public void setSortName(String sortName)
    {
        this.sortName = sortName;
    }

    public void setSortOrder(Order sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public String toString() {
        return "SortParam [sortName=" + this.sortName + ", sortOrder=" + this.sortOrder +
                "]";
    }
}
