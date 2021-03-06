package com.nuosi.flow.logic.parse.backup;

import com.ai.ipu.basic.util.IpuUtility;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nuosi.flow.logic.model.action.Sql;
import com.nuosi.flow.logic.model.domain.Attr;
import com.nuosi.flow.logic.model.domain.Behavior;
import com.nuosi.flow.logic.model.domain.DomainModel;
import com.nuosi.flow.logic.model.domain.Limit;
import com.nuosi.flow.logic.util.XmlToJsonHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>desc: 业务传输对象解析</p>
 * <p>date: 2021/3/25 13:49</p>
 * @author nuosi fsofs@163.com
 * @version v1.0.0
 */
public class BizDataParser {
    public static final String MODEL = "model";
    public static final String ATTR = "attr";
    public static final String BEHAVIOR = "behavior";
    public static final String CHILDREN = XmlToJsonHelper.CHILDREN_TAG;

    public static final String INPUT = "input";
    public static final String OUTPUT = "output";
    public static final String SQL = "sql";

    public static final String VAR = "var";
    public static final String SUFFIX_ATTR = com.ai.ipu.common.xml.Dom4jHelper.SUFFIX_ATTR;
    public static final String SUFFIX_TEXT = com.ai.ipu.common.xml.Dom4jHelper.SUFFIX_TEXT;

    public static final String LIMIT = "limit";
    public static final String MAX = "max";
    public static final String MIN = "min";
    public static final String SIZE = "size";
    public static final String PRECISION = "precision";
    public static final String SCALE = "scale";
    public static final String MAX_DECIMAL = "maxDecimal";
    public static final String MIN_DECIMAL = "minDecimal";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String START_DATETIME = "startDatetime";
    public static final String END_DATETIME = "endDatetime";

    public DomainModel parser(InputStream flowInputStream) throws Exception {
        XmlToJsonHelper dh = new XmlToJsonHelper(flowInputStream);
        JSONObject allJson = dh.getAllJson();
        DomainModel domainModel = parserDomainModel(allJson);
        return domainModel;
    }

    public DomainModel parserDomainModel(JSONObject allJson){
        JSONObject domainModelObject = allJson.getJSONObject(MODEL);
        JSONObject domainModeAttr = domainModelObject.getJSONObject(MODEL + SUFFIX_ATTR);
        DomainModel domainModel = domainModeAttr.toJavaObject(DomainModel.class);

        JSONArray children = domainModelObject.getJSONArray(CHILDREN);
        if (children != null && !children.isEmpty()) {
            List<Attr> attrs = new ArrayList<Attr>();
            List<Behavior> behaviors = new ArrayList<Behavior>();
            JSONObject modelItem;
            for (int i = 0; i < children.size(); i++) {
                modelItem = children.getJSONObject(i);
                if (modelItem.containsKey(ATTR)) {
                    Attr attr = parserAttr(modelItem.getJSONObject(ATTR));
                    attrs.add(attr);
                } else if (modelItem.containsKey(BEHAVIOR)) {
                    Behavior behavior = parserBehavior(modelItem.getJSONObject(BEHAVIOR));
                    behaviors.add(behavior);
                } else{
                    IpuUtility.error("无可匹配标签："+ modelItem);
                }
            }
            if(!attrs.isEmpty()){
                domainModel.setAttrs(attrs);
            }
            if(!behaviors.isEmpty()){
                domainModel.setBehaviors(behaviors);
            }
        }

        return domainModel;
    }

    public Attr parserAttr(JSONObject attrObject){
        JSONObject attrJson = attrObject.getJSONObject(ATTR + SUFFIX_ATTR);
        Attr attr = attrJson.toJavaObject(Attr.class);

        JSONArray children = attrObject.getJSONArray(CHILDREN);
        if (children != null && !children.isEmpty()) {
            Limit limit = parserLimit(children.getJSONObject(0));
            List<Limit> limits = null;
            if(limit!=null){
                limits = new ArrayList<Limit>();
                limits.add(limit);
            }
            attr.setLimits(limits);
        }
        return attr;
    }

    public Limit parserLimit(JSONObject limitObject){
        JSONObject limitItem = limitObject.getJSONObject(LIMIT);
        JSONObject limitAttr = limitItem.getJSONObject(LIMIT + SUFFIX_ATTR);
        if(limitAttr!=null){
            Limit limit = limitAttr.toJavaObject(Limit.class);
            return limit;
        }else{
            return null;
        }

    }

    public Behavior parserBehavior(JSONObject behaviorObject){
        JSONObject behaviorJson = behaviorObject.getJSONObject(BEHAVIOR + SUFFIX_ATTR);
        Behavior behavior = behaviorJson.toJavaObject(Behavior.class);

        JSONArray children = behaviorObject.getJSONArray(CHILDREN);
        if (children != null && !children.isEmpty()) {
            JSONObject behaviorItem;
            List<Sql> sqls = null;
            for (int i = 0; i < children.size(); i++) {
                behaviorItem = children.getJSONObject(i);
                if (behaviorItem.containsKey(SQL)) {
                    sqls = sqls==null?new ArrayList<Sql>():sqls;
                    Sql sql = new Sql();
                    sql.setSql(behaviorItem.getJSONObject(SQL).getString(SQL+SUFFIX_TEXT));
                    sqls.add(sql);
                }else{
                    IpuUtility.error("无可匹配标签："+ behaviorItem);
                }
            }
            behavior.setSqls(sqls);
        }
        return behavior;
    }
}
