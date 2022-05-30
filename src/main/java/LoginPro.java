import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoginPro")
public class LoginPro extends HttpServlet {

    UserList ul = new UserList();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");     //Get variables login and password from login.jsp

        if (ul.check(login, password)) {                  //Validate login and password
            request.getSession().setAttribute(Const.HTTP_SESSION_ATTRIBUTE_USER, login);
            request.getRequestDispatcher(Const.STEP1).forward(request, response);    //If ok, then go farther
        } else {
            request.setAttribute("messageLogin", "Error: Login failed.");                   //If not, go back to Login page
            request.getRequestDispatcher(Const.LOGIN).forward(request, response);
        }
    }
}
