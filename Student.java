package cn.edu.swu.zxy.student;

//entity  实体类
public class Student {
 private String id;
 private String name;
 private int math;
 private int chinese;
 private int english;

 public Student() {

 }

 public Student(String id, String name) {

 }

 public void setId(String id) {
     this.id = id;
 }

 public String getId() {
     return this.id;
 }

 public void setName(String name) {
     this.name = name;
 }

 public String getName() {
     return this.name;
 }

 public int getMath() {
     return math;
 }

 public void setMath(int math) {
     this.math = math;
 }

 public int getChinese() {
     return chinese;
 }

 public void setChinese(int chinese) {
     this.chinese = chinese;
 }

 public int getEnglish() {
     return english;
 }

 public void setEnglish(int english) {
     this.english = english;
 }

 public int getTotal() {
     return this.getMath() + this.getChinese() + this.getEnglish();
 }

 public double getAverage() {
     return this.getTotal() / 4.0;
 }

 public String toString() {
     return String.format(
         "%s, %s，%s, %s, %s",
         this.getId(), this.getName(), this.getMath(), this.getChinese(), this.getEnglish()
     );
 }
}