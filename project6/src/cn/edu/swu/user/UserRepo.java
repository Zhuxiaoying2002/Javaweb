package cn.edu.swu.user;

import cn.edu.swu.db.DBEngine;
import cn.edu.swu.db.RecordVisitor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserRepo {

    private static UserRepo instance = new UserRepo();

    private UserRepo() {
    }

    public static UserRepo getInstance() {
        return instance;
    }

    public void save(User user) throws SQLException {
        String template =
                "INSERT INTO `user`(id, name, user, password) " +
                "VALUES (\"%s\", \"%s\", \"%s\", MD5(\"%s\"))";
        String sql = String.format(template, user.getId(), user.getName(), user.getUser(), user.getPassword());
        DBEngine.getInstance().execute(sql);
    }

    private void insertUser(User user) throws SQLException {
        String template =
                "INSERT INTO `user`(id, name, user, password) " +
                        "VALUES (\"%s\", \"%s\", %s, \"%s\")";
        String sql = String.format(template, user.getId(), user.getName(), user.getUser(), user.getPassword());
        DBEngine.getInstance().execute(sql);
    }


    public User auth(String user, String password) throws SQLException {
        String template = "SELECT * FROM `user` WHERE `user` = \"%s\" AND `password` = MD5(\"%s\")";
        List<User> users = DBEngine.getInstance().query(
            String.format(template, user, password), new RecordVisitor<User>() {
            @Override
            public User visit(ResultSet rs) throws SQLException {
                return UserRepo.getUserByResultSet(rs);
            }
        });
        return users.size() == 0 ? null : users.get(0);
    }

    public void delete(User user) throws SQLException {
        String template = "DELETE FROM `user` WHERE `id` = \"%s\"";
        DBEngine.getInstance().execute(String.format(template, user.getId()));
    }

    public List<User> getAll() throws SQLException {
        String sql = "SELECT `id`, `name`, `user`, `password` FROM `user`";
        return DBEngine.getInstance().query(sql, new RecordVisitor<User>() {
            @Override
            public User visit(ResultSet rs) throws SQLException {
                return UserRepo.getUserByResultSet(rs);
            }
        });
    }

    private static User getUserByResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setUser(rs.getString("user"));
        user.setPassword(rs.getString("password"));
        return user;
    }

    public boolean update(User user) throws SQLException {
        String id = user.getId();
        String user1 = user.getUser();
        String name = user.getName();
        String password = user.getPassword();
        String sql = "UPDATE `user` SET `id`="+id+" ,`name`="+name+" ,`user`="+user1+",`password`="+password+" FROM `user`";
        DBEngine.getInstance().execute(sql);
        return true;

    }
}
