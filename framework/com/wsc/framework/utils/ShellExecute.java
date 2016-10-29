package com.wsc.framework.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ShellExecute {
	
	public  String remoteRunCmd(String hostname,String username,String password,String  cmd)
    {
            Connection conn = new Connection(hostname);
            Session sess = null;
            try
            {
               
                conn.connect();
               
                boolean isAuthenticated = conn.authenticateWithPassword(username, password);
                if (isAuthenticated == false)
                        throw new IOException("Authentication failed.");
               
                sess = conn.openSession();
                sess.execCommand( cmd);
                InputStream stdout =    sess.getStdout() ;
                BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
                StringBuilder sb = new StringBuilder();
                while (true)
                {
                    String line = br.readLine();
                    if (line == null)
                        break;
                    sb.append(line);
                    sb.append('\n');
                }
               
               return sb.toString();
            }catch (Exception e){
                return "";
            }finally{
                
                sess.close();
               
                conn.close();
            }
        }

	

}
