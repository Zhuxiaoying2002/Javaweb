package cn.edu.swu.zxy.student;

import java.io.IOException;
import java.util.List;

public interface IStudentReader  {

    public List<Student> read() throws IOException;

}