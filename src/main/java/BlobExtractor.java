import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlobExtractor {

    private static final Logger log = Logger.getLogger(BlobExtractor.class.getName());

    private static String ORACLE_JDBC_CLASS_NAME = "oracle.jdbc.OracleDriver";
    private static String LINTER_JDBC_CLASS_NAME = "com.relx.jdbc.LinterDriver";
    private static String ODBC_CLASS_NAME = "sun.jdbc.odbc.JdbcOdbcDriver";
    private static String FIREBIRD_JDBC_CLASS_NAME = "org.firebirdsql.jdbc.FBDriver";
    private static String POSTGRESQL_CLASS_NAME = "org.postgresql.Driver";

    private static String MSACCESS_JDBC_PREFIX = "JDBC:ODBC:Driver=Microsoft";

    private static DbBlobExtractorParams params;

    private boolean msAccessDetected = false;

    private static DbBlobExtractorParams initParams(String paramFileName) throws JAXBException {
        JAXBContext context =
                JAXBContext.newInstance(DbBlobExtractorParams.class.getPackage().getName());

        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (DbBlobExtractorParams) unmarshaller.unmarshal(new File(paramFileName));
    }

    private Connection createConnection(String url, String userName, String password)
            throws Exception {
        java.util.Properties properties = new Properties();

        properties.put("user", userName);
        properties.put("password", password);

        Connection result = DriverManager.getConnection(url, properties);

        return result;
    }

    public void process(String[] args) throws Exception {

        if (args.length != 1) {
            log.fatal("Please set full file name for application parameters");

            return;
        }

//        params = initParams(args[ 0 ]);


        if (params.getSourceDbUrl() == null || params.getSourceDbUrl().length() == 0) {
            log.fatal("Property 'sourceDbUrl' must be set");

            return;
        }
        if (params.getTargetDbUrl() == null || params.getTargetDbUrl().length() == 0) {
            log.fatal("Property 'targetDbUrl' must be set");

            return;
        }
        if (params.getTargetDbUser() == null || params.getTargetDbUser().length() == 0) {
            log.fatal("Property 'targetDbUser' must be set");

            return;
        }
/*
        if (params.getTargetDbPassword() == null || params.getTargetDbPassword().length() == 0) {
            log.fatal("Property 'targetDbPassword' must be set");

            return;
        }
        if (params.getTargetDbScheme() == null || params.getTargetDbScheme().length() == 0) {
            log.fatal("Property 'targetDbScheme' must be set");

            return;
        }
*/

        log.info("Source Database URL :      " + params.getSourceDbUrl());
        log.info("Source Database User :     " + params.getSourceDbUser());
        log.info("Source Database Password : " + params.getSourceDbPassword());
        log.info("Source Database Scheme :   " + params.getSourceDbScheme());
        log.info("Target Database URL :      " + params.getTargetDbUrl());
        log.info("Target Database User :     " + params.getTargetDbUser());
        log.info("Target Database Password : " + params.getTargetDbPassword());
        log.info("Target Database Scheme :   " + params.getTargetDbScheme());

        log.info("Thread Count :             " + params.getThreadCount());
        log.info("Auto Commit :              " + params.isAutoCommit());
        log.info("Rows Per Transaction :     " + params.getRowsPerTran());

        try {
/*
            Class.forName(ORACLE_JDBC_CLASS_NAME);
            Class.forName(ODBC_CLASS_NAME);
            Class.forName(FIREBIRD_JDBC_CLASS_NAME);
            Class.forName(POSTGRESQL_CLASS_NAME);
            Class.forName(LINTER_JDBC_CLASS_NAME);
*/
            Class.forName("org.h2.Driver");

            if (params.getSourceDbUrl().startsWith(MSACCESS_JDBC_PREFIX)) {
                msAccessDetected = true;
            }
        } catch (Exception e) {
            log.fatal(e.getMessage(), e);

            return;
        }

        try {
            List<Task> tasks = new ArrayList<Task>();

            for (int i = 0; i < params.getThreadCount().intValue(); i++) {
                Task task = new Task();

                tasks.add(task);
            }

            ExecutorService executor = Executors.newFixedThreadPool(params.getThreadCount().intValue());

            for (Task thread : tasks) {
                executor.execute(thread);
            }

            executor.shutdown();

            while (!executor.isTerminated()) {
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void setParams(DbBlobExtractorParams params) {
        BlobExtractor.params = params;
    }

    public static void main(String[] args) throws Exception {
        BlobExtractor blobExtractor = new BlobExtractor();

        blobExtractor.process(args);
    }

    private class Task extends Thread {

        private final Logger taskLog = Logger.getLogger(Task.class.getName());
        private int taskNumber;

        private int getTaskName() {
            String threadName = Thread.currentThread().getName();

            if (threadName != null && threadName.startsWith("pool-1-thread-")) {
                threadName = threadName.replaceFirst("pool-1-thread-", "");
            }

            return Integer.parseInt(threadName) - 1;
        }

        private void prepareTargetConnection(Connection connection) throws SQLException {
            connection.setAutoCommit(params.isAutoCommit());
        }

        private void processCommit(Connection connection, int rowCount) throws SQLException {
            if (params.isAutoCommit()) {
                if (rowCount % params.getRowsPerTran().intValue() == 0) {
                    connection.commit();

                    taskLog.info(rowCount + " processed");
                }
            } else {
                if (rowCount % params.getRowsPerTran().intValue() == 0) {
                    taskLog.info(rowCount + " processed");
                }
            }
        }

        private void processRollback(Connection connection, int rowCount) throws SQLException {
            if (!params.isAutoCommit()) {
                connection.rollback();
            }

            taskLog.info(rowCount + " processed");
        }

        @Override
        public void run() {
            List<PreparedStatement> insertPreparedStatements = new ArrayList<PreparedStatement>();

            try (Connection sourceConnection = createConnection(params.getSourceDbUrl(),
                    params.getSourceDbUser(),
                    params.getSourceDbPassword());
                 Connection targetConnection = createConnection(params.getTargetDbUrl(),
                         params.getTargetDbUser(),
                         params.getTargetDbPassword());) {

                prepareTargetConnection(targetConnection);

                int x=0;

                for (Query query : params.getQueries().getQuery()) {
                    int rowCount = 0;
                    int successRowCount = 0;

                    for (PreparedStatement preparedStatement : insertPreparedStatements) {
                        try {
                            preparedStatement.close();

                        } catch (SQLException e) {
                            taskLog.error(e.getMessage(), e);
                        }
                    }

                    insertPreparedStatements.clear();

                    String sourceQuery = query.getSourceDbQuery().toString();

                    sourceQuery = sourceQuery.replaceAll("%THREAD_COUNT_PARAM%", "" +
                            params.getThreadCount().intValue());
                    sourceQuery = sourceQuery.replaceAll("%THREAD_ID_PARAM%", "" + getTaskName());

                    taskLog.trace("sourceQuery = " + sourceQuery);

                    for (String targetQuery : query.getTargetDbQueries().getTargetDbQuery()) {
                        taskLog.trace("targetQuery = " + targetQuery);

                        insertPreparedStatements.add(targetConnection.prepareStatement(targetQuery));

                    }

                    try (PreparedStatement selectPreparedStatement = sourceConnection
                            .prepareStatement(sourceQuery);) {

                        if (!msAccessDetected) {
                            selectPreparedStatement.setFetchSize(1000);
                        }

                        ResultSetMetaData resultSetMetaData = null;

                        try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
                            taskLog.info("Start processing query = " + sourceQuery);

                            while (resultSet.next()) {
                                StringBuilder queryParamsForLog = new StringBuilder();

                                try {
                                    resultSetMetaData = resultSet.getMetaData();

                                    int delta = 0;

                                    Iterator<PreparedStatement> it = insertPreparedStatements.iterator();

                                    PreparedStatement insertPreparedStatement = it.next();

                                    int paramIndex = 1;

                                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                                        if (!"THRD".equals(resultSetMetaData.getColumnName(i))) {
                                            if (resultSetMetaData.getColumnType(i) == Types.BLOB ||
                                                    resultSetMetaData.getColumnType(i) == Types.CLOB) {

                                                byte[] binaryBuffer = new byte[4096];

                                                InputStream sourceBlobIs = resultSet.getBinaryStream(i);

                                                try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                                                    int bytesRead = 0;

                                                    while ((bytesRead = sourceBlobIs.read(binaryBuffer)) != -1) {
                                                        byteArrayOutputStream.write(binaryBuffer, 0,
                                                                bytesRead);
                                                    }

                                                    byteArrayOutputStream.flush();

                                                    InputStream is = new ByteArrayInputStream(
                                                            byteArrayOutputStream
                                                                    .toByteArray());

                                                    if (paramIndex >= (i - delta)) {
                                                        paramIndex = (i - delta);
                                                    }

                                                    try {
                                                        insertPreparedStatement
                                                                .setBinaryStream(paramIndex, is,
                                                                        is.available());

                                                        String paramForLog = "?" + paramIndex + " = " + is;

                                                        log.trace(paramForLog);

                                                        queryParamsForLog.append(paramForLog);
                                                        queryParamsForLog.append("\n");
                                                    } catch (SQLException e) {
                                                        insertPreparedStatement = it.next();

                                                        paramIndex = 1;

                                                        insertPreparedStatement
                                                                .setBinaryStream(paramIndex, is,
                                                                        is.available());

                                                        String paramForLog = "?" + paramIndex + " = " + is;

                                                        log.trace(paramForLog);

                                                        queryParamsForLog.append(paramForLog);
                                                        queryParamsForLog.append("\n");
                                                    }

                                                    paramIndex++;
                                                }
                                            } else {
                                                if (paramIndex >= (i - delta)) {
                                                    paramIndex = (i - delta);
                                                }

                                                Object data = null;

                                                try {
                                                    data = resultSet.getObject(i);

                                                    insertPreparedStatement
                                                            .setObject(paramIndex, data,
                                                                    resultSetMetaData.getColumnType(i));

                                                    String paramForLog = "?" + paramIndex + " = " + data;

                                                    log.trace(paramForLog);

                                                    queryParamsForLog.append(paramForLog);
                                                    queryParamsForLog.append("\n");
                                                } catch (SQLException e) {
                                                    insertPreparedStatement = it.next();

                                                    paramIndex = 1;

                                                    insertPreparedStatement
                                                            .setObject(paramIndex, data,
                                                                    resultSetMetaData.getColumnType(i));

                                                    String paramForLog = "?" + paramIndex + " = " + data;

                                                    log.trace(paramForLog);

                                                    queryParamsForLog.append(paramForLog);
                                                    queryParamsForLog.append("\n");
                                                }

                                                paramIndex++;
                                            }
                                        } else {
                                            delta++;
                                        }
                                    }

                                    for (PreparedStatement insertStmt : insertPreparedStatements) {
                                        insertStmt.execute();
                                    }

                                    successRowCount++;
                                    rowCount++;

                                    processCommit(targetConnection, rowCount);
                                } catch (Exception e) {
                                    taskLog.error("Query params: \n" + queryParamsForLog);
                                    taskLog.error(e.getMessage(), e);

                                    rowCount++;

                                    processRollback(targetConnection, rowCount);
                                }
                            }
                        }

                        if (!params.isAutoCommit()) {
                            targetConnection.commit();
                        }

                        taskLog.info("Stop processing query = " + sourceQuery);
                    } catch (Exception e) {
                        taskLog.error(e.getMessage(), e);
                    } finally {
                        taskLog.info("Success processed rows : " + successRowCount);
                        taskLog.info("Total rows             : " + rowCount);
                        if (successRowCount!=0 && successRowCount == rowCount)
                            JobStatusContainer.getInstance().setTaskStatus(x++, 2);
                        else
                            JobStatusContainer.getInstance().setTaskStatus(x++, 1);
                    }
                }
            } catch (Exception e1) {
                taskLog.fatal(e1.getMessage(), e1);
                JobStatusContainer.getInstance().setError(e1.getMessage());
            } finally {
                for (PreparedStatement preparedStatement : insertPreparedStatements) {
                    try {
                        if (!preparedStatement.isClosed()) {
                            preparedStatement.close();
                        }
                    } catch (SQLException e) {
                        taskLog.trace(e.getMessage(), e);
                    }
                }
            }
        }
    }
}