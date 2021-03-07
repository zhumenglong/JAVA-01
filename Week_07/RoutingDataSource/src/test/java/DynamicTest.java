import dynamic.Application;
import dynamic.annotation.DS;
import dynamic.annotation.OperationEnum;
import dynamic.dao.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DynamicTest {

    @Autowired
    OrderMapper mapper;



    @Test
    public void test() {
        read();
        read2();
    }

    @DS(value = "write", operate = OperationEnum.READ)
    public void read(){
        mapper.selectById(1);
    }

    @DS(value = "read", operate = OperationEnum.READ)
    public void read2(){
        mapper.selectById(1);
    }

}
