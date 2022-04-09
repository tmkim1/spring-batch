package io.springbatch.springbatch.itemReader;

import io.springbatch.springbatch.domain.Customer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.ArrayList;
import java.util.List;
public class CustomItemReader implements ItemReader<Customer> {

    private final List<Customer> list;

    public CustomItemReader(List<Customer> list) {
        this.list = new ArrayList<>(list);
    }

    @Override
    public Customer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if(!list.isEmpty()) {
            return list.remove(0);
        }

        return null;
    }
}
