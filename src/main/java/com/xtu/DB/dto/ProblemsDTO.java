package com.xtu.DB.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * 题目筛选数据传输对象
 * Created by Ilovezilian on 2017/4/18.
 */
@Data
public class ProblemsDTO {
    /**
     * 问题Id
     */
    @Min(0)
    private int problemId = 0;
    /**
     * 题目名称
     */
    @Size(max = 50)
    private String title = "";
    @Min(0)
    private int start = 0;
    @Min(0)
    @Max(1000)
    private int size = 50;
}
