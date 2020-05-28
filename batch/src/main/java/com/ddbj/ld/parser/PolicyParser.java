package com.ddbj.ld.parser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ddbj.ld.bean.PolicyBean;
import com.ddbj.ld.common.ParserHelper;

@Component
@AllArgsConstructor
@Slf4j
public class PolicyParser {
    private ParserHelper parserHelper;

    // TODO name, dateCreated, dateModified, datePublished
    public List<PolicyBean> parse(String xmlFile) {
        try {
            String xml = parserHelper.readAll(xmlFile);
            JSONObject policySet  = XML.toJSONObject(xml).getJSONObject("POLICY_SET");
            Object policyObject   = policySet.get("POLICY");

            List<PolicyBean> policyBeanList = new ArrayList<>();

            if(policyObject instanceof JSONArray) {
                JSONArray policyArray = ((JSONArray)policyObject);

                for(int n = 0; n < policyArray.length(); n++) {
                    JSONObject policy  = policyArray.getJSONObject(n);
                    PolicyBean policyBean = getBean(policy);
                    policyBeanList.add(policyBean);
                }
            } else {
                JSONObject policy = (JSONObject)policyObject;
                PolicyBean policyBean = getBean(policy);
                policyBeanList.add(policyBean);
            }

            return policyBeanList;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }

    private PolicyBean getBean(JSONObject obj) {
        String identifier  = obj.getString("accession");
        String title       = obj.getString("TITLE");
        String description = obj.getString("POLICY_TEXT");
        String properties  = obj.toString();

        PolicyBean policyBean = new PolicyBean();
        policyBean.setIdentifier(identifier);
        policyBean.setTitle(title);
        policyBean.setDescription(description);
        policyBean.setProperties(properties);

        return policyBean;
    }
}
