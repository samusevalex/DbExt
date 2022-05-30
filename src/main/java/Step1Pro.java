import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/Step1Pro")
public class Step1Pro extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String db = request.getParameter("db");
        String sourceDbUrl = request.getParameter("sourceDbUrl");
        String sourceDbUser = request.getParameter("sourceDbUser");
        String sourceDbPassword = request.getParameter("sourceDbPassword");
        String sourceDbScheme = request.getParameter("sourceDbScheme");

        DbBlobExtractorParams dbBlobExtractorParams = new DbBlobExtractorParams();

        request.getSession().setAttribute("dbBlobExtractorParams", dbBlobExtractorParams);

        dbBlobExtractorParams.setSourceDbUrl("jdbc:" + db + ":" + sourceDbUrl);
        dbBlobExtractorParams.setSourceDbUser(sourceDbUser);
        dbBlobExtractorParams.setSourceDbPassword(sourceDbPassword);
        dbBlobExtractorParams.setSourceDbScheme(sourceDbScheme);
        dbBlobExtractorParams.setAutoCommit(true);

        DbConnection dbConnection = new DbConnection();

        if (dbConnection.check(db, sourceDbUrl, sourceDbUser, sourceDbPassword)) {
            request.getRequestDispatcher(Const.STEP2).forward(request, response);
        } else {
            request.setAttribute("messageStep1", "Error: Fail to connect to " + db);
            request.getRequestDispatcher(Const.STEP1).forward(request, response);
        }
    }
}
