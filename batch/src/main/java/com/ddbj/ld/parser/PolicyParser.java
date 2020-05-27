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

    // TODO name
    public List<PolicyBean> parse(String xmlFile) {
        try {
            String xml = parserHelper.readAll(xmlFile);
            JSONObject policySet  = XML.toJSONObject(xml);
            JSONArray policyArray = policySet.getJSONObject("POLICY_SET").getJSONArray("POLICY");

            List<PolicyBean> policyBeanList = new ArrayList<>();

            for(int n = 0; n < policyArray.length(); n++) {
                JSONObject policy     = policyArray.getJSONObject(n);

                String properties  = policy.toString();
                String identifier  = policy.getString("accession");
                String title       = policy.getString("TITLE");
                String description = policy.getString("POLICY_TEXT");

                PolicyBean policyBean = new PolicyBean();
                policyBean.setProperties(properties);
                policyBean.setIdentifier(identifier);
                policyBean.setTitle(title);
                policyBean.setDescription(description);

                policyBeanList.add(policyBean);
            }

            return policyBeanList;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }
}
