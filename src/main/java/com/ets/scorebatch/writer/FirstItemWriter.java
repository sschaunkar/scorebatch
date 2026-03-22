package com.ets.scorebatch.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class FirstItemWriter implements ItemWriter<Long> {

	@Override
	public void write(Chunk<? extends Long> items) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Inside Item Writer");
		items.getItems().stream().forEach(System.out::println);
	}

}
