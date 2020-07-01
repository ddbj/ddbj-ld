package com.ddbj.ld.common.helper;

import com.ddbj.ld.bean.common.DistributionBean;
import com.ddbj.ld.bean.common.JsonBean;
import com.ddbj.ld.bean.common.OrganismBean;
import com.ddbj.ld.common.constant.DistributionEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Component
public class ParserHelper {
    private UrlHelper urlHelper;

    public String getElementText(XMLStreamReader reader) {
        String elementText = null;

      try {
          elementText = reader.getElementText();
          reader.getEncoding();

          return elementText;
      } catch(XMLStreamException e) {
          return null;
      }
    }

    public String readAll(final String path) throws IOException {
        return Files.lines(Paths.get(path), Charset.forName("UTF-8"))
                .collect(Collectors.joining(System.getProperty("line.separator")));
    }

    public OrganismBean getOrganism(String name, String identifier) {
        OrganismBean organismBean = new OrganismBean();
        organismBean.setName(name);
        organismBean.setIdentifier(identifier);

        return organismBean;
    }

    public List<DistributionBean> getDistribution(String type, String identifier) {
        List<DistributionBean> distributionBeanList = new ArrayList<>();
        DistributionBean jsonDistributionBean = new DistributionBean();
        DistributionBean jsonLdDistributionBean = new DistributionBean();

        String jsonType   = DistributionEnum.TYPE_DATA_DOWNLOAD.getItem();
        String jsonLdType = DistributionEnum.TYPE_DATA_DOWNLOAD.getItem();

        String jsonEncodingFormat   = DistributionEnum.ENCODING_FORMAT_JSON.getItem();
        String jsonLdEncodingFormat = DistributionEnum.ENCODING_FORMAT_JSON_LD.getItem();

        String jsonExtension   = DistributionEnum.CONTACT_URL_EXTENTION_JSON.getItem();
        String jsonLdExtension = DistributionEnum.CONTACT_URL_EXTENTION_JSON_LD.getItem();

        String jsonContentUrl      = urlHelper.getUrl(type, identifier, jsonExtension);
        String jsonConLdContentUrl = urlHelper.getUrl(type, identifier, jsonLdExtension
        );

        jsonDistributionBean.setType(jsonType);
        jsonLdDistributionBean.setType(jsonLdType);

        jsonDistributionBean.setEncodingFormat(jsonEncodingFormat);
        jsonLdDistributionBean.setEncodingFormat(jsonLdEncodingFormat);

        jsonDistributionBean.setContentUrl(jsonContentUrl);
        jsonLdDistributionBean.setContentUrl(jsonConLdContentUrl);

        distributionBeanList.add(jsonDistributionBean);
        distributionBeanList.add(jsonLdDistributionBean);

        return distributionBeanList;
    }

    public JsonBean getBean(JSONObject obj, String type, String isPartOf, String organismName, String organismIdentifier) {
        String identifier  = obj.getString("accession");
        String url         = urlHelper.getUrl(type, identifier);
        String properties  = obj.toString();

        OrganismBean organism               = getOrganism(organismName, organismIdentifier);
        List<DistributionBean> distribution = getDistribution(type, identifier);

        JsonBean jsonBean = new JsonBean();
        jsonBean.setIdentifier(identifier);
        jsonBean.setType(type);
        jsonBean.setUrl(url);
        jsonBean.setIsPartOf(isPartOf);
        jsonBean.setOrganism(organism);
        jsonBean.setDistribution(distribution);
        jsonBean.setProperties(properties);

        return jsonBean;
    }
}
