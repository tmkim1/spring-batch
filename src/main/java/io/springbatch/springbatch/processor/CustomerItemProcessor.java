package io.springbatch.springbatch.processor;

import io.springbatch.springbatch.domain.Customer;
import io.springbatch.springbatch.domain.Customer2;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

public class CustomerItemProcessor implements ItemProcessor<Customer, Customer2> {

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public Customer2 process(Customer item) throws Exception {
        return modelMapper.map(item, Customer2.class);
    }
}
