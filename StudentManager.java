package cn.edu.swu.zxy.student;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

// service class
public class StudentManager {

    private static StudentManager instance = new StudentManager();

    private Map<String, Student> students = new HashMap<>();

    private Map<String, Student> getStudents() {
        return this.students;
    }

    private StudentManager() {

    }

    public static StudentManager getInstance() {
        return instance;
    }

    public int getTotalNumber() {
        return this.getStudents().size();
    }

    public void printAll() {
        Map<String, Student> ss = this.getStudents();
        for (Map.Entry<String, Student> entry : ss.entrySet()) {
            Student student = entry.getValue();
            System.out.println(String.format(
                "%s, %-5s, %s, %s, %s",
                student.getId(), student.getName(), student.getMath(), student.getChinese(), student.getEnglish()
            ));
        }
    }

    public void visit(IStudentVisitor visitor) {
        this.getStudents().forEach((key, val) -> {
            visitor.visit(val);
        });
    }


    public void addStudent(Student student) {
        this.students.put(student.getId(), student);
    }

    public void deleteById(String id) {
        this.getStudents().remove(id);
    }

    public Student getStudentById(String id) {
       return this.students.get(id);
    }

    public List<Student> findByScore(ClassType classType, int min, int max) {
        List<Student> result = new ArrayList<>();
        this.getStudents().forEach((id, student) -> {
            int score = 0;
            switch (classType) {
                case MATH -> {score = student.getMath(); break;}
                case CHINESE -> {score = student.getChinese(); break;}
                case ENGLISH -> {score = student.getEnglish(); break;}
                default -> throw new IllegalArgumentException("错误的学科类别");
            }

            if (score >= min && score <= max) {
                result.add(student);
            }
        });
        return result;
    }

    public List<Student> findByAverage(int min, int max) {
        List<Student> result = new ArrayList<>();
        this.getStudents().forEach((id, student) -> {
            if ( student.getAverage() >= min  && student.getAverage() <= max) {
                result.add(student);
            }
        });
        return result;
    }

    public List<Student> sortByTotal() {
        return this.sortByTotal(SortType.DESC);
    }

    public List<Student> sortByTotal(SortType sortType) {
        List<Student> students = this.getStudents().values().stream().collect(Collectors.toList());
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                if (s1.getTotal() > s2.getTotal()) {
                    return sortType.equals(SortType.ASC) ? 1 : -1;
                } else if (s1.getTotal() < s2.getTotal()) {
                    return sortType.equals(SortType.ASC) ? -1 : 1;
                } else {
                    return 0;
                }
            }
        });
        return students;
    }

    public void saveAs(String filePath) throws IOException {
        Map<String, Student> ss = this.getStudents();
        FileWriter writer = new FileWriter(filePath);
        writer.write(String.format("学号, 姓名, 数学, 语文, 外语\n"));
        for (Iterator<String> it = ss.keySet().iterator(); it.hasNext(); ) {
            String key = it.next();
            Student student = ss.get(key);
            writer.write(String.format(
                "'%s, %s, %s, %s, %s\n",
                student.getId(), student.getName(), student.getMath(), student.getChinese(), student.getEnglish()
            ));
        }
        writer.close();
    }

}
