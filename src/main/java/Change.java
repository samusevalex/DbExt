import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Change")
public class Change extends HttpServlet {
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        for(int i=1;i<=5;i++)
            JobStatusContainer.getInstance().setTaskStatus(i, Integer.parseInt(request.getParameter("task"+i)));
    }
}