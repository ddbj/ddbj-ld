package com.ddbj.ld.app.transact.service.dra.meta;

import com.ddbj.ld.common.helper.UrlHelper;
import com.ddbj.ld.data.beans.common.SameAsBean;
import com.ddbj.ld.data.beans.dra.common.ID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DraMetaCommonService {
    private final UrlHelper urlHelper;

    public List<SameAsBean> getSameAsBeans(List<ID> externalID, String type) {
        List<SameAsBean> sameAs = new ArrayList<>();

        for (int cnt = 0; cnt < externalID.size(); cnt++) {
            var sameAsName = externalID.get(cnt).getNamespace();
            var sameAsId =  externalID.get(cnt).getContent();
            var sameAsUrl = this.urlHelper.getUrl(type, sameAsId);
            SameAsBean sab = new SameAsBean();
            sab.setIdentifier(sameAsName);
            sab.setIdentifier(sameAsId);
            sab.setUrl(sameAsUrl);
            sameAs.add(sab);
        }
        return sameAs;
    }
}
