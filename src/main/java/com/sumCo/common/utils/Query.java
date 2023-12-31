package com.sumCo.common.utils;

import org.apache.commons.lang.StringUtils;

import com.sumCo.common.xss.SQLFilter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author oplus
 * @Description: TODO(查詢參數)
 * @date 2017-6-23 15:07
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;

    private int page;

    private int limit;

    public Query(Map<String, Object> params){
        this.putAll(params);


        this.page = Integer.parseInt(params.get("page").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.put("offset", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);


        String sidx = (String)params.get("sidx");
        String order = (String)params.get("order");
        if(StringUtils.isNotBlank(sidx)){
            this.put("sidx", SQLFilter.sqlInject(sidx));
        }
        if(StringUtils.isNotBlank(order)){
            this.put("order", SQLFilter.sqlInject(order));
        }

    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
