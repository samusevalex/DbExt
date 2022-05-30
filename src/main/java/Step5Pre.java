import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Step5Pre")
public class Step5Pre extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        for (int i = (Integer)request.getAttribute("total")+1; i <= 5; i++)
            request.setAttribute("d" + i, "display:none");

        DbBlobExtractorParams dbBlob = (DbBlobExtractorParams) request.getSession().getAttribute("dbBlobExtractorParams");
        new WebBlobExtractor(dbBlob).start();

        request.getRequestDispatcher("step5.jsp").forward(request, response);
    }
}