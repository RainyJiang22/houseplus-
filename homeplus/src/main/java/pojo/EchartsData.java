package pojo;

/**
 * @program: homeplus
 * @description: eCHART实体类
 * @author: JSY
 * @create: 2019-08-22 15:33
 **/
public class EchartsData {
    private String name;  //需要展示的名字
    private Integer num; //获得它所有的数量

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "EchartsData{" +
                "name='" + name + '\'' +
                ", num=" + num +
                '}';
    }
}
