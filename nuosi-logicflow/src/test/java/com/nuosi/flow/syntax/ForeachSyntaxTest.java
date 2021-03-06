package com.nuosi.flow.syntax;

import com.ai.ipu.data.JList;
import com.ai.ipu.data.JMap;
import com.ai.ipu.data.impl.JsonList;
import com.ai.ipu.data.impl.JsonMap;
import com.nuosi.flow.logic.LogicFlowEngine;
import com.nuosi.flow.logic.LogicFlowManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

/**
 * <p>desc: 循环逻辑相关语法展示 </p>
 * <p>date: 2021/5/11 18:28 </p>
 *
 * @author nuosi fsofs@163.com
 * @version v1.0.0
 */
public class ForeachSyntaxTest {

    @Test
    public void testForeachMap() {
        try {
            JMap paramMap = new JsonMap();
            paramMap.put("name", "zhangsan");
            paramMap.put("age", 18);
            JMap param = new JsonMap();
            param.put("input_map", paramMap);

            LogicFlowEngine.execute("foreach_map", param);
            Assert.assertTrue(true);
        } catch (Exception e) {
            System.out.println("抛出信息：" + e.getMessage());
            Assert.assertTrue(e.getMessage(), false);
        }
    }

    @Test
    public void testForeachExcept() {
        try {
            JList paramList = new JsonList();
            for (int i = 0; i < 10; i++) {
                paramList.add(i);
            }
            JMap param = new JsonMap();
            param.put("input_list", paramList);

            LogicFlowEngine.execute("foreach_except", param);
            Assert.assertTrue(false);
        } catch (Exception e) {
            System.out.println("抛出信息：" + e.getMessage());
            Assert.assertTrue(e.getMessage(), true);
        }
    }

    @Before
    public void setUp() {
        String flowConfig = "syntax/foreach_except.xml";
        InputStream is = getClass().getClassLoader().getResourceAsStream(flowConfig);
        LogicFlowManager.registerLogicFlow(is);

        flowConfig = "syntax/foreach_map.xml";
        is = getClass().getClassLoader().getResourceAsStream(flowConfig);
        LogicFlowManager.registerLogicFlow(is);
    }
}
