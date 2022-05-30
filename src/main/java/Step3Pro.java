import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Принимаем данные из JSP, в которой вводим количество потоков и транзакций
 */
@WebServlet("/Step3Pro")
public class Step3Pro extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Boolean ismultithread = Boolean.parseBoolean(request.getParameter("isMultithread"));
        BigInteger threadCount = new BigInteger(request.getParameter("threadCount"));
        BigInteger rowsPerTran = new BigInteger(request.getParameter("rowsPerTran"));

        DbBlobExtractorParams dbBlob = (DbBlobExtractorParams) request.getSession().getAttribute("dbBlobExtractorParams");

        dbBlob.setRowsPerTran(rowsPerTran);

        if(ismultithread)
            dbBlob.setThreadCount(threadCount);
        else
            dbBlob.setThreadCount(new BigInteger("1"));

        request.getRequestDispatcher(Const.STEP4).forward(request, response);
    }
}