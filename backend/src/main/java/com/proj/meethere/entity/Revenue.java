package com.proj.meethere.entity;
import javax.persistence.*;

/**
 * @Author Yiqing Tao
 * @Date 2019-10-29
 * Revenue Entity
 */
@Entity
@Table(name="revenue")
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="rvn_name")
    String rvnName;

    /* 总共房间数 */
    @Column(name = "rvn_roomnum")
    int rvnRoomnum;

    @Column(name = "rvn_intro")
    String rvnIntro;

    @Column(name = "rvn_price")
    int rvnPrice;

    public Revenue() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRvnName() {
        return rvnName;
    }

    public void setRvnName(String rvn_name) {
        this.rvnName = rvn_name;
    }

    public int getRvnRoomnum() {
        return rvnRoomnum;
    }

    public void setRvnRoomnum(int rvnRoomNum) {
        this.rvnRoomnum = rvnRoomNum;
    }

    public String getRvnIntro() {
        return rvnIntro;
    }

    public void setRvnIntro(String rvnIntro) {
        this.rvnIntro = rvnIntro;
    }

    public int getRvnPrice() {
        return rvnPrice;
    }

    public void setRvnPrice(int rvnPrice) {
        this.rvnPrice = rvnPrice;
    }
}
