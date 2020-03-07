package com.ol.im.repository;

import com.ol.im.domain.ItemEntity;
import com.ol.im.exception.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;


    @Test
    public void saveItem() {
        ItemEntity item = new ItemEntity();
        item.setAmount(25.0);
        item.setInventoryCode("452353GF");
        item.setName("firstItem");
        ItemEntity result = itemRepository.save(item);
        Assertions.assertThat(result).isEqualTo(item);
        Assertions.assertThat(result.getItemNo()).isNotNull();
    }

    @Test
    public void getItemByItemNumber() {
        ItemEntity item = new ItemEntity();
        item.setAmount(25.0);
        item.setInventoryCode("452353GF");
        item.setName("firstItem");
        itemRepository.save(item);
        ItemEntity result = itemRepository.findById(item.getItemNo()).orElse(new ItemEntity());
        Assertions.assertThat(result.getItemNo()).isEqualTo(item.getItemNo());
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteByItemNumber() throws EntityNotFoundException {
        ItemEntity item = new ItemEntity();
        item.setAmount(25.0);
        item.setInventoryCode("452353GF");
        item.setName("firstItem");
        itemRepository.save(item);
        itemRepository.deleteById(item.getItemNo());
        itemRepository.findById(item.getItemNo()).orElseThrow(() -> new EntityNotFoundException("item not found"));
    }
}
