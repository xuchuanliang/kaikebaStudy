import com.ant.bean.Student;
import com.ant.bean.Teacher;
import com.ant.bean.beanPostProcesser.SomeService;
import com.ant.bean.myDiySpringIOC.BeanDefined;
import com.ant.bean.myDiySpringIOC.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class TestMain {

    public static void main(String[] args) throws Exception {
//        testMySpring();
//        testScope();
//        getBeanByFactory();
//        getBeanDIYFactory();
        testPostProcesser();
    }

    public static void testSpring(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring_config.xml");
        Teacher teacher = applicationContext.getBean(Teacher.class);
        Student student = applicationContext.getBean(Student.class);
        System.out.println(teacher);
        System.out.println(student);
    }

    public static void testMySpring() throws Exception {
        BeanDefined beanDefined = new BeanDefined();
        beanDefined.setBeanId("teacher");
        beanDefined.setClasPath("com.ant.bean.Teacher");
        List<BeanDefined> beanDefinedList = new ArrayList<BeanDefined>();
        beanDefinedList.add(beanDefined);
        BeanFactory beanFactory = new BeanFactory(beanDefinedList);
        beanFactory.setBeanDefinedList(beanDefinedList);
        Teacher teacher = (Teacher)beanFactory.getBean("teacher");
        System.out.println(teacher);
    }

    public static void testScope() throws Exception {
        BeanDefined beanDefined = new BeanDefined();
        beanDefined.setBeanId("teacher");
        beanDefined.setClasPath("com.ant.bean.Teacher");
//        beanDefined.setScope("prototype");
//        beanDefined.setScope("prototype");
        List<BeanDefined> beanDefinedList = new ArrayList<BeanDefined>();
        beanDefinedList.add(beanDefined);
        BeanFactory beanFactory = new BeanFactory(beanDefinedList);
        beanFactory.setBeanDefinedList(beanDefinedList);
        Teacher teacher = (Teacher)beanFactory.getBean("teacher");
        System.out.println(teacher);
        Teacher teacher2 = (Teacher)beanFactory.getBean("teacher");
        System.out.println(teacher2);
    }

    public static void getBeanByFactory(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring_config.xml");
        Teacher teacher = applicationContext.getBean(Teacher.class);
        System.out.println(teacher);
    }

    private static void getBeanDIYFactory() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        BeanDefined beanDefined = new BeanDefined();
        beanDefined.setBeanId("teacher");
        beanDefined.setClasPath("com.ant.bean.Teacher");
        beanDefined.setFactoryMethod("createTeacher");
        beanDefined.setBeanFactory("com.ant.bean.TeacheFactor");
//        beanDefined.setScope("prototype");
        List<BeanDefined> beanDefinedList = new ArrayList<BeanDefined>();
        beanDefinedList.add(beanDefined);
        BeanFactory beanFactory = new BeanFactory(beanDefinedList);
        beanFactory.setBeanDefinedList(beanDefinedList);
        Teacher teacher = (Teacher)beanFactory.getBean("teacher");
        System.out.println(teacher);
        Teacher teacher2 = (Teacher)beanFactory.getBean("teacher");
        System.out.println(teacher2);
    }

    /**
     * 调用getBean时先调用对象的构造方法、其次调用postProcessBeforeInitialization(),postProcessAfterInitialization(),
     */
    private static void testPostProcesser(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring_config.xml");
        SomeService someService = applicationContext.getBean(SomeService.class);
        System.out.println(someService.some());
    }
}
