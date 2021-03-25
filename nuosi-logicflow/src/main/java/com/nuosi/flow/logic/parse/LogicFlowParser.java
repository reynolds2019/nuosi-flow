package com.nuosi.flow.logic.parse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nuosi.flow.logic.model.LogicFlow;
import com.nuosi.flow.logic.model.body.EndNode;
import com.nuosi.flow.logic.model.body.Node;
import com.nuosi.flow.logic.model.body.SqlNode;
import com.nuosi.flow.logic.model.body.StartNode;
import com.nuosi.flow.logic.model.element.Var;
import com.nuosi.flow.logic.model.header.GlobalDomain;
import com.nuosi.flow.logic.util.Dom4jHelper;
import com.nuosi.flow.logic.util.DtoUtil;

import java.io.InputStream;

/**
 * @author nuosi fsofs@163.com
 * @version 0.1.0
 * @name LogicFlowParser
 * @desc TODO
 * @date 2021/3/7 16:37
 */
public class LogicFlowParser {
    public static final String LOGIC_FLOW = "logic-flow";
    public static final String IMPORT = "import";
    public static final String DATABUS = "databus";
    public static final String VAR = "var";

    public static final String NODE = "node";
    public static final String GLOBAL_DOMAIN = "global-domain";
    public static final String START = "start";
    public static final String END = "end";

    public static final String INPUT = "input";
    public static final String OUTPUT = "output";
    public static final String NODE_DOMAIN = "node_domain";
    public static final String SQL = "sql";

    public static final String CHILDREN = Dom4jHelper.CHILDREN_TAG;
    public static final String SUFFIX_ATTR = com.ai.ipu.common.xml.Dom4jHelper.SUFFIX_ATTR;
    public static final String SUFFIX_TEXT = com.ai.ipu.common.xml.Dom4jHelper.SUFFIX_TEXT;

    public LogicFlow parser(InputStream flowInputStream) throws Exception {
        Dom4jHelper dh = new Dom4jHelper(flowInputStream);
        JSONObject originData = dh.getAllJson();
        LogicFlow logicFlow = parserLogicFlow(originData);
        return logicFlow;
    }

    public LogicFlow parserLogicFlow(JSONObject originData) {
        JSONObject logicFlowObject = originData.getJSONObject(LOGIC_FLOW);
        JSONObject logicFlowAttr = logicFlowObject.getJSONObject(LOGIC_FLOW + SUFFIX_ATTR);
        LogicFlow logicFlow = logicFlowAttr.toJavaObject(LogicFlow.class);

        JSONArray children = logicFlowObject.getJSONArray(CHILDREN);
        if (children != null && !children.isEmpty()) {
            for (int i = 0; i < children.size(); i++) {
                parserLogicFlowChildren(children.getJSONObject(i));
            }
        }
        return logicFlow;
    }

    /**
     * <p>desc: 频率node>其他</p>
     * <p>date: 2021/3/24 11:43</p>
     *
     * @param logicFlowChildren 1
     * @return com.nuosi.flow.logic.model.LogicFlow
     * @author nuosi fsofs@163.com
     * @version v1.0.0
     */
    public LogicFlow parserLogicFlowChildren(JSONObject logicFlowChildren) {
        if (logicFlowChildren.containsKey(NODE)) {
            parserNode(logicFlowChildren.getJSONObject(NODE));
        } else if (logicFlowChildren.containsKey(GLOBAL_DOMAIN)) {

        } else if (logicFlowChildren.containsKey(START)) {

        } else if (logicFlowChildren.containsKey(END)) {

        } else {
            // 抛无指定节点异常
        }
        return null;
    }

    public Node parserNode(JSONObject node) {
        // 生成node的Java对象。
        JSONObject nodeAttr = node.getJSONObject(NODE + SUFFIX_ATTR);
        SqlNode sqlNode = nodeAttr.toJavaObject(SqlNode.class);
        System.out.println("node===" + DtoUtil.toString(sqlNode));

        // 判断children并循环。
        JSONArray childrenNode = node.getJSONArray(CHILDREN);
        if (childrenNode != null && !childrenNode.isEmpty()) {
            for (int i = 0; i < childrenNode.size(); i++) {
                parserLogicFlowChildren(childrenNode.getJSONObject(i));
            }
        }
        return null;
    }

    public StartNode parserStart(JSONObject node) {
        // 生成node的Java对象。

        //判断children并循环。

        return null;
    }

    public EndNode parserEnd(JSONObject node) {
        // 生成node的Java对象。

        //判断children并循环。

        return null;
    }

    public GlobalDomain parserGlobalDomain(JSONObject node) {
        // 生成node的Java对象。

        //判断children并循环。

        return null;
    }

    public GlobalDomain parserNodeChildren(JSONObject node) {

        return null;
    }
}
