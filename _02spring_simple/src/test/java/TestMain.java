import com.ant.bean.Student;
import com.ant.bean.Teacher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMain {

    public static void main(String[] args){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring_config.xml");
        Teacher teacher = applicationContext.getBean(Teacher.class);
        Student student = applicationContext.getBean(Student.class);
        System.out.println(teacher);
        System.out.println(student);
    }
}
