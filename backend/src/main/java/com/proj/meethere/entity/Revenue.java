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
    String rvn_name;

    @Column(name = "rvn_roomnum")
    int rvnRoomNum;

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

    public String getRvn_name() {
        return rvn_name;
    }

    public void setRvn_name(String rvn_name) {
        this.rvn_name = rvn_name;
    }

    public int getRvnRoomNum() {
        return rvnRoomNum;
    }

    public void setRvnRoomNum(int rvnRoomNum) {
        this.rvnRoomNum = rvnRoomNum;
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
