package com.mikhail.githubapi.util;

import com.android.internal.util.Predicate;
import com.mikhail.githubapi.model.Contributor;
import com.mikhail.githubapi.model.Repo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Filter out contributors who have more than 1 contribution
 */
public class Filter {

    public static Collection<Contributor> isFrequentContributor(Collection<Contributor> target) {
        return filter(target, isFrequentContributorPredicate);
    }

    private static Predicate<Contributor> isFrequentContributorPredicate = new Predicate<Contributor>() {
        @Override
        public boolean apply(Contributor contributor) {
            return contributor.getContribution() > 1;
        }
    };


    public static <T> Collection<T> filter(Collection<T> target, Predicate<T> predicate) {
        Collection<T> result = new ArrayList<T>();
        for (T element: target) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }
}
