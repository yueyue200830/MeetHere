package com.proj.meethere.unitTest.repositoryTest;

import com.proj.meethere.dao.RevenueRepository;
import com.proj.meethere.entity.Order;
import com.proj.meethere.entity.Revenue;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Yiqing Tao
 * @date 2019-12-20 11:22
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
public class RevenueRepositoryTest {
    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    RevenueRepository revenueRepository;

    Revenue revenue;

    @Before
    public void init() {
        revenue = new Revenue("mock name", 20, "mock introduction", 120);
    }

    @After
    public void cleanUp() {
        this.testEntityManager.clear();
    }

    @Test
    public void revenue_should_be_inserted() {
        int result = revenueRepository.insertNewRevenue("rvn_name", 100, 120, "rvn introduction");
        Assert.assertEquals(1, result);
    }

    @Test
    public void revenue_name_should_be_selected_uniquely() {
        Revenue revenue2 = new Revenue("rvn name", 10, "fake Introduction", 100);
        this.testEntityManager.persist(revenue);
        this.testEntityManager.persist(revenue2);
        List<String> names = revenueRepository.selectRevenueName();
        Assert.assertEquals(2, names.size());
    }

    @Test
    public void revenue_should_all_be_selected() {
        Revenue revenue2 = new Revenue();
        revenue2.setRvnName("mock name");
        revenue2.setRvnRoomnum(10);
        revenue2.setRvnIntro("mock introduction2");
        revenue2.setRvnPrice(100);
        this.testEntityManager.persist(revenue);
        this.testEntityManager.persist(revenue2);
        List<Revenue> revenueList = revenueRepository.getAllRvnInfo();
        Assert.assertEquals(2,revenueList.size());
        Revenue firstRevenue = revenueList.get(0);
        Revenue secondRevenue = revenueList.get(1);
        Assert.assertSame(revenue, firstRevenue);
        Assert.assertSame(revenue2, secondRevenue);
    }

    @Test
    public void revenue_should_be_selected_by_id() {
        this.testEntityManager.persist(revenue);
        List<Revenue> revenueList = revenueRepository.getRevenueById(revenue.getId());
        Assert.assertEquals(1, revenueList.size());
        Assert.assertSame(revenue, revenueList.get(0));
    }


    @Test
    public void revenue_should_be_updated() {
        this.testEntityManager.persist(revenue);
        int result = revenueRepository.updateRvnInfo(10, "change intro", revenue.getId());
        Assert.assertEquals(1, result);
        List<Revenue> newRevenues = revenueRepository.getRevenueById(revenue.getId());
        System.out.println(newRevenues.get(0).getRvnPrice());
        Assert.assertEquals("change intro", newRevenues.get(0).getRvnIntro());
        Assert.assertEquals(10, newRevenues.get(0).getRvnPrice());
    }

    @Test
    public void revenue_should_be_got_by_name() {
        this.testEntityManager.persist(revenue);
        List<String> nameList = revenueRepository.selectRevenueName();
        Assert.assertEquals(1, nameList.size());
        Assert.assertEquals("mock name", nameList.get(0));
    }


}

