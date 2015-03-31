package by.itecharty.flowerty.dao.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import by.itechart.flowerty.dao.repository.GoodsRepository;
import by.itechart.flowerty.model.Company;
import by.itechart.flowerty.model.Flower;
import by.itechart.flowerty.model.Goods;
import by.itecharty.flowerty.config.JpaConfigurationAware;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 29.03.15
 * Time: 22:26
 * To change this template use File | Settings | File Templates.
 */
public class TestGoodsRepository extends JpaConfigurationAware {
    @Autowired
    private GoodsRepository goodsRepository;

    @Test
    public void findGoodsValidId () {
        Goods goods = goodsRepository.findOne(1l);
        Assert.assertEquals("Yellow Tulip", goods.getFlower().getName());
    }
    @Test
    public void findGoodsInvalidId () {
        Goods goods = goodsRepository.findOne(100l);
        Assert.assertNull(goods);
    }
    @Test
    public void saveGoods() {
        Flower fl = new Flower();
        fl.setId(4l);
        Company company = new Company();
        company.setId(1l);
        Goods goods = new Goods();
        goods.setCompany(company);
        goods.setFlower(fl);
        goods.setCost(15.7);
        goods.setRemain(111);
        goods = goodsRepository.save(goods);
        Assert.assertEquals((Object) goods.getCost(), 15.7);

    }
    @Test
    public void findByGoodCompany() {
        Company company = new Company();
        company.setId(1l);
        Page page = goodsRepository.findByCompany(company, new PageRequest(0, 10));
        Assert.assertNotNull(page);
        Assert.assertNotEquals(0, page.getContent().size());
    }
    @Test
    public void findByBadCompany() {
        Company company = new Company();
        company.setId(1000l);
        Page page = goodsRepository.findByCompany(company, new PageRequest(1, 10));
        Assert.assertNotNull(page);
        Assert.assertEquals(0, page.getNumberOfElements());
    }

}
