import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/Step4Pro")
public class Step4Pro extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int total = Integer.valueOf(request.getParameter("total"));
        request.setAttribute("total", total);
        JobStatusContainer.getInstance().setTotal(total);

        DbBlobExtractorParams dbBlob = (DbBlobExtractorParams) request.getSession().getAttribute("dbBlobExtractorParams");
        dbBlob.setQueries(new Queries());
        List<Query> listQuery = dbBlob.getQueries().getQuery();

        for (int i = 1; i <= total; i++) {

            String sourceDbQuery = request.getParameter("sourceDbQuery" + i);
            String targetDbQuery = request.getParameter("targetDbQuery" + i);

                Query query = new Query();
                query.setSourceDbQuery(sourceDbQuery);

                TargetDbQueries targetDbQueries = new TargetDbQueries();
                List<String> listTargetDbQueries = targetDbQueries.getTargetDbQuery();
                listTargetDbQueries.add(targetDbQuery);
                query.setTargetDbQueries(targetDbQueries);

                listQuery.add(query);
        }

        request.getRequestDispatcher(Const.STEP5).forward(request, response);
    }
}