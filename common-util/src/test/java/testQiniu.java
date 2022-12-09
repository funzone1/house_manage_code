import com.yuzai.util.QiniuUtils;
import org.junit.Test;

public class testQiniu {

    @Test
    public void testUpload(){

        QiniuUtils.upload2Qiniu("C:\\Users\\yuzai\\Documents\\imFiles\\Gras\\组会\\DL.PNG","Dl");
    }
}
