package com.nuosi.flow.syntax;

import com.ai.ipu.data.JMap;
import com.ai.ipu.data.impl.JsonMap;
import com.nuosi.flow.logic.LogicFlowEngine;
import com.nuosi.flow.logic.LogicFlowManager;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

/**
 * <p>desc: SQL逻辑相关语法展示 </p>
 * <p>date: 2021/5/19 23:16 </p>
 *
 * @author nuosi fsofs@163.com
 * @version v1.0.0
 */
public class SqlSyntaxTest {

    @Test
    public void testSqlSimple() throws Exception {
        JMap param = new JsonMap();
        param.put("fill_staff","zhangs");
        Object result = LogicFlowEngine.execute("sql_simple",param);
        System.out.println("result===" + result);
    }

    @Before
    public void setUp() {
        String flowConfig = "syntax/sql_simple.xml";
        InputStream is = getClass().getClassLoader().getResourceAsStream(flowConfig);
        LogicFlowManager.registerLogicFlow(is);
    }
}
