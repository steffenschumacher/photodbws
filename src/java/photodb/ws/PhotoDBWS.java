/*
 *  
 *  TDC A/S CONFIDENTIAL
 *  __________________
 *  
 *   [2004] - [2013] TDC A/S, Operations department 
 *   All Rights Reserved.
 *  
 *  NOTICE:  All information contained herein is, and remains
 *  the property of TDC A/S and its suppliers, if any.
 *  The intellectual and technical concepts contained herein are
 *  proprietary to TDC A/S and its suppliers and may be covered
 *  by Danish and Foreign Patents, patents in process, and are 
 *  protected by trade secret or copyright law.
 *  Dissemination of this information or reproduction of this 
 *  material is strictly forbidden unless prior written 
 *  permission is obtained from TDC A/S.
 *  
 */

package photodb.ws;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import photodb.db.Database;
import photodb.db.ExistingPhotoException;
import photodb.io.ByteChannel;
import photodb.photo.FilePhoto;

/**
 *
 * @author Steffen Schumacher <steff@tdc.dk>
 */
@WebService(serviceName = "PhotoDBWS")
public class PhotoDBWS extends photodb.processing.PhotoController implements ServletContextListener {
    private final static Logger LOG = Logger.getLogger(PhotoDBWS.class.getName());
    private final static String s_photoroot = "/vol/photo-db/";
    private static Database s_db;
    
    public PhotoDBWS() throws SQLException {
        super(s_photoroot, s_db);
    }
    
    /**
     * exists checks if a given photo fingerprint exists in the database already
     * @param filephoto
     * @return 
     */
    @WebMethod(operationName = "exists")
    public boolean exists(@WebParam(name = "filephoto") FilePhoto filephoto) {
        try {
            db.insert(filephoto);
            db.delete(filephoto);
            return false;
        } catch (ExistingPhotoException e) {
            return true;
        }
    }
    
    /**
     * exists checks if a given photo fingerprint exists in the database already
     * @param filephoto 
     * @param imageBytes 
     * @throws photodb.db.ExistingPhotoException 
     */
    @WebMethod(operationName = "exists")
    public void add(@WebParam(name = "filephoto") FilePhoto filephoto, 
            @WebParam(name= "imageBytes") byte[] imageBytes) throws ExistingPhotoException {
        try {
            ByteChannel source = new ByteChannel(imageBytes);

            insert(filephoto, source);
            
        } catch (ExistingPhotoException e) {
            LOG.log(Level.SEVERE, "Attempt to add {0} failed, since we already had it", filephoto);
            throw e;
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Unexpected IO Exception during insert of " + 
                    filephoto.toString(), ex);
        }
    }
    
    
    

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            s_db = new Database(s_photoroot + "photo.db");
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Unable to initialize connection to database", ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if(db != null) {
            db.close();
        }
    }
    
    
}
