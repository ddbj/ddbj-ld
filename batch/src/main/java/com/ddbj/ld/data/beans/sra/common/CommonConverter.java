package com.ddbj.ld.data.beans.sra.common;

import com.ddbj.ld.data.beans.common.Converter;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonConverter {
    public static final ObjectMapper mapper = Converter.getMapper();
}
