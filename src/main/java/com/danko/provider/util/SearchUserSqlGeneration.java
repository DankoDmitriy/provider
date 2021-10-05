package com.danko.provider.util;

import com.danko.provider.validator.InputDataValidator;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.danko.provider.controller.command.ParamName.*;
import static com.danko.provider.domain.dao.ColumnName.*;

public class SearchUserSqlGeneration {
    private static final String SQL_LIKE = "LIKE";
    private static final String SQL_PERCENT = "%";
    private static final String SQL_SPACE = " ";
    private static final String SQL_QUOTE = "'";
    private static final String SQL_AND = "and";
    private static final String SQL_WHERE = " where ";
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static SearchUserSqlGeneration instance;
    private final InputDataValidator validator;

    private SearchUserSqlGeneration() {
        this.validator = InputDataValidator.getInstance();
    }

    public static SearchUserSqlGeneration getInstance() {
        while (instance == null) {
            if (isCreated.compareAndSet(false, true)) {
                instance = new SearchUserSqlGeneration();
            }
        }
        return instance;
    }

    public String generateSearchSqlCriteria(Map<String, String[]> inputCriteriaMap) {
        String[] firstName = inputCriteriaMap.get(USER_SEARCH_FIRST_NAME);
        String[] lastName = inputCriteriaMap.get(USER_SEARCH_LAST_NAME);
        String[] patronymic = inputCriteriaMap.get(USER_SEARCH_PATRONYMIC);
        String[] contractNumber = inputCriteriaMap.get(USER_SEARCH_CONTRACT_NUMBER);
        String[] email = inputCriteriaMap.get(USER_SEARCH_E_MAIL);
        boolean andAdd = false;
        StringBuilder sb = new StringBuilder();
        if (firstName != null && validator.isFirstNameValid(firstName[0])) {
            addCriteriaParameter(andAdd, sb, USER_FIRST_NAME, firstName[0]);
            andAdd = true;
        }
        if (lastName != null && validator.isLastNameValid(lastName[0])) {
            addCriteriaParameter(andAdd, sb, USER_LAST_NAME, lastName[0]);
            andAdd = true;
        }
        if (patronymic != null && validator.isPatronymicValid(patronymic[0])) {
            addCriteriaParameter(andAdd, sb, USER_PATRONYMIC, patronymic[0]);
            andAdd = true;
        }
        if (contractNumber != null && validator.isContractNumberForSearchValid(contractNumber[0])) {
            addCriteriaParameter(andAdd, sb, USER_CONTRACT_NUMBER, contractNumber[0]);
            andAdd = true;
        }
        if (email != null && validator.isEmailForSearchValid(email[0])) {
            addCriteriaParameter(andAdd, sb, SQL_SPACE, email[0]);
        }
        if (andAdd) {
            return SQL_WHERE + sb.toString();
        } else {
            return null;
        }
    }

    private void addCriteriaParameter(boolean andAdd, StringBuilder sb, String columnName, String searchRex) {
        if (andAdd) {
            sb.append(SQL_SPACE)
                    .append(SQL_AND);
        }
        sb.append(SQL_SPACE)
                .append(columnName)
                .append(SQL_SPACE)
                .append(SQL_LIKE)
                .append(SQL_SPACE)
                .append(SQL_QUOTE)
                .append(SQL_PERCENT)
                .append(searchRex)
                .append(SQL_PERCENT)
                .append(SQL_QUOTE)
                .append(SQL_SPACE);
    }
}
