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
import photodb.photo.FilePhoto;

/**
 *
 * @author Steffen Schumacher <steff@tdc.dk>
 */
@WebService(serviceName = "PhotoDBWS")
public class PhotoDBWS implements ServletContextListener {
    final static Logger LOG = Logger.getLogger(PhotoDBWS.class.getName());
    protected Database db;
    
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
            db.insert(filephoto);
            LOG.log(Level.FINE, "Inserted {0} into the database", filephoto.toString());
            
        } catch (ExistingPhotoException e) {
            LOG.log(Level.SEVERE, "Attempt to add {0} failed, since we already had it", filephoto);
            throw e;
        }
    }
    
    
    

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            db = new Database("/vol/photo-db/photo.db");
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
