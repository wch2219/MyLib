package com.example.httprxjava.helper;

/**
 * 数据解析helper
 *
 * @author ZhongDaFeng
 */
public interface ParseHelper<T> {
    /*解析数据*/
    T parse(String data);
}
