package com.xtu.DB.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by Ilovezilian on 2017/4/18.
 */
@Data
public class ModifyProblemsVO {
    /**
     * ModifyProblemsEntityVO列表
     */
    private List<ModifyProblemsEntityVO> entityList;
    /**
     * 总数
     */
    private long total;
    /**
     * 页数
     */
    private int start;
}
