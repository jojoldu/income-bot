package com.jojoldu.incomebot.batch.common;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class JpaItemListPersistWriter<T> implements ItemWriter<List<T>> {

    private JpaItemWriter<T> jpaItemWriter;

    public JpaItemListPersistWriter(JpaItemWriter<T> jpaItemWriter) {
        this.jpaItemWriter = jpaItemWriter;
    }

    @Override
    public void write(List<? extends List<T>> items) {
        List<T> totalList = items.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        jpaItemWriter.write(totalList);
    }

}
