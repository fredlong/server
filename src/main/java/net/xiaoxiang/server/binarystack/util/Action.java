package net.xiaoxiang.server.binarystack.util;


/**
 * 回调辅助类
 */
public interface Action<T>
{
    void run(T a);
}