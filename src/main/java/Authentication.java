import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 *
 */
@WebServlet("/Authentication")
public class Authentication extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute(Const.HTTP_SESSION_ATTRIBUTE_USER)==null) {          //Verification of user presence in HTTP session.
            request.getRequestDispatcher(Const.LOGIN).forward(request, response);    //If present, then forward to Login page.
        }else{
            request.getRequestDispatcher(Const.STEP1).forward(request, response);
        }
    }
}
