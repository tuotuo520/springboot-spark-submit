package com.taihe.springbootsparksubmit.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@ApiModel("分页结果")
public class Page<T> {

    @ApiModelProperty("数据列表")
    private List<T> list;

    @ApiModelProperty("总纪录数")
    private long total;

    @ApiModelProperty("当前页码")
    private int pageNum;

    @ApiModelProperty("页纪录数")
    private int pageSize;

    @ApiModelProperty("总页数")
    private int pages;

    /**
     * 获取数据流
     *
     * @return 流
     */
    public Stream<T> stream() {
        return list.stream();
    }

    /**
     * 隐射指定结果
     *
     * @param mapper 映射器
     * @param <R>    结果类型
     * @return 分页结果
     */
    public <R> Page<R> map(Function<? super T, ? extends R> mapper) {
        List<R> newList = list.stream().map(mapper).collect(Collectors.toList());
        Page<R> newPage = new Page<>();
        newPage.setList(newList);
        newPage.setTotal(total);
        newPage.setPageNum(pageNum);
        newPage.setPageSize(pageSize);
        newPage.setPages(pages);
        return newPage;
    }

    /**
     * 过滤结果
     *
     * @param predicate 过滤条件
     * @return 分页结果
     */
    public Page<T> filter(Predicate<? super T> predicate) {
        List<T> newList = list.stream().filter(predicate).collect(Collectors.toList());
        Page<T> newPage = new Page<>();
        newPage.setList(newList);
        newPage.setTotal(total);
        newPage.setPageNum(pageNum);
        newPage.setPageSize(pageSize);
        newPage.setPages(pages);
        return newPage;
    }

    /**
     * 遍历结果
     *
     * @param action 处理动作
     */
    public void forEach(Consumer<? super T> action) {
        list.stream().forEach(action);
    }

}
