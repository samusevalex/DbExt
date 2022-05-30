import javax.script.SimpleBindings;

public class UserList {
    private SimpleBindings loginsDb = new SimpleBindings();
    public UserList() {
        loginsDb.put("qwe", "qwe");
    }
    public boolean check(String login, String password){
        return !login.isEmpty()
                && loginsDb.containsKey(login)
                && loginsDb.get(login).equals(password);
    }
}
