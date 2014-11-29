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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.bind.annotation.XmlElement;
import photodb.config.Config;
import photodb.config.NotInitializedException;
import photodb.db.ExistingPhotoException;
import photodb.io.ByteChannel;
import photodb.photo.SoapPhoto;
import photodb.processing.PhotoController;

/**
 *
 * @author Steffen Schumacher <steff@tdc.dk>
 */
@WebService(serviceName = "PhotoDBWS")
public class PhotoDBWS implements ServletContextListener {

    private final static Logger LOG = Logger.getLogger(PhotoDBWS.class.getName());
    private static PhotoController cntr;

    /**
     * exists checks if a given photo fingerprint exists in the database already
     *
     * @param soapphoto
     * @return
     */
    @WebMethod(operationName = "isUploadEligible")
    public boolean isUploadEligible(
            @WebParam(name = "soapphoto")
            @XmlElement(required = true) SoapPhoto soapphoto) {
        boolean isDesired = cntr.isDesired(soapphoto);
        LOG.log(Level.FINE, "{0} was checked for upload eligibility. Result: {1}",
                new Object[]{soapphoto.toString(), isDesired});
        return isDesired;
    }

    /**
     * Checks an array of SoapPhotos to see if they each are eligible for uploading.
     * @param photos array of SoapPhoto
     * @return 
     */
    @WebMethod(operationName = "isArrayUploadEligible")
    public List<SoapPhoto> isArrayUploadEligible(
            @WebParam(name = "photos")
            @XmlElement(required = true)
            List<SoapPhoto> photos) {
        if(photos.size() > 20) {
            return null;
        }
        for(SoapPhoto sp : photos) {
            boolean isDesired = cntr.isDesired(sp);
            LOG.log(Level.FINE, "{0} was checked for upload eligibility. Result: {1}",
                new Object[]{sp.toString(), isDesired});
            sp.setEligible(isDesired);
        }
        return photos;
    }

    /**
     * exists checks if a given photo fingerprint exists in the database already
     *
     * @param soapphoto
     * @param imageBytes
     * @throws photodb.ws.ExistingPhotoWSException
     */
    @WebMethod(operationName = "add")
    public void add(
            @WebParam(name = "soapphoto")
            @XmlElement(required = true) SoapPhoto soapphoto,
            @WebParam(name = "imageBytes")
            @XmlElement(required = true) byte[] imageBytes) throws ExistingPhotoWSException {
        try {
            ByteChannel source = new ByteChannel(imageBytes);
            cntr.insert(soapphoto, source);
            LOG.log(Level.INFO, "Inserted {0} successfully", soapphoto.toString());
        } catch (ExistingPhotoException e) {
            LOG.log(Level.SEVERE, "Attempt to add {0} failed, since we already had it", soapphoto);
            throw new ExistingPhotoWSException(e.getMessage(), e.getBlockingPhoto().toSOAPObject());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Unexpected IO Exception during insert of "
                    + soapphoto.toString(), ex);
        }
    }

    @Override
    @WebMethod(exclude = true)
    public void contextInitialized(ServletContextEvent sce) {
        final String root = "/Volumes/store0/photo-db";
        Config c;

        try {
            Config.getInstance();
            LOG.log(Level.SEVERE, "Got unexpected config before having initialized it?");
        } catch (NotInitializedException ex) {
            try {
                ex.initializeConfig(Config.deriveConfigFileFromPath(root));
            } catch (FileNotFoundException fnfe) {
                //Means we need to create one manually
                c = new Config(root);
                ex.initializeConfig(c);
                try {
                    ex.storeConfig(c);
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, "Exception storing config", ex1);
                }
            } catch (IOException ex1) {
                LOG.log(Level.SEVERE, "Exception reading config", ex1);
            }
            //At this point Config should be initialized
        }
        try {
            cntr = new PhotoController(root);
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Unable to initialize connection to database", ex);
        }

    }

    @Override
    @WebMethod(exclude = true)
    public void contextDestroyed(ServletContextEvent sce) {
        if (cntr != null) {
            cntr.close();
        }
    }

}
