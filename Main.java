package cn.edu.swu.zxy.student;

import cn.edu.swu.zxy.student.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.startup(
            "http://10.122.7.154/javaweb/data/students.txt",
            "http://139.186.26.196/javaweb/data/math.txt",
            "http://139.186.26.196/javaweb/data/chinese.txt",
            "http://139.186.26.196/javaweb/data/english.txt"
        );
        main.execute();
        main.shutdown();
    }

    public void startup(String uriStudent, String ruiMath, String ruiChinese, String uriEnglish) throws IOException {
        List<Student> students = this.loadStudents(uriStudent);

        // 加载学生基本信息
        StudentManager manager = StudentManager.getInstance();
        students.forEach(s -> {
            manager.addStudent(s);
        });

        // 加载数学成绩
        Map<String, Integer> mathScores = ScoreReaderFactory.create(ruiMath).read();
        mathScores.forEach((id, score) -> {
            Student student = manager.getStudentById(id);
            student.setMath(score);
        });

        // 加载语文成绩
        Map<String, Integer> chineseScores = ScoreReaderFactory.create(ruiChinese).read();
        chineseScores.forEach((id, score) -> {
            manager.getStudentById(id).setChinese(score);
        });

        // 加载外语成绩
        Map<String, Integer> englishScores = ScoreReaderFactory.create(uriEnglish).read();
        englishScores.forEach((id, score) -> {
            manager.getStudentById(id).setEnglish(score);
        });

    }

    public void shutdown() {
        System.out.println("Bye bye !");
    }

    public void execute() throws IOException {
        StudentManager manager = StudentManager.getInstance();
        manager.printAll();
    }

    public List<Student> loadStudents(String uri) throws IOException {
        IStudentReader reader = StudentReaderFactory.create(uri);
        return reader.read();
    }

}