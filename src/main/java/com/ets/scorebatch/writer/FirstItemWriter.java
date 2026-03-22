package com.ets.scorebatch.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.ets.scorebatch.model.StudentJson;
import com.ets.scorebatch.model.StudentXml;

@Component
public class FirstItemWriter implements ItemWriter<StudentXml> {

	@Override
	public void write(Chunk<? extends StudentXml> items) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Inside Item Writer");
		items.getItems().stream().forEach(System.out::println);
	}

}
