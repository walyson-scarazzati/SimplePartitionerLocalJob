package com.springbatch.simplepartitionerlocal.reader;

import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;

public class CustomArquivoReader<T> implements ItemStreamReader<T> {
    private FlatFileItemReader<T> delegate;
    private int itensLimit;

    public CustomArquivoReader(FlatFileItemReader<T> delegate, int itensLimit){
        this.delegate = delegate;
        this.itensLimit = itensLimit;
    }

    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
       if(itensLimit>0){
           itensLimit--;
           return delegate.read();
       } else {
           return null;
       }
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        delegate.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        delegate.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        delegate.close();
    }
}
