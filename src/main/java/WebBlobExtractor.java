public class WebBlobExtractor extends Thread {

    private DbBlobExtractorParams dbBlob;

    public WebBlobExtractor(DbBlobExtractorParams dbBlob) {
        this.dbBlob = dbBlob;
    }

    public void run() {
        BlobExtractor blobExtractor = new BlobExtractor();
        blobExtractor.setParams(dbBlob);
        try {
            String[] str = {""};
            blobExtractor.process(str);
        }catch (Exception e)
        {

        }
    }
}
