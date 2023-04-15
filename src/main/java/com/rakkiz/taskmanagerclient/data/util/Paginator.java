package com.rakkiz.taskmanagerclient.data.util;

import java.util.List;

public interface Paginator<T> {

    /**
     * Returns true if, and only if, retrieved data can be split over multiple pages.
     * @return true if more than one page exists, otherwise false
     */
    boolean isPageable();

    /**
     * Returns true if, and only if, there exist a page after the current page.
     * @return true if current page is not last, otherwise false
     */
    boolean hasNext();

    /**
     * Returns true if, and only if, there exists a page before the current page.
     * @return true if current page is not first, otherwise false
     */
    boolean hasPrevious();

    /**
     * Sets the count of records in each page
     * @param recordsCount new record count per Page
     */
    void setRecordsPerPage(int recordsCount);

    /**
     * Returns the number of pages a paginator can reach.
     * @return total number of pages
     */
    int getPageCount();
    /**
     * Retrieves records consisting the current page.
     * @return list of records.
     */
    List<T> get();

    /**
     * Positions the paginator to the next page.
     */
    void toNext();
    /**
     * Positions the paginator to the previous page.
     */
    void toPrevious();
    /**
     * Positions the paginator to the first page.
     */
    void toFirst();
    /**
     * Positions the paginator to the last page.
     */
    void toLast();
    /**
     * Positions the paginator to page of given index
     * @param pageIndex Page's index, where 0 is first
     * @throws IndexOutOfBoundsException if index is less than 0 or greater than index of last page
     */
    void toPage(int pageIndex);

}
