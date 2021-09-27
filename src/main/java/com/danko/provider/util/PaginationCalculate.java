package com.danko.provider.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class PaginationCalculate {
    public static final String START_POSITION = "startPosition";
    public static final String NEXT_PAGE = "nextPage";
    public static final String PREVIEW_PAGE = "previewPage";
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static PaginationCalculate instance;

    private PaginationCalculate() {
    }

    public static PaginationCalculate getInstance() {
        while (instance == null) {
            if (isCreated.compareAndSet(false, true)) {
                instance = new PaginationCalculate();
            }
        }
        return instance;
    }

    public Map<String, Long> calculateNextAndPreviewPageValue(long nextPage, long rowsInTable, long rowsOnPage) {
        Map<String, Long> result = new HashMap<>();
        long previewPage;
        long startPosition;
        if (nextPage <= 0) {
            nextPage = 0;
            previewPage = -1;
            startPosition = nextPage * rowsOnPage;
            if (rowsInTable > rowsOnPage) {
                nextPage++;
            }
        } else {
            startPosition = nextPage * rowsOnPage;
            if (rowsInTable > (rowsOnPage * (nextPage + 1))) {
                previewPage = nextPage - 1;
                nextPage++;
            } else {
                previewPage = nextPage - 1;
            }
        }
        result.put(NEXT_PAGE, nextPage);
        result.put(PREVIEW_PAGE, previewPage);
        result.put(START_POSITION, startPosition);
        return result;
    }
}
