package mybatis;

import mybatis.bean.Dept;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

public class MyDeptFacory extends DefaultObjectFactory {
    @Override
    public <T> T create(Class<T> type) {
        if(Dept.class==type){
            //依靠父类的方法创建对象
            Dept dept = (Dept) super.create(type);
            //针对对象自定义规则
            dept.setContry("中国");
            return (T) dept;
        }
        return super.create(type);
    }
}
