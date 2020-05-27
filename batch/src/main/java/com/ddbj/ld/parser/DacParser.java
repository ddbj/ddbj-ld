package com.ddbj.ld.parser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ddbj.ld.bean.DacBean;
import com.ddbj.ld.common.ParserHelper;

@Component
@AllArgsConstructor
@Slf4j
public class DacParser {
    private ParserHelper parserHelper;

    // TODO name, title, description
    // TODO 現在Dacは一件しかなくJSONObjectとして取得されるが将来的に件数が増えたらJSONArrayとなる、その際は他のJGAのパーサと実装を合わせること
    public List<DacBean> parse(String xmlFile) {
        try {
            String xml      = parserHelper.readAll(xmlFile);
            JSONObject dac  = XML.toJSONObject(xml).getJSONObject("DAC_SET").getJSONObject("DAC");

            List<DacBean> dacBeanList = new ArrayList<>();

            String properties  = dac.toString();
            String identifier  = dac.getString("accession");

            DacBean dacBean = new DacBean();
            dacBean.setProperties(properties);
            dacBean.setIdentifier(identifier);

            dacBeanList.add(dacBean);

            return dacBeanList;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }
}
