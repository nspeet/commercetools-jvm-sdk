package de.commercetools.sphere.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FilterParser {

    public static List<FilterExpression> parse(Map<String,String[]> queryString, Collection<FilterDefinition> filterDefinitions) {
        List<FilterExpression> filterQueries = new ArrayList<FilterExpression>();
        for (FilterDefinition filter: filterDefinitions) {
            filterQueries.add(filter.parse(queryString));
        }
        return filterQueries;
    }
}
