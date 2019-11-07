
import java.io.*;

/**
 * @author xuchuanliangbt
 * @title: Test
 * @projectName kaikebaStudy
 * @description:
 * @date 2019/10/158:38
 * @Version
 */
public class Test {
    private static int normalLines = 0;  //有效程序行数
    private static int whiteLines = 0;   //空白行数
    private static int commentLines = 0; //注释行数

    public static void countCodeLine(File file) {
        System.out.println("代码行数统计：" + file.getAbsolutePath());
        if (file.exists()) {
            try {
                scanFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("文件不存在！");
            System.exit(0);
        }
        System.out.println(file.getAbsolutePath() + " ,java文件统计：" +
                "总有效代码行数: " + normalLines +
                " ,总空白行数：" + whiteLines +
                " ,总注释行数：" + commentLines +
                " ,总行数：" + (normalLines + whiteLines + commentLines));
    }

    private static void scanFile(File file) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                scanFile(files[i]);
            }
        }
        if (file.isFile()) {
            if (file.getName().endsWith(".java")) {
                count(file);
            }
        }
    }

    private static void count(File file) {
        BufferedReader br = null;
        // 判断此行是否为注释行
        boolean comment = false;
        int temp_whiteLines = 0;
        int temp_commentLines = 0;
        int temp_normalLines = 0;

        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.matches("^[//s&&[^//n]]*$")) {
                    // 空行
                    whiteLines++;
                    temp_whiteLines++;
                } else if (line.startsWith("/*") && !line.endsWith("*/")) {
                    // 判断此行为"/*"开头的注释行
                    commentLines++;
                    comment = true;
                } else if (comment == true && !line.endsWith("*/")) {
                    // 为多行注释中的一行（不是开头和结尾）
                    commentLines++;
                    temp_commentLines++;
                } else if (comment == true && line.endsWith("*/")) {
                    // 为多行注释的结束行
                    commentLines++;
                    temp_commentLines++;
                    comment = false;
                } else if (line.startsWith("//")) {
                    // 单行注释行
                    commentLines++;
                    temp_commentLines++;
                } else {
                    // 正常代码行
                    normalLines++;
                    temp_normalLines++;
                }
            }

            System.out.println(file.getName() +
                    " ,有效行数" + temp_normalLines +
                    " ,空白行数" + temp_whiteLines +
                    " ,注释行数" + temp_commentLines +
                    " ,总行数" + (temp_normalLines + temp_whiteLines + temp_commentLines));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                    br = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //测试
    public static void main(String[] args) {
        File file = new File("D:\\code\\idea_code\\newhikmtc\\hikmtc");
        countCodeLine(file);
    }
}
