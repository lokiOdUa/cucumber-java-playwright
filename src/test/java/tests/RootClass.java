package tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static java.lang.System.currentTimeMillis;

public class RootClass {
    protected static String proxyAddress = null;

    private static final long POLLING_INTERVAL = 500;
    private static final String PROXY_ENV_NAME = "HTTP_PROXY";
    protected static final Proxy proxy = initProxy();

    protected void createDirectoryIfNotExist(String dirName) throws IOException {
        try {
            Files.createDirectory(Paths.get(dirName));
        }
        catch (IOException e) {
            if(!e.getClass().getSimpleName().equals("FileAlreadyExistsException")) {
                throw new IOException(e);
            }
        }
    }

    protected void createFileIfNotExist(String fileName) throws IOException {
        try {
            Files.createFile(Paths.get(fileName));
        }
        catch (IOException e) {
            if(!e.getClass().getSimpleName().equals("FileAlreadyExistsException")) {
                throw new IOException(e);
            }
        }
    }

    protected HttpURLConnection getConnection(URL url) throws IOException {
        return proxy == null ?
                (HttpURLConnection) url.openConnection() :
                (HttpURLConnection) url.openConnection(proxy);
    }

    protected String getHTTPData(HttpURLConnection connection) throws IOException {
        String contents = "";
        String newline = System.getProperty("line.separator");
        InputStreamReader in = new InputStreamReader((InputStream) connection.getContent());
        BufferedReader buff = new BufferedReader(in);
        String line;
        do {
            line = buff.readLine();
            if (line != null) {
                contents = contents + line + newline;
            }
        } while (line != null);
        return contents;
    }

    protected static Proxy initProxy() {
        proxyAddress = System.getenv(PROXY_ENV_NAME);
        if( ! (proxyAddress == null)) {
            String[] parts = proxyAddress.split(":");
            String hostName = parts[1].substring(2);
            int portNumber = Integer.parseInt(parts[2]);
            System.out.println("***** Got proxy address via ENV[" + PROXY_ENV_NAME + "] as " + proxyAddress +
                    ", so using it to connect");
            return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostName, portNumber));
        }
        return null;
    }

    protected boolean ensureCookiesContainJSessionID(List<HttpCookie> cookies) {
        for (HttpCookie cookie: cookies) {
            if(cookie.getName().equals("JSESSIONID") && validateUUID(cookie.getValue())) {
                return true;
            }
        }
        return false;
    }

    protected boolean validateUUID(String uuid) {
        try {
            UUID.fromString(uuid);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public void waitForAWhile(int msec) {
        long startMoment = currentTimeMillis();
        long finishMoment = startMoment + msec;
        try {
            do {
                Thread.sleep(POLLING_INTERVAL);
            } while(currentTimeMillis() < finishMoment );
        } catch (InterruptedException e) {
            System.out.println("Interrupted in " + e.toString());
        }
    }
}
