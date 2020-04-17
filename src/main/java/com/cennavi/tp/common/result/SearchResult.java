package com.cennavi.tp.common.result;


import java.util.List;

/**
 * 封装返回结果 分页列表
 */
public class SearchResult {
    protected List result;
    protected int totalCount = -1;
    protected int firstResult = 0;
    protected int maxResults = 0;

    public List getResult() {
        return result;
    }

    public void setResult(List result) {
        this.result = result;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }
}
