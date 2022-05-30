import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/Step2Pro")
public class Step2Pro extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String db = request.getParameter("db");
        String targetDbUrl = request.getParameter("targetDbUrl");
        String targetDbUser = request.getParameter("targetDbUser");
        String targetDbPassword = request.getParameter("targetDbPassword");
        String targetDbScheme = request.getParameter("targetDbScheme");

        DbBlobExtractorParams dbBlob = (DbBlobExtractorParams) request.getSession().getAttribute("dbBlobExtractorParams");

        dbBlob.setTargetDbUrl("jdbc:" + db + ":" + targetDbUrl);
        dbBlob.setTargetDbUser(targetDbUser);
        dbBlob.setTargetDbPassword(targetDbPassword);
        dbBlob.setTargetDbScheme(targetDbScheme);

        if (new DbConnection().check(db, targetDbUrl, targetDbUser, targetDbPassword)) {
            request.getRequestDispatcher(Const.STEP3).forward(request, response);
        } else {
            request.setAttribute("messageStep2", "Error: Fail to connect to " + db);
            request.getRequestDispatcher(Const.STEP2).forward(request, response);
        }
    }
}
