package com.ddbj.ld.common.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface BulkUtil<T> extends Iterable<T> {
    static <T> void extract(List<T> list, int size, Consumer<? super List<T>> bulkList) {
        for (int i = 0; i < list.size(); i += size) {
            List<T> _list = new ArrayList<>(list.subList(i, Integer.min(i + size, list.size())));
            bulkList.accept(_list);
        }
    }
}
