/**
 * <p>项目名：Mzyy</p>
 * <p>包名：com.rzhy.constants</p>
 * <p>文件名：Describable.java</p>
 * <p>版本信息：</p>
 * <p>日期：2015-5-9-下午10:23:26</p>
 * Copyright (c) 2015aux公司-版权所有
 */
package com.ysq.test.other;

/**
 * <p>名称：Describable.java</p>
 * <p>描述：可以被描述的抽象接口</p>
 * <pre>
 *
 * </pre>
 *
 * @author shenlx
 * @version 1.0.0
 * @date 2015-12-22 下午10:23:26
 */
public interface Describable {
    /**
     * 描述：
     * <pre>描述编码</pre>
     *
     * @return
     */
    Integer getCode();

    /**
     * 描述：
     * <pre>描述信息</pre>
     *
     * @return
     */
    String getMsg();
}
