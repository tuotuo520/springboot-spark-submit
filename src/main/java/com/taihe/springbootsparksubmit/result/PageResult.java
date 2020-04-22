/*
 * Copyright 2016 juor & Co., Ltd.
 */
package com.taihe.springbootsparksubmit.result;


import com.taihe.springbootsparksubmit.constant.ErrorCodes;
import lombok.NoArgsConstructor;

/**
 * 分頁结果
 *
 * @author Lyn
 */
@NoArgsConstructor
public final class PageResult<T> extends Result<Page<T>> {

    public PageResult(Page<T> page) {
        this.setCode(ErrorCodes.OK);
        this.setData(page);
    }
}
