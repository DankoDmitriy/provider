package com.danko.provider.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Pagination calculate.
 */
public class PaginationCalculate {
    /**
     * The constant START_POSITION.
     */
    public static final String START_POSITION = "startPosition";
    /**
     * The constant NEXT_PAGE.
     */
    public static final String NEXT_PAGE = "nextPage";
    /**
     * The constant PREVIEW_PAGE.
     */
    public static final String PREVIEW_PAGE = "previewPage";
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static PaginationCalculate instance;

    private PaginationCalculate() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PaginationCalculate getInstance() {
        while (instance == null) {
            if (isCreated.compareAndSet(false, true)) {
                instance = new PaginationCalculate();
            }
        }
        return instance;
    }

    /**
     * Calculate next and preview page value map.
     *
     * @param nextPage    the next page
     * @param rowsInTable the rows in table
     * @param rowsOnPage  the rows on page
     * @return the map
     */
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